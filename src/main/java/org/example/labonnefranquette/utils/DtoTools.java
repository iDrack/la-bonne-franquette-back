package org.example.labonnefranquette.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoTools {
    private final ModelMapper modelMapper;

    public DtoTools() {
        this.modelMapper = new ModelMapper();
    }

    public <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
