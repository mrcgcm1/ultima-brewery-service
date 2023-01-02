package com.marco.ultimabreweryservice.services.inventory;

import com.marco.dtocommoninterface.model.inventory.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BeerInventoryFeignClientFailoverService implements BeerInventoryServiceFeignClient{

    BeerInventoryFailoverFeignClient beerInventoryFailoverFeignClient;
    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
        return beerInventoryFailoverFeignClient.getOnHandInventory();
    }

    @Override
    public ResponseEntity<Integer> getOnHandInventoryTest(UUID beerId) {
        return null;
    }

    @Override
    public List<BeerInventoryDto> getOnHandInventoryList(UUID beerId) {
        return null;
    }
}
