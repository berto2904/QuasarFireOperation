package org.meli.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.meli.model.Point;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteRepository;
import org.meli.repository.impl.SatelliteRepositoryImpl;
import org.meli.service.LocationService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationServiceTest {

    @Nested
    class WithMockRepository{
        private SatelliteRepository satelliteRepository;
        private LocationService locationService;

        @BeforeEach
        void setup(){
            satelliteRepository =mock(SatelliteRepository.class);
            locationService = new LocationServiceImpl(satelliteRepository);
        }

        @Test
        void testGetLocationReturnsExpectedResult() {
            when(satelliteRepository.getSatellites()).thenReturn(List.of(
                    new Satellite("kenobi", new Point(-500, -200)),
                    new Satellite("skywalker", new Point(100, -100)),
                    new Satellite("sato", new Point(500, 100))
            ));

            double[] distances = {485.71, 266.04, 600.50};

            Point result = locationService.getLocation(distances);

            assertNotNull(result);
            assertTrue(result.getX() >= -101 && result.getX() <= -99);
            assertTrue(result.getY() >= 74.5 && result.getY() <= 76.5);
        }
    }

    @Nested
    class WithRealRepository {
        private SatelliteRepository satelliteRepository;

        @BeforeEach
        void setup() throws IOException {
            satelliteRepository = new SatelliteRepositoryImpl();
        }

        @Test
        void testSatellitesJsonIsLoadedSuccessfully() {
            List<Satellite> satellites = satelliteRepository.getSatellites();
            assertNotNull(satellites);
            assertFalse(satellites.isEmpty());
        }
    }

}