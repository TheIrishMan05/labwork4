package se.ifmo.weblab4.utils;

import org.springframework.stereotype.Component;
import se.ifmo.weblab4.models.Point;

@Component
public class AreaChecker {

    public boolean checkArea(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        return checkTriangle(x, y, r) || checkCircle(x, y, r) || checkRectangle(x, y, r);
    }

    private boolean checkTriangle(double x, double y, double r) {
        if(x >= 0 && y >= 0) {
            return y <= (x / 2) - r && y <= r && x <= (r / 2);
        }
        return false;
    }

    private boolean checkCircle(double x, double y, double r) {
        if(x <= 0 && y >= 0) {
            return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2);
        }
        return false;
    }

    private boolean checkRectangle(double x, double y, double r) {
        if(x >= 0 && y <= 0) {
            return x <= r && y >= -r;
        }
        return false;
    }
}
