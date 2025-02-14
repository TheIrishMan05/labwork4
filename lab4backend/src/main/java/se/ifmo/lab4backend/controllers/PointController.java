package se.ifmo.lab4backend.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.services.PointService;
import se.ifmo.lab4backend.utils.Validator;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.findAllForCurrentUser();
        log.info(points.size() + ": amount of points for current user");
        return ResponseEntity.ok(points);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Point>> insert(@RequestBody Point point) {
        log.info("Insert point: " + point);
        if(validator.validate(point)) {
            pointService.insert(point);
            return ResponseEntity.ok(Map.of("point", point));
        } else {
            return ResponseEntity.badRequest().body(Map.of("point", point));
        }
    }

}
