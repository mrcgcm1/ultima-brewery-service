package com.marco.ultimabreweryservice.services.inventory;

import com.marco.dtocommoninterface.model.inventory.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("local-discovery")
public class BeerInventoryServiceFeign implements BeerInventoryService{
    private final BeerInventoryServiceFeignClient beerInventoryServiceFeignClient;
    @Override
    public Integer getOnhandInventory(UUID id) {
        log.debug("chiamata al servizio di inventario Feign");

        ResponseEntity<List<BeerInventoryDto>> responseEntity = beerInventoryServiceFeignClient.getOnHandInventory(id);

        Integer onHand = Objects.requireNonNull(responseEntity.getBody()).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
        return onHand;

    }
}
