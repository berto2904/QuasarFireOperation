package org.meli.repository;

import org.meli.model.Satellite;

public interface SatelliteWriteableRepository {
    void saveSatellite(Satellite satellite);
    boolean clearAll();
}
