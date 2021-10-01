package com.softserveinc.app.service;

import com.softserveinc.app.dto.ApplicationDTO;

import java.util.List;
import java.util.Set;

public interface ApplicationService {

    List<ApplicationDTO> getAll();

    Boolean saveApplication(ApplicationDTO applicationDTO);

    ApplicationDTO getById(Integer id);

    Boolean deleteById(Integer id);

    ApplicationDTO getNewestApplication(Set<Integer> ids);

    Integer getAmountByContentRates(Set<Integer> contentRate);

}
