package com.marco.ultimabreweryservice.web.mappers;

import com.marco.dtocommoninterface.model.BeerDto;
import com.marco.ultimabreweryservice.domain.Beer;
import com.marco.ultimabreweryservice.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;

    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDto(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        return mapper.beerDtoToBeer(dto);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }
}
