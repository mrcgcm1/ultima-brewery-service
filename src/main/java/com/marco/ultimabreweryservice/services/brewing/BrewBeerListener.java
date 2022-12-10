package com.marco.ultimabreweryservice.services.brewing;

import com.marco.dtocommoninterface.event.BrewBeerEvent;
import com.marco.dtocommoninterface.event.NewInventoryEvent;
import com.marco.dtocommoninterface.model.BeerDto;
import com.marco.ultimabreweryservice.config.JmsConfig;
import com.marco.ultimabreweryservice.domain.Beer;
import com.marco.ultimabreweryservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerListener {

    private final JmsTemplate jmsTemplate;
    private final BeerRepository  beerRepository;
    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){

        BeerDto beerDto = event.getBeerDto();
        Beer beer = beerRepository.findById(beerDto.getId()).orElse(null);

        if(beer != null){
            NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
            beerDto.setQuantityOnHand(beer.getMinOnHand());
            jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
        }
    }
}
