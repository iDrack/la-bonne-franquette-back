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
        return !this.requestUrl.endsWith("/login");
    }

    //TODO : Retirer avant MEP
    private Boolean UsersRoutes() {
        return !this.requestUrl.endsWith("/users/create");
    }
}
