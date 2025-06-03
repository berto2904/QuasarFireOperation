package org.meli.service;

import org.meli.dto.SatelliteDataDTO;

import java.util.List;

public interface LocationSplitService extends LocationService {
    void saveSatellite(SatelliteDataDTO satelliteDataDTO);
    List<SatelliteDataDTO> getAllSatellites();
}
