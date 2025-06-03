package org.meli.util;

import org.meli.exception.PositionNotResolvableException;
import org.meli.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TrilaterationUtils {
    public static Point calculateLocation(List<Point> positions, double[] distancesArray) {

        if (positions.size() != 3 || distancesArray.length != 3) {
            throw new PositionNotResolvableException("Se requieren 3 posiciones y 3 distancias.");
        }

        List<Double> distances = Arrays.stream(distancesArray)
                .boxed()
                .toList();

        Point p1 = positions.get(0);
        Point p2 = positions.get(1);
        Point p3 = positions.get(2);

        double x1 = p1.getX(), y1 = p1.getY(), d1 = distances.get(0);
        double x2 = p2.getX(), y2 = p2.getY(), d2 = distances.get(1);
        double x3 = p3.getX(), y3 = p3.getY(), d3 = distances.get(2);

        double A = 2 * (x2 - x1);
        double B = 2 * (y2 - y1);
        double C = Math.pow(d1, 2) - Math.pow(d2, 2)
                - Math.pow(x1, 2) + Math.pow(x2, 2)
                - Math.pow(y1, 2) + Math.pow(y2, 2);

        double D = 2 * (x3 - x1);
        double E = 2 * (y3 - y1);
        double F = Math.pow(d1, 2) - Math.pow(d3, 2)
                - Math.pow(x1, 2) + Math.pow(x3, 2)
                - Math.pow(y1, 2) + Math.pow(y3, 2);

        double denominator = A * E - B * D;
        if (denominator == 0) {
            throw new PositionNotResolvableException("No se puede determinar la posición");
        }

        double x = (C * E - B * F) / denominator;
        double y = (A * F - C * D) / denominator;

        return new Point(x, y);
    }
}
