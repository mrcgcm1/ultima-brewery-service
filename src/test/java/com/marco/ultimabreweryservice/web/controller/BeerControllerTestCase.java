package com.marco.ultimabreweryservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.dtocommoninterface.model.BeerDto;
import com.marco.dtocommoninterface.model.BeerStyleEnum;
import com.marco.ultimabreweryservice.bootstrap.BeerLoader;
import com.marco.ultimabreweryservice.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setUp(){
        validBeerGet = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Birra").beerStyle(BeerStyleEnum.ALE)
                .upc(BeerLoader.BEER_1_UPC)
                .price(new BigDecimal("10")).build();
        validBeerPost = BeerDto.builder()
                .beerName("Birra")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(BeerLoader.BEER_2_UPC)
                .price(new BigDecimal("10")).build();
    }

//    @Test
//    public void ottieniUnaBirra() throws Exception {
//        given(service.getBeerById(any(UUID.class))).willReturn(validBeerGet);
//
//        mockMvc.perform(get("/api/v1/beer/" + validBeerGet.getId().toString() + "?showInventoryOnHand=false").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
//        ;
//
//    }
    @Test
    public void ottieniUnaBirraConInventario() throws Exception {
        given(service.getBeerById(any(UUID.class), anyBoolean())).willReturn(validBeerGet);

        mockMvc.perform(get("/api/v1/beer/" + validBeerGet.getId().toString() + "?showInventoryOnHand=false").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
        ;

    }
    @Test
    public void ottieniUnaBirra() throws Exception {
        given(service.getBeerById(any(UUID.class), anyBoolean())).willReturn(validBeerGet);

        mockMvc.perform(get("/api/v1/beer/" + validBeerGet.getId().toString() ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
        ;

    }

    @Test
    public void ottieniUnaBirraConUpc() throws Exception {
        given(service.getBeerByUpc(any(String.class))).willReturn(validBeerGet);

        mockMvc.perform(get("/api/v1/beerUpc/" + validBeerGet.getUpc() ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
        ;

    }


    @Test
    public void inserisciUnaBirra() throws Exception {

        BeerDto beerDto = validBeerPost;
      //  beerDto.setId(null);

        BeerDto savedBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("mia birra").beerStyle(BeerStyleEnum.PILSNER).build();

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
