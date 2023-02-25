package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.samples.petclinic.web.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    ClinicService clinicService;

    @Autowired
    OwnerController ownerController;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void testInitCreationForm() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testNewOwnerPostValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("address", "123 Duval St ")
                        .param("city", "Key West")
                        .param("telephone", "3151231234"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testNewOwnerPostNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("city", "Key West"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address", "telephone"));
    }


    @Test
    void testProcessFindForm() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners")
                        .param("lastName", "dont find me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
    }

    @Test
    void testProcessFindFormResultSize1() throws Exception {
        //given
        Owner owner = mock(Owner.class);
        given(owner.getId()).willReturn(1);
        given(clinicService.findOwnerByLastName(anyString())).willReturn(Collections.singleton(owner));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners")
                        .param("lastName", "find me"))
                //then
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
        //and then
        then(clinicService).should().findOwnerByLastName(anyString());
    }
    @Test
    void testProcessFindFormResultSize2() throws Exception {
        //given
        given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(), new Owner()));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("selections"))
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
        //and then
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("");
    }

    @Test
    void testProcessUpdateOwnerForm() throws Exception {
        mockMvc.perform(post("/owners/1/edit")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("address", "123 Duval St ")
                        .param("city", "Key West")
                        .param("telephone", "3151231234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessUpdateOwnerFormNotValid() throws Exception {
        mockMvc.perform(post("/owners/1/edit")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("city", "Key West"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner", "address", "telephone"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));;
    }
}