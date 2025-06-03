package org.meli.repository;

import org.meli.model.Satellite;

import java.util.List;
import java.util.Optional;

public interface SatelliteReadOnlyRepository {
    List<Satellite> getSatellites();
    Optional<Satellite> findByName(String name);
}
