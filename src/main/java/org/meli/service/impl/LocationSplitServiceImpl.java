package org.meli.service.impl;

import lombok.RequiredArgsConstructor;
import org.meli.dto.SatelliteDataDTO;
import org.meli.model.Point;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteReadWriteRepository;
import org.meli.repository.impl.SatelliteMemoryStorageRepositoryImpl;
import org.meli.repository.SatelliteReadOnlyRepository;
import org.meli.service.LocationSplitService;
import org.meli.util.TrilaterationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationSplitServiceImpl implements LocationSplitService {

    private final SatelliteReadWriteRepository memoryStorage;
    private final SatelliteReadOnlyRepository jsonStorage;

    public LocationSplitServiceImpl(
            @Qualifier("satelliteMemoryStorageRepositoryImpl") SatelliteReadWriteRepository memoryStorage,
            @Qualifier("satelliteJsonFileRepositoryImpl") SatelliteReadOnlyRepository jsonStorage) {
        this.memoryStorage = memoryStorage;
        this.jsonStorage = jsonStorage;
    }


    @Override
    public Point getLocation(double[] distances) {
        List<Point> points = memoryStorage.getSatellites()
                .stream()
                .map(Satellite::getPosition)
                .toList();
        return TrilaterationUtils.calculateLocation(points, distances);
    }

    @Override
    public void saveSatellite(SatelliteDataDTO satelliteDataDTO) {
        Optional<Satellite> optionalSatellite = jsonStorage.findByName(satelliteDataDTO.getName());

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
        List<Satellite> satellitesOnMemory = memoryStorage.getSatellites();
        return satellitesOnMemory.stream()
                .map(s -> SatelliteDataDTO.builder()
                        .name(s.getName())
                        .distance(s.getDistance())
                        .message(s.getMessage())
                        .build())
                .toList();
    }
}
