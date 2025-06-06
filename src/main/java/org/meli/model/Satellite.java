package org.meli.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Satellite {
    private String name;
    private Point position;
    private double distance;
    private String[] message;

    public Satellite(String name, Point position) {
        this.name = name;
        this.position = position;
    }
}


