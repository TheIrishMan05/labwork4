package se.ifmo.weblab4.utils;

import org.springframework.stereotype.Component;
import se.ifmo.weblab4.models.Point;

@Component
public class Validator {
    public boolean validate(Point point) {
        return validateX(point) && validateY(point) && validateR(point);
    }

    private boolean validateX(Point point) {
        return point.getX() <= 2 && point.getX() >= -2;
    }

    private boolean validateY(Point point) {
        return point.getY() <= 5 && point.getY() >= -3;
    }

    private boolean validateR(Point point) {
        return point.getX() <= 2 && point.getY() >= 0;
    }
}
