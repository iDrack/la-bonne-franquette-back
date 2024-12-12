package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.security.service.CustomUserDetailsService;
import org.labonnefranquette.data.security.JWTUtil;
import org.labonnefranquette.data.security.service.JwtBlacklistService;
import org.labonnefranquette.data.services.AuthService;
import org.labonnefranquette.data.services.UserService;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final DtoTools dtoTools;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService,  UserService userService, DtoTools dtoTools) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.dtoTools = dtoTools;
    }

    @Override
    public Map<String, String> signup(UserCreateDto userCreateDto) {
        User userEntity = userService.createUser(userCreateDto);
        return authenticate(dtoTools.convertToDto(userEntity, UserLoginDto.class));
    }
   @Override
   public Map<String, String> authenticate(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDto.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = jwtUtil.generateToken(userDetails.getUsername(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

   @Override
    public void logout(String accessToken, String refreshToken) {
        JwtBlacklistService.addToBlacklist(accessToken);
        JwtBlacklistService.addToBlacklist(refreshToken);

        SecurityContextHolder.clearContext();
    }

   @Override
   public String refresh(String refreshToken) {
        try {
            if (jwtUtil.isValidRefreshToken(refreshToken)) {
                String username = jwtUtil.extractUsername(refreshToken);
                List<String> roles = userDetailsService.loadUserByUsername(username).getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                return jwtUtil.generateToken(username, roles);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

   @Override
    public boolean isConnected() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
   }
}
