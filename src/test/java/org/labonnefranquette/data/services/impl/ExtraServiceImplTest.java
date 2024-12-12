package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.repository.ExtraRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ExtraServiceImplTest {

    @Mock
    private ExtraRepository extraRepository;

    @InjectMocks
    private ExtraServiceImpl extraService;

    private Extra extra;

    @BeforeEach
    public void setup() {
        extra = new Extra();
        extra.setId(1L);
        extra.setNom("Test");
    }

    @Test
    public void findAllExtrasSuccessfully() {
        when(extraRepository.findAll()).thenReturn(Arrays.asList(extra));

        List<Extra> result = extraService.findAll();

        assertEquals(1, result.size());
        assertEquals(extra, result.get(0));
    }

    @Test
    public void findExtraByIdSuccessfully() {
        when(extraRepository.findById(anyLong())).thenReturn(Optional.of(extra));

        Optional<Extra> result = extraService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(extra, result.get());
    }

    @Test
    public void findExtraByIdNotFound() {
        when(extraRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Extra> result = extraService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void createExtraSuccessfully() {
        when(extraRepository.save(any(Extra.class))).thenReturn(extra);

        Extra result = extraService.create(extra);

        assertEquals(extra, result);
    }

    @Test
    public void deleteExtraSuccessfully() {
        doNothing().when(extraRepository).deleteById(anyLong());

        extraService.deleteById(1L);

        verify(extraRepository, times(1)).deleteById(anyLong());
    }
}