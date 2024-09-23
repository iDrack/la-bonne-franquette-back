package org.labonnefranquette.data.security.filter.voter;

import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.security.voter.AuthorizationVoter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizationVoterTest {

    @Test
    void testVoteAdminRole_WithAdminRole() {
        AuthorizationVoter voter = new AuthorizationVoter();
        String roles = "ROLE_ADMIN";
        boolean result = voter.voteAdminRole(roles);
        assertTrue(result);
    }

    @Test
    void testVoteAdminRole_WithoutAdminRole() {
        AuthorizationVoter voter = new AuthorizationVoter();
        String roles = "ROLE_USER";
        boolean result = voter.voteAdminRole(roles);
        assertFalse(result);
    }

    @Test
    void testVoteAdminRole_WithMultipleRolesIncludingAdmin() {
        AuthorizationVoter voter = new AuthorizationVoter();
        String roles = "ROLE_USER,ROLE_ADMIN";
        boolean result = voter.voteAdminRole(roles);
        assertTrue(result);
    }

    @Test
    void testVoteAdminRole_WithEmptyRoles() {
        AuthorizationVoter voter = new AuthorizationVoter();
        String roles = "";
        boolean result = voter.voteAdminRole(roles);
        assertFalse(result);
    }
}

