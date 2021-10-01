package com.softserveinc.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserveinc.app.dto.ApplicationDTO;
import com.softserveinc.app.entity.ApplicationEntity;
import com.softserveinc.app.exceptions.InvalidVersionValue;
import com.softserveinc.app.mapper.ApplicationMapper;
import com.softserveinc.app.repositories.ApplicationRepository;
import com.softserveinc.app.service.ApplicationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private ApplicationDTO applicationDTO1;
    private ApplicationEntity applicationEntity1;
    private ApplicationEntity applicationEntity2;
    private List<ApplicationEntity> applicationEntities;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(applicationService)
        .build();

        applicationDTO1 = new ApplicationDTO();
        applicationDTO1.setId(1);
        applicationDTO1.setName("Viber");
        applicationDTO1.setVersion("1.09.12");
        applicationDTO1.setContent_rate(3);

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
    }

    @Test
    public void findAll() {
        List<ApplicationEntity> applicationEntities = new ArrayList<>(Arrays.asList(new ApplicationEntity(), new ApplicationEntity()));
        when(applicationRepository.getAllApps()).thenReturn(applicationEntities);
        applicationService.getAll();
        verify(applicationMapper, times(applicationEntities.size())).toApplicationDTO(any(ApplicationEntity.class));

    }

    @Test
    public void createNewApplication_NotValidVersion() throws Exception {
        String content = "{\n"
                + "  \"name\": \"Viber\",\n"
                + "  \"version\": \"1.09.hi\",\n"
                + "  \"content_rate\": 3\n"
                + "}";

        ObjectMapper mapper = new ObjectMapper();
        ApplicationDTO application = mapper.readValue(content, ApplicationDTO.class);

        mockMvc.perform(post("/app").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(result ->
                Assertions.assertThrows(InvalidVersionValue.class, () -> applicationService
                        .saveApplication(application)));
    }

    @Test
    public void findNewestApplication_NotFound() throws Exception {
        Set<Integer> appIds = new HashSet<>();
        appIds.add(1);
        appIds.add(2);

        Mockito.when(applicationService.getNewestApplication(appIds))
                .thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/compare?ids=1,2"))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertEquals(0, result.getResponse().getContentLength()));
    }
}