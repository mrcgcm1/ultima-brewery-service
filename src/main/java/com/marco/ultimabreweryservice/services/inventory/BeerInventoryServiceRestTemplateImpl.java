package com.marco.ultimabreweryservice.services.inventory;

import com.marco.dtocommoninterface.model.inventory.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
/*
 Non rinominare la classe referenziata nell'application.properies
 */
@Component
@Slf4j
@Profile("localdiscoveryRest")
@ConfigurationProperties(prefix = "com.marco.beer", ignoreUnknownFields = true)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "api/v1/beer/{beerId}/inventory";

    public static final String INVENTORY_PATH_TEST = "api/v1/beer/{beerId}/inventory/test";

    public static final String INVENTORY_PATH_ONLY_LIST= "api/v1/beer/{beerId}/inventory/testlist";

    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder builder,
                                                @Value("${com.marco.beer.inventory-user}") String inventoryUser
    , @Value("${com.marco.beer.inventory-password}") String inventoryPassword) {

        this.restTemplate = builder
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    @Override
    public Integer getOnhandInventory(UUID id) {

        log.debug("chiamata al servizio di inventario");

        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                }, id);

        Integer onHand = Objects.requireNonNull(responseEntity.getBody()).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
        return onHand;
    }
}
