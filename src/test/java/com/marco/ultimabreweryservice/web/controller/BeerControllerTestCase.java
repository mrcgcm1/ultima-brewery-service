package com.marco.ultimabreweryservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.ultimabreweryservice.model.BeerDto;
import com.marco.ultimabreweryservice.services.BeerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTestCase {

    @MockBean
    BeerService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    BeerDto validBeerGet;

    BeerDto validBeerPost;

    @Before
    public void setUp(){
        validBeerGet = BeerDto.builder().id(UUID.randomUUID()).beerName("Birra").beerStyle("Pale Ale").upc(1L).price(new BigDecimal("10")).build();
        validBeerPost = BeerDto.builder().beerName("Birra").beerStyle("Pale Ale").upc(1L).price(new BigDecimal("10")).build();
    }

    @Test
    public void ottieniUnaBirra() throws Exception {
        given(service.getBeerById(any(UUID.class))).willReturn(validBeerGet);

        mockMvc.perform(get("/api/v1/beer/" + validBeerGet.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
        ;

    }

    @Test
    public void inserisciUnaBirra() throws Exception {

        BeerDto beerDto = validBeerPost;
      //  beerDto.setId(null);

        BeerDto savedBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("mia birra").beerStyle("My Style").build();

        String beerToJson = mapper.writeValueAsString(beerDto);

        given(service.saveNewBeer(any())).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beer/").contentType(MediaType.APPLICATION_JSON).content(beerToJson))
                .andExpect(status().isCreated());

    }
    @Test
    public void aggiornaUnaBirra() throws Exception {

        BeerDto beerDto = validBeerGet;

        beerDto.setId(null);
        String beerToJson = mapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(beerToJson))
                .andExpect(status().isNoContent());

        then(service).should().updateBeer(any(), any());
    }

}
