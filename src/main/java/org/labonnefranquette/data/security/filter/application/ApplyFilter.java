package org.labonnefranquette.data.security.filter.application;

public class ApplyFilter {

    private String requestUrl;

    public ApplyFilter(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Boolean doFilterOne() {return !this.AuthRoutes() && !this.UsersRoutes();}
    public Boolean doFilterTwo() {return this.AdminRoutes() && !this.UsersRoutes();}

    private Boolean AuthRoutes() {
        return  this.requestUrl.endsWith("/login");
    }
    //TODO : Retirer avant MEP
    private Boolean UsersRoutes() {
        return  this.requestUrl.endsWith("/create") ||
                this.requestUrl.endsWith("/test-connection") ||
                this.requestUrl.endsWith("/is-connected");
    }
    private Boolean AdminRoutes() {
        return this.requestUrl.contains("admin");
    }

}
