package com.marco.ultimabreweryservice.services.inventory;

import com.marco.dtocommoninterface.model.inventory.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-failover")
public interface BeerInventoryFailoverFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();

}
