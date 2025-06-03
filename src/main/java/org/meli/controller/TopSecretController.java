package org.meli.controller;

import lombok.RequiredArgsConstructor;
import org.meli.dto.SatelliteDataDTO;
import org.meli.dto.TopSecretRequestDTO;
import org.meli.dto.TopSecretResponseDTO;
import org.meli.model.Point;
import org.meli.service.LocationService;
import org.meli.service.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topsecret")
public class TopSecretController {

    @Qualifier("locationService")
    private final LocationService locationService;
    private final MessageService messageService;

    public TopSecretController(LocationService locationService, MessageService messageService) {
        this.locationService = locationService;
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<TopSecretResponseDTO> getTopSecret(@RequestBody TopSecretRequestDTO request){
        List<SatelliteDataDTO> satellites = request.getSatellites();

        double[] distances = satellites.stream()
                .mapToDouble(SatelliteDataDTO::getDistance)
                .toArray();

        String[][] messages = satellites.stream()
                .map(SatelliteDataDTO::getMessage)
                .toArray(String[][]::new);

        Point position = locationService.getLocation(distances);
        String message = messageService.getMessage(messages);

        TopSecretResponseDTO response = new TopSecretResponseDTO(position, message);

        return ResponseEntity.ok(response);
    }
}
