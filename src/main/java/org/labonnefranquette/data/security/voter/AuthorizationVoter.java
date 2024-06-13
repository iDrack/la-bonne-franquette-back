package org.labonnefranquette.data.security.voter;

import org.labonnefranquette.data.model.enums.Roles;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorizationVoter {

    public Boolean voteAdminRole(String roles) {
        return roles.contains(Roles.ROLE_ADMIN.name());
    }
}
