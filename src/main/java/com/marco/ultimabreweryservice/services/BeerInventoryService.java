package com.marco.ultimabreweryservice.services;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnhandInventory(UUID id);
}
