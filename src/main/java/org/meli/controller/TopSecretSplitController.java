package org.meli.controller;

import lombok.AllArgsConstructor;
import org.meli.dto.SatelliteDataDTO;
import org.meli.dto.TopSecretResponseDTO;
import org.meli.model.Point;
import org.meli.service.LocationSplitService;
import org.meli.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topsecret_split")
@AllArgsConstructor
public class TopSecretSplitController {

    private final LocationSplitService locationServiceSplit;
    private final MessageService messageService;


    @PostMapping("/{satellite_name}")
    public ResponseEntity<Void> saveSatellite(@PathVariable("satellite_name") String name,
                                              @RequestBody SatelliteDataDTO satelliteDataDTO) {
        satelliteDataDTO.setName(name.toLowerCase());
        locationServiceSplit.saveSatellite(satelliteDataDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TopSecretResponseDTO> getTopSecretSplit() {
        List<SatelliteDataDTO> satellites = locationServiceSplit.getAllSatellites();

        if (satellites.size() < 3) {
            return ResponseEntity.status(404).build();
        }

        double[] distances = satellites.stream().mapToDouble(SatelliteDataDTO::getDistance).toArray();
        String[][] messages = satellites.stream().map(SatelliteDataDTO::getMessage).toArray(String[][]::new);

        try {
            Point position = locationServiceSplit.getLocation(distances);
            String message = messageService.getMessage(messages);
            return ResponseEntity.ok(new TopSecretResponseDTO(position, message));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> clearTopSecretSplit() {
        Map<String, String> response = new HashMap<>();
        if (!locationServiceSplit.clearSatellites()){
            response.put("message","Un error ocurrio al limpiar los satelites");
            return ResponseEntity.internalServerError().body(response);
        }
        response.put("message", "Los satélites almacenados fueron eliminados correctamente.");
        return ResponseEntity.ok(response);
    }
}
