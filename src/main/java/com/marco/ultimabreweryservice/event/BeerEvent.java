package com.marco.ultimabreweryservice.event;

import com.marco.dtocommoninterface.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -6343199200078196341L;

    private  BeerDto beerDto;

}
