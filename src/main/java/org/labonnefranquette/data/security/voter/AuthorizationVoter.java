package org.labonnefranquette.data.security.voter;

import org.labonnefranquette.data.model.enums.Roles;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorizationVoter {

    public Boolean vote(String url, String roles) {
        if (!this.isAdminRoutes(url)) {
            return true;
        }
        return Objects.equals(roles, Roles.ROLE_MANAGER.name()) || Objects.equals(roles, Roles.ROLE_ADMIN.name());
    }

    private Boolean isAdminRoutes(String url) {
        return url.contains("admin");
    }

}
