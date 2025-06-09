package org.labonnefranquette.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("smoke")
public class SmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailService mailService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    void allGetRoutesReturnNo500() throws Exception {
        var handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<String> getRoutes = handlerMethods.keySet().stream()
                .filter(handlerMethod -> handlerMethod.getMethodsCondition().getMethods().contains(org.springframework.web.bind.annotation.RequestMethod.GET))
                .map(RequestMappingInfo::getPatternsCondition)
                .filter(Objects::nonNull)
                .flatMap(pc -> pc.getPatterns().stream())
                .collect(Collectors.toSet());

        for (String route : getRoutes) {
            if (route.contains("{")) continue;

            var result = mockMvc.perform(get(route))
                    .andReturn()
                    .getResponse();

            int status = result.getStatus();
            assertThat(status)
                    .withFailMessage("La route %s a renvoyé %d", route, status)
                    .isNotEqualTo(500);
        }
    }
}
