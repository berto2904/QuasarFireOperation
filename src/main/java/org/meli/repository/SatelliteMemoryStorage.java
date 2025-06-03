package org.meli.repository;

import org.meli.model.Satellite;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SatelliteMemoryStorage {
    private final Map<String, Satellite> satelliteMap = new HashMap<>();

    public void saveSatellite(Satellite satellite) {
        if (satellite.getName() == null) {
            throw new IllegalArgumentException("El nombre del satélite no puede ser null");
        }
        satelliteMap.put(satellite.getName().toLowerCase(), satellite);
    }

    public List<Satellite> getAllSatellites() {
        return new ArrayList<>(satelliteMap.values());
    }

    public void clear() {
        satelliteMap.clear();
    }

    public boolean isEmpty() {
        return satelliteMap.isEmpty();
    }

    public Optional<Satellite> getSatelliteByName(String name) {
        return Optional.ofNullable(satelliteMap.get(name.toLowerCase()));
    }
}
