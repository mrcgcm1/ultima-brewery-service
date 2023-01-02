package com.marco.ultimabreweryservice.services.inventory;

import com.marco.dtocommoninterface.model.inventory.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@Component
@Slf4j
//@Profile("!localdiscovery")
//@ConfigurationProperties(prefix = "com.marco.beer", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "api/v1/beer/{beerId}/inventory";

    public static final String INVENTORY_PATH_TEST = "api/v1/beer/{beerId}/inventory/test";

    public static final String INVENTORY_PATH_ONLY_LIST= "api/v1/beer/{beerId}/inventory/testlist";

    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
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
