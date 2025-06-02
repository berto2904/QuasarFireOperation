package org.meli.dto;

import lombok.Data;

import java.util.List;

@Data
public class TopSecretRequestDTO {
   private List<SatelliteDataDTO> satellites;
}
