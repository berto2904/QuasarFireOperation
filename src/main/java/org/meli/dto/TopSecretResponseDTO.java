package org.meli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.meli.model.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretResponseDTO {
    private Point position;
    private String message;
}
