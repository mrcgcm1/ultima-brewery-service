package com.marco.ultimabreweryservice.web.controller;

import com.marco.ultimabreweryservice.model.BeerDto;
import com.marco.ultimabreweryservice.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping("/api/v1/beer")
@RestController
@AllArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> handlePost(@Valid @RequestBody BeerDto beerDto){
        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer" + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> handleUpdate(@PathVariable("beerId") UUID beerId,@Valid @RequestBody BeerDto beerDto){
        BeerDto savedDto = beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);

    }
@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationeErrorHandler(ConstraintViolationException ex){
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());

    ex.getConstraintViolations().forEach(c ->{
        errors.add(c.getPropertyPath() + " : " + c.getMessage());
    });

    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

}

}
