package com.marco.ultimabreweryservice.services;

import com.marco.dtocommoninterface.model.BeerStyleEnum;
import com.marco.ultimabreweryservice.model.BeerPagedList;
import org.springframework.data.domain.PageRequest;
import com.marco.dtocommoninterface.model.BeerDto;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    void deleteById(UUID beerId);

    BeerPagedList listBeers(Integer beerName, BeerStyleEnum beerStyle, PageRequest of, boolean showInventoryOnHand);

    BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand);

    BeerDto getBeerByUpc(String upc);
}
