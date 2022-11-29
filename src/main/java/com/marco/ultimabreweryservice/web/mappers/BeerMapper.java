package com.marco.ultimabreweryservice.web.mappers;

import com.marco.ultimabreweryservice.domain.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import com.marco.dtocommoninterface.model.BeerDto;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);

    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
