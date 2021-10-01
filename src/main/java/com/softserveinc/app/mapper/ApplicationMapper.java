package com.softserveinc.app.mapper;

import com.softserveinc.app.dto.ApplicationDTO;
import com.softserveinc.app.entity.ApplicationEntity;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationDTO toApplicationDTO(ApplicationEntity applicationEntity);

    ApplicationEntity toApplicationEntity(ApplicationDTO applicationDTO);

    ApplicationEntity updateApplicationEntityFromDTO(ApplicationDTO applicationDTO, @MappingTarget ApplicationEntity applicationEntity);

}
