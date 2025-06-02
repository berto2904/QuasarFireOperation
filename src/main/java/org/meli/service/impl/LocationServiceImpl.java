package org.meli.service.impl;

import lombok.RequiredArgsConstructor;
import org.meli.dto.SatelliteDataDTO;
import org.meli.model.Point;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteRepository;
import org.meli.service.LocationService;
import org.meli.util.TrilaterationUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.Position;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final SatelliteRepository satelliteRepository;


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
