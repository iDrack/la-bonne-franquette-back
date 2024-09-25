package org.labonnefranquette.data.security.filter.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApplyFilterTest {

    @Test
    public void testDoFilterOne_AuthRoutes_Login() {
        ApplyFilter filter = new ApplyFilter("/login");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testDoFilterOne_AuthRoutes_TestConnection() {
        ApplyFilter filter = new ApplyFilter("/testConnection");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testDoFilterOne_AuthRoutes_OtherRoute() {
        ApplyFilter filter = new ApplyFilter("/otherRoute");
        boolean result = filter.doFilterOne();
        assertTrue(result);
    }

    @Test
    public void testDoFilterOne_UsersRoutes_Create() {
        ApplyFilter filter = new ApplyFilter("/users/create");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testDoFilterOne_UsersRoutes_Other() {
        ApplyFilter filter = new ApplyFilter("/users/other");
        boolean result = filter.doFilterOne();
        assertTrue(result);
    }

    @Test
    public void testDoFilterTwo_AdminRoutes_Dashboard() {
        ApplyFilter filter = new ApplyFilter("/admin/dashboard");
        boolean result = filter.doFilterTwo();
        assertTrue(result);
    }

    @Test
    public void testDoFilterTwo_AdminRoutes_UserDashboard() {
        ApplyFilter filter = new ApplyFilter("/user/dashboard");
        boolean result = filter.doFilterTwo();
        assertFalse(result);
    }

    @Test
    public void testAuthRoutes_Login() {
        ApplyFilter filter = new ApplyFilter("/login");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testAuthRoutes_TestConnection() {
        ApplyFilter filter = new ApplyFilter("/testConnection");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testAuthRoutes_OtherRoute() {
        ApplyFilter filter = new ApplyFilter("/otherRoute");
        boolean result = filter.doFilterOne();
        assertTrue(result);
    }

    @Test
    public void testUsersRoutes_Create() {
        ApplyFilter filter = new ApplyFilter("/users/create");
        boolean result = filter.doFilterOne();
        assertFalse(result);
    }

    @Test
    public void testUsersRoutes_Other() {
        ApplyFilter filter = new ApplyFilter("/users/other");
        boolean result = filter.doFilterOne();
        assertTrue(result);
    }

    @Test
    public void testAdminRoutes_Dashboard() {
        ApplyFilter filter = new ApplyFilter("/admin/dashboard");
        boolean result = filter.doFilterTwo();
        assertTrue(result);
    }

    @Test
    public void testAdminRoutes_UserDashboard() {
        ApplyFilter filter = new ApplyFilter("/user/dashboard");
        boolean result = filter.doFilterTwo();
        assertFalse(result);
    }
}
