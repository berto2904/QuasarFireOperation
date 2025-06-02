package org.meli.service;

import org.meli.model.Point;


public interface LocationService {
    Point getLocation(double[] distances);
}
