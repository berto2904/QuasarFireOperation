package org.meli.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.meli.model.Satellite;
import org.meli.repository.SatelliteReadOnlyRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("satelliteJsonFileRepositoryImpl")
public class SatelliteJsonFileRepositoryImpl implements SatelliteReadOnlyRepository {

    private final List<Satellite> satellites;

    public SatelliteJsonFileRepositoryImpl() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("satellites.json");
        try (InputStream is = resource.getInputStream()) {
            this.satellites = Arrays.asList(mapper.readValue(is, Satellite[].class));
        }
    }
    @Override
    public List<Satellite> getSatellites() {
        return satellites;
    }

    @Override
    public Optional<Satellite> findByName(String name) {
        return satellites.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
