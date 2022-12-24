package com.marco.ultimabreweryservice.web.controller;

import com.marco.dtocommoninterface.model.BeerStyleEnum;
import com.marco.ultimabreweryservice.services.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.marco.dtocommoninterface.model.BeerDto;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping(BeerController.API_URL)
@RestController
@AllArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    public static final String API_URL ="/api/v1";
    private final BeerService beerService;

    @GetMapping(path = "beer",produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeer(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize,
                                                  @RequestParam(value = "beerName", required = false) Integer beerName,
                                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                  @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), getShowInventoryOnHand(showInventoryOnHand));
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    private boolean getShowInventoryOnHand(Boolean showInventoryOnHand) {
        return showInventoryOnHand == null ? false : showInventoryOnHand;
    }

    @GetMapping({"beer/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        ResponseEntity<BeerDto> tResponseEntity = new ResponseEntity<>(beerService.getBeerById(beerId, getShowInventoryOnHand(showInventoryOnHand)), HttpStatus.OK);
        return tResponseEntity;
    }

    @GetMapping({ "beerUpc"+ "/{upc}"})
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc) {
        ResponseEntity<BeerDto> tResponseEntity = new ResponseEntity<>(beerService.getBeerByUpc(upc), HttpStatus.OK);
        return tResponseEntity;
    }

    @PostMapping(path = "beer")
    public ResponseEntity<BeerDto> handlePost(@Valid @RequestBody BeerDto beerDto) {
        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer" + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @PutMapping({"beer/{beerId}"})
    public ResponseEntity<BeerDto> handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);

    }


    @DeleteMapping({"beer/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("beerId") UUID beerId) {
        beerService.deleteById(beerId);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationeErrorHandler(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());

        ex.getConstraintViolations().forEach(c -> {
            errors.add(c.getPropertyPath() + " : " + c.getMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

}
