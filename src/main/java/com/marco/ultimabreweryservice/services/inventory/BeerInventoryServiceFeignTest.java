package com.marco.ultimabreweryservice.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("localdiscoverytest")
public class BeerInventoryServiceFeignTest implements BeerInventoryService{
    private final BeerInventoryServiceFeignClient beerInventoryServiceFeignClient;
    @Override
    public Integer getOnhandInventory(UUID id) {
        log.debug("chiamata al servizio di inventario Feign");

        ResponseEntity<Integer> responseEntityInt = beerInventoryServiceFeignClient.getOnHandInventoryTest(id);

        Integer onHand = responseEntityInt.getBody();

        return onHand;

    }
}
