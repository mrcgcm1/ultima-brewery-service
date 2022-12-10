package com.marco.ultimabreweryservice.services.brewing;

import com.marco.ultimabreweryservice.config.JmsConfig;
import com.marco.ultimabreweryservice.domain.Beer;
import com.marco.ultimabreweryservice.event.BrewBeerEvent;
import com.marco.ultimabreweryservice.repositories.BeerRepository;
import com.marco.ultimabreweryservice.services.BeerInventoryService;
import com.marco.ultimabreweryservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingEventServiceImpl implements BrewingEventService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper mapper;

    @Override
    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(b -> {
            Integer invOH = beerInventoryService.getOnhandInventory(b.getId());

            Integer minOh = b.getMinOnHand();
            log.debug("Min on Hand is " + (minOh != null ? minOh : " niente"));

            log.debug("Inventory is " + (invOH != null ? invOH : " niente"));

          //  if (minOh >= invOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(mapper.beerToBeerDto(b)));
           // }

        });
    }
}
