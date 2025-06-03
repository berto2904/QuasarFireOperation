package org.meli.repository.impl;

import org.meli.model.Satellite;
import org.meli.repository.SatelliteReadOnlyRepository;
import org.meli.repository.SatelliteReadWriteRepository;
import org.meli.repository.SatelliteWriteableRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("satelliteMemoryStorageRepositoryImpl")
public class SatelliteMemoryStorageRepositoryImpl implements SatelliteReadWriteRepository {

    private final Map<String, Satellite> satelliteMap = new HashMap<>();

    @Override
    public List<Satellite> getSatellites() {
        return new ArrayList<>(satelliteMap.values());
    }

    @Override
    public Optional<Satellite> findByName(String name) {
        return Optional.ofNullable(satelliteMap.get(name.toLowerCase()));
    }

    @Override
    public void saveSatellite(Satellite satellite) {
        if (satellite.getName() == null) {
            throw new IllegalArgumentException("El nombre del satélite no puede ser null");
        }
        satelliteMap.put(satellite.getName().toLowerCase(), satellite);
    }

    public void clear() {
        satelliteMap.clear();
    }

    public boolean isEmpty() {
        return satelliteMap.isEmpty();
    }
}
