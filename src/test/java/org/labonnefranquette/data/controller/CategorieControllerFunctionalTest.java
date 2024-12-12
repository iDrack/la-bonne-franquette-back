package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategorieControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getAllCategories_shouldReturnListOfCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categorie")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nom").exists())
                .andExpect(jsonPath("$[0].categorieType").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getAllCategories_shouldReturnAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categorie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void getAllCategories_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/categorie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllCategories_shouldReturnForbidden_becauseFalseToken() throws Exception {
        mockMvc.perform(get("/api/v1/categorie")
                        .header("auth-token", "false-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}