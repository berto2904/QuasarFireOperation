package org.meli.service.impl;

import lombok.RequiredArgsConstructor;
import org.meli.dto.SatelliteDataDTO;
import org.meli.model.Point;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteMemoryStorage;
import org.meli.repository.SatelliteRepository;
import org.meli.service.LocationSplitService;
import org.meli.util.TrilaterationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationSplitServiceImpl implements LocationSplitService {

    private final SatelliteMemoryStorage memoryStorage;
    private final SatelliteRepository satelliteRepository;

    @Override
    public Point getLocation(double[] distances) {
        List<Point> points = memoryStorage.getAllSatellites()
                .stream()
                .map(Satellite::getPosition)
                .toList();
        return TrilaterationUtils.calculateLocation(points, distances);
    }

    @Override
    public void saveSatellite(SatelliteDataDTO satelliteDataDTO) {
        Optional<Satellite> optionalSatellite = satelliteRepository.findByName(satelliteDataDTO.getName());

        if (optionalSatellite.isEmpty())
            throw new IllegalArgumentException("Satelite no existe");

        Satellite newSatellite = Satellite.builder()
                .name(satelliteDataDTO.getName())
                .distance(satelliteDataDTO.getDistance())
                .message(satelliteDataDTO.getMessage())
                .position(optionalSatellite.get().getPosition())
                .build();

        memoryStorage.saveSatellite(newSatellite);
    }

    @Override
    public List<SatelliteDataDTO> getAllSatellites() {
        List<Satellite> satellitesOnMemory = memoryStorage.getAllSatellites();
        return satellitesOnMemory.stream()
                .map(s -> SatelliteDataDTO.builder()
                        .name(s.getName())
                        .distance(s.getDistance())
                        .message(s.getMessage())
                        .build())
                .toList();
    }
}
