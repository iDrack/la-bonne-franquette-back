package org.example.labonnefranquette.security.voter;

import org.example.labonnefranquette.model.enums.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorizationVoter {

    public Boolean vote(String url, String roles){

        if (!this.isAdminRoutes(url)) {
            return true;
        }

        return Objects.equals(roles, Roles.ROLE_MANAGER.name()) || Objects.equals(roles, Roles.ROLE_ADMIN.name());
    }

    private Boolean isAdminRoutes(String url) {
        return url.contains("admin");
    }

}
