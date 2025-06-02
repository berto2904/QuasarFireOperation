package org.meli.dto;

import lombok.Data;

@Data
public class SatelliteDataDTO {
    private String name;
    private double distance;
    private String[] message;
}
