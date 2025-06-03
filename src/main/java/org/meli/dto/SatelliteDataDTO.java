package org.meli.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SatelliteDataDTO {
    private String name;
    private double distance;
    private String[] message;
}
