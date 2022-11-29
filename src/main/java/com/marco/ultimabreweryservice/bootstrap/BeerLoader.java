package com.marco.ultimabreweryservice.bootstrap;

import com.marco.dtocommoninterface.model.BeerStyleEnum;
import com.marco.ultimabreweryservice.domain.Beer;
import com.marco.ultimabreweryservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

//@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Mia birra")
                    .beerStyle(BeerStyleEnum.ALE)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Tua birra")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .quantityToBrew(400)
                    .minOnHand(2)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("15.95"))
                    .build());
            beerRepository.save(Beer.builder()
                    .beerName("Sua birra")
                    .beerStyle(BeerStyleEnum.PILSNER)
                    .quantityToBrew(600)
                    .minOnHand(24)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("18.95"))
                    .build());
        }

        System.out.println("Birre caricate " + beerRepository.count());
    }
}
