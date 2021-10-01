package com.softserveinc.app.service;

import com.softserveinc.app.dto.ApplicationDTO;
import com.softserveinc.app.entity.ApplicationEntity;
import com.softserveinc.app.exceptions.InvalidContentRateException;
import com.softserveinc.app.exceptions.InvalidVersionValue;
import com.softserveinc.app.exceptions.NotEnoughQueryVariables;
import com.softserveinc.app.mapper.ApplicationMapper;
import com.softserveinc.app.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    public final ApplicationRepository applicationRepository;
    public final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }


    @Override
    public List<ApplicationDTO> getAll() {
        return applicationRepository.getAllApps()
                .stream()
                .map(applicationMapper::toApplicationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean saveApplication(ApplicationDTO applicationDTO) {

        ArrayList<Integer> validContentRates = new ArrayList<>((Arrays.asList(3, 7, 12, 16, 18)));

        if (applicationDTO != null) {
            boolean matches = Pattern.matches("[0-9]+(\\.[0-9]+)*", applicationDTO.getVersion());
            if (matches) {
                ApplicationEntity applicationEntity = getApplicationEntityFromApplicationDTO(applicationDTO);
                if (validContentRates.contains(applicationEntity.getContent_rate())) {
                    applicationRepository.saveApplication(
                            applicationEntity.getName(), applicationEntity.getVersion(), applicationEntity.getContent_rate());
                    return true;
                } else {
                    throw new InvalidContentRateException("Invalid content rate was entered");
                }
            } else {
                throw new InvalidVersionValue("Invalid version value was entered");
            }
        }
        return false;
    }

    @Override
    public ApplicationDTO getById(Integer id) {
        ApplicationEntity applicationEntity = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with %s not found", id)));
        return applicationMapper.toApplicationDTO(applicationEntity);
    }

    @Override
    public Boolean deleteById(Integer id) {
        ApplicationEntity applicationEntity = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with %s not found", id)));
        applicationRepository.deleteById(id);
        return true;
    }

    @Override
    public ApplicationDTO getNewestApplication(Set<Integer> appIds) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        if (appIds != null && appIds.size() > 1) {
            List<ApplicationDTO> applicationDTOList = applicationRepository.findAllByIds(appIds)
                    .stream()
                    .map(applicationMapper::toApplicationDTO)
                    .collect(Collectors.toList());
            if (!applicationDTOList.isEmpty()) applicationDTO = applicationDTOList.get(0);

            for (int i = 0; i < applicationDTOList.size() - 1; i++) {
                applicationDTO = compareApplicationsByVersion(applicationDTO, applicationDTOList.get(i + 1));
            }
            return applicationDTO;

        } else throw new NotEnoughQueryVariables("Less than 2 query variables was entered");
    }

    @Override
    public Integer getAmountByContentRates(Set<Integer> contentRates) {
        if (contentRates != null) {
            return applicationRepository.getCountOfAppsByContentRates(contentRates);
        }
        return null;
    }

    private ApplicationEntity getApplicationEntityFromApplicationDTO(ApplicationDTO applicationDTO) {
        Integer appId = applicationDTO.getId();
        ApplicationEntity mappedEntity;
        if (appId == null) {
            mappedEntity = applicationMapper.toApplicationEntity(applicationDTO);
        } else {
            ApplicationEntity appById = applicationRepository.findById(appId)
                    .orElseThrow(EntityNotFoundException::new);
            //Don’t create a new instance of the target type but instead update an existing instance of that type
            mappedEntity = applicationMapper.updateApplicationEntityFromDTO(applicationDTO, appById);
        }

        return mappedEntity;
    }

    private ApplicationDTO compareApplicationsByVersion(ApplicationDTO currentApplicationDTO, ApplicationDTO nextApplicationDTO) {
        String[] currVersionParts = currentApplicationDTO.getVersion().split("\\.");
        String[] nextVersionParts = nextApplicationDTO.getVersion().split("\\.");
        int length = Math.max(currVersionParts.length, nextVersionParts.length);
        for (int i = 0; i < length; i++) {
            int currPart = i < currVersionParts.length ?
                    Integer.parseInt(currVersionParts[i]) : 0;
            int nextPart = i < nextVersionParts.length ?
                    Integer.parseInt(nextVersionParts[i]) : 0;

            if (currPart < nextPart)
                return nextApplicationDTO;
            if (currPart > nextPart)
                return currentApplicationDTO;
            else continue;
        }
        return null;
    }
}
