package org.meli.util;

import org.junit.jupiter.api.Test;
import org.meli.model.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrilaterationUtilsTest {
    @Test
    void testCalculateLocation() {
        List<Point> positions = List.of(
                new Point(-500, -200),
                new Point(100, -100),
                new Point(500, 100)
        );

        double[] distances = {485.71, 266.04, 600.50};

        Point result = TrilaterationUtils.calculateLocation(positions, distances);

        assertNotNull(result);
        assertEquals(-100.0, result.getX(), 0.5);
        assertEquals(75.5, result.getY(), 0.5);
    }
}