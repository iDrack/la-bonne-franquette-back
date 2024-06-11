package org.labonnefranquette.data.security.filter.application;

public class ApplyFilter {

    private String requestUrl;

    public ApplyFilter(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Boolean doFilterOne() {
        return this.AuthRoutes() && this.UsersRoutes();
    }

    public Boolean doFilterTwo() {
        return this.AuthRoutes() && this.UsersRoutes();
    }

    private Boolean AuthRoutes() {
        return !this.requestUrl.endsWith("/login") &&
                !this.requestUrl.endsWith("/verify-token") &&
                !this.requestUrl.endsWith("/update-token") &&
                !this.requestUrl.endsWith("/testConnection");
    }

    private Boolean UsersRoutes() {
        return !this.requestUrl.endsWith("/users/create");
    }
}
