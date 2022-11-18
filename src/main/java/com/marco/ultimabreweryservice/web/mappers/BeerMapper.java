package com.marco.ultimabreweryservice.web.mappers;

import com.marco.ultimabreweryservice.domain.Beer;
import com.marco.ultimabreweryservice.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);
}
