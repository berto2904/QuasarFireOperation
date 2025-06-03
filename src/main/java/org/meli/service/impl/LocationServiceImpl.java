package org.meli.service.impl;

import lombok.RequiredArgsConstructor;
import org.meli.model.Point;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteReadOnlyRepository;
import org.meli.repository.impl.SatelliteJsonFileRepositoryImpl;
import org.meli.service.LocationService;
import org.meli.util.TrilaterationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

    private final SatelliteReadOnlyRepository satelliteRepository;

    public LocationServiceImpl(@Qualifier("satelliteJsonFileRepositoryImpl") SatelliteReadOnlyRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    @Override
    public Point getLocation(double[] distances) {
        List<Satellite> satellites = satelliteRepository.getSatellites();

        if (satellites.size() != distances.length) {
            throw new IllegalArgumentException("La cantidad de distancias no coincide con la cantidad de satélites configurados");
        }

        List<Point> positions = satellites.stream()
                .map(Satellite::getPosition)
                .collect(toList());

        return TrilaterationUtils.calculateLocation(positions, distances);
    }
}
