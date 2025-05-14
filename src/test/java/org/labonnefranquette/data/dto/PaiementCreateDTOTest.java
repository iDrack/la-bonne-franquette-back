package org.labonnefranquette.data.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.PaymentCreateDTO;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaiementCreateDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void shouldCreatePaiementCreateDTOWithValidData() {
        PaymentCreateDTO dto = new PaymentCreateDTO("CREDIT_CARD", 500, Collections.emptyList(), Collections.emptyList());

        Set<ConstraintViolation<PaymentCreateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenTypeIsNull() {
        PaymentCreateDTO dto = new PaymentCreateDTO(null, 500, Collections.emptyList(), Collections.emptyList());

        Set<ConstraintViolation<PaymentCreateDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    void shouldHandleNullArticles() {
        PaymentCreateDTO dto = new PaymentCreateDTO("CREDIT_CARD", 500, null, Collections.emptyList());

        Set<ConstraintViolation<PaymentCreateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}