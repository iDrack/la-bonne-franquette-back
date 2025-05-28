package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.security.service.CustomUserDetailsService;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.labonnefranquette.data.services.impl.AuthServiceImpl;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private UserService userService;
    @Mock
    private DtoTools dtoTools;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void signup_ShouldDelegateToUserService() {
        UserCreateDto dto = new UserCreateDto();
        User created = new User();
        when(userService.create(dto)).thenReturn(created);

        User result = authService.signup(dto);

        assertSame(created, result);
        verify(userService, times(1)).create(dto);
    }

    @Test
    void logout_ShouldBlacklistTokensAndClearContext() {
        // placer une authentication factice dans le contexte
        Authentication dummyAuth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(dummyAuth);
        String at = "at", rt = "rt";

        try (MockedStatic<JwtBlacklistService> blackListMock = mockStatic(JwtBlacklistService.class)) {
            authService.logout(at, rt);
            blackListMock.verify(() -> JwtBlacklistService.addToBlacklist(at),  times(1));
            blackListMock.verify(() -> JwtBlacklistService.addToBlacklist(rt),  times(1));
        }

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Le contexte de sécurité doit être vidé après logout");
    }

    @Test
    void refresh_WhenValidToken_ShouldReturnNewAccessToken() {
        String rt = "refresh";
        String username = "user2";
        when(jwtUtil.isValidRefreshToken(rt)).thenReturn(true);
        when(jwtUtil.extractUsername(rt)).thenReturn(username);

        List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, "pwd", auths);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        User domainUser = mock(User.class);
        Restaurant rest = mock(Restaurant.class);
        when(domainUser.getRestaurant()).thenReturn(rest);
        when(rest.getId()).thenReturn(99L);
        when(userService.getByUsername(username)).thenReturn(domainUser);

        String newToken = "new-token";
        when(jwtUtil.generateToken(eq(username), anyList(), eq(99L)))
                .thenReturn(newToken);

        String result = authService.refresh(rt);
        assertEquals(newToken, result);

        verify(jwtUtil).isValidRefreshToken(rt);
        verify(jwtUtil).generateToken(eq(username), anyList(), eq(99L));
    }

    @Test
    void refresh_WhenInvalidToken_ShouldReturnNull() {
        when(jwtUtil.isValidRefreshToken("bad")).thenReturn(false);
        assertNull(authService.refresh("bad"));
        verify(jwtUtil).isValidRefreshToken("bad");
        verify(jwtUtil, never()).generateToken(anyString(), anyList(), anyLong());
    }


    @Test
    void isConnected_NoAuthentication_ReturnsFalse() {
        SecurityContextHolder.clearContext();
        assertFalse(authService.isConnected());
    }

    @Test
    void isConnected_AnonymousAuthentication_ReturnsFalse() {
        Authentication auth = new AnonymousAuthenticationToken(
                "u",
                "p",
                List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertFalse(authService.isConnected());
    }

    @Test
    void isConnected_ValidAuthentication_ReturnsTrue() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "u",
                "p",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertTrue(authService.isConnected());
    }

    @Test
    void isConnected_AuthenticationNotAuthenticated_ReturnsFalse() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertFalse(authService.isConnected());
    }
}
