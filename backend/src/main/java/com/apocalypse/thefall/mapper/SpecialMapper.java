package com.apocalypse.thefall.mapper;

import com.apocalypse.thefall.dto.SpecialDto;
import com.apocalypse.thefall.entity.Special;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialMapper {

    SpecialDto toDto(Special special);

}