package com.marco.ultimabreweryservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnhandInventory(UUID id);
}
