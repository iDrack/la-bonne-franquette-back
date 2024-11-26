package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class DtoToolsTest {

    private DtoTools dtoTools;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dtoTools = new DtoTools();
    }

    @Test
    public void convertToDto_returnsCorrectDto() {
        TestEntity entity = new TestEntity();
        entity.setId(1);
        entity.setName("Test");

        TestDto dto = dtoTools.convertToDto(entity, TestDto.class);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void convertToEntity_returnsCorrectEntity() {
        TestDto dto = new TestDto();
        dto.setId(1);
        dto.setName("Test");

        TestEntity entity = dtoTools.convertToEntity(dto, TestEntity.class);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
    }
}
