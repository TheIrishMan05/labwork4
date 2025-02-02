package se.ifmo.weblab4.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ifmo.weblab4.models.Point;
import se.ifmo.weblab4.services.PointService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class PointController {
    private PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getPointsByUserId();
        log.info(points.size() + ": amount of points for current user");
        return ResponseEntity.ok(points);
    }

    @PostMapping("/add")
    public ResponseEntity<String> insert(@Validated @RequestBody Point point) {
        log.info("Insert point: " + point);
        pointService.insert(point);
        return ResponseEntity.ok(point.toString());
    }

}
