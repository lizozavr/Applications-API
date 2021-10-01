package com.softserveinc.app.service;

import com.softserveinc.app.dto.ApplicationDTO;
import com.softserveinc.app.entity.ApplicationEntity;
import com.softserveinc.app.exceptions.NotEnoughQueryVariables;
import com.softserveinc.app.mapper.ApplicationMapper;
import com.softserveinc.app.repositories.ApplicationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationServiceImplTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private ApplicationEntity applicationEntity1;
    private ApplicationEntity applicationEntity2;
    private List<ApplicationEntity> applicationEntities;
    private ApplicationDTO applicationDTO1;
    private ApplicationDTO applicationDTO2;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(applicationService)
                .build();

        applicationEntity1 = new ApplicationEntity();
        applicationEntity1.setId(1);
        applicationEntity1.setName("Facebook");
        applicationEntity1.setVersion("1.1.9");
        applicationEntity1.setContent_rate(3);

        applicationEntity2 = new ApplicationEntity();
        applicationEntity2.setId(2);
        applicationEntity2.setName("Instaram");
        applicationEntity2.setVersion("5.9.4");
        applicationEntity2.setContent_rate(17);

        applicationEntities = new ArrayList<>(Arrays.asList(applicationEntity1, applicationEntity2));

        applicationDTO1 = new ApplicationDTO();
        applicationDTO1.setId(1);
        applicationDTO1.setName("Facebook");
        applicationDTO1.setVersion("1.1.9");
        applicationDTO1.setContent_rate(3);

        applicationDTO2 = new ApplicationDTO();
        applicationDTO2.setId(2);
        applicationDTO2.setName("Instaram");
        applicationDTO2.setVersion("5.9.4");
        applicationDTO2.setContent_rate(17);
    }


    @Test
    public void getNewestApplication() {
        Set<Integer> appIds = new HashSet<>();
        appIds.add(1);
        appIds.add(2);

        Mockito.when(applicationRepository.findAllByIds(appIds)).thenReturn(applicationEntities);
        when(applicationMapper.toApplicationDTO(applicationEntity1)).thenReturn(applicationDTO1);
        when(applicationMapper.toApplicationDTO(applicationEntity2)).thenReturn(applicationDTO2);

        applicationService.getNewestApplication(appIds);
        verify(applicationMapper, times(2)).toApplicationDTO(any(ApplicationEntity.class));
        assertEquals(applicationDTO2, applicationService.getNewestApplication(appIds));
    }

    @Test
    public void getNewestApplication_NotEnoughQueryVariables(){
        Set<Integer> appIds = new HashSet<>();
        appIds.add(1);

        assertThrows(NotEnoughQueryVariables.class, () -> applicationService
                .getNewestApplication(appIds));
    }
}