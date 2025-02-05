package se.ifmo.lab4backend.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.services.PointService;
import se.ifmo.lab4backend.utils.Validator;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class PointController {
    private PointService pointService;
    private Validator validator;

    @Autowired
    public PointController(PointService pointService, Validator validator) {
        this.pointService = pointService;
        this.validator = validator;
    }

    @GetMapping("/user")
    public List<Point> getAllPoints() {
        List<Point> points = pointService.getPointsByUserId();
        log.info(points.size() + ": amount of points for current user");
        return points;
    }

    @PostMapping("/add")
    public Point insert(@RequestParam double x, @RequestParam double y, @RequestParam double r) {
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setR(r);
        log.info("Insert point: " + point);
        if(validator.validate(point)) {
            pointService.insert(point);
            return point;
        } else {
            throw new RuntimeException("Невалидная точка.");
        }
    }

}
