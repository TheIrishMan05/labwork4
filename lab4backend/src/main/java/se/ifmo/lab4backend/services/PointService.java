package se.ifmo.lab4backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.repositories.PointRepository;
import se.ifmo.lab4backend.utils.AreaChecker;

import java.util.List;

@Service
public class PointService {
    private final AreaChecker areaChecker;
    private PointRepository pointRepository;

    @Autowired
    public PointService(AreaChecker areaChecker, PointRepository pointRepository) {
        this.pointRepository = pointRepository;
        this.areaChecker = areaChecker;
    }

    @Transactional
    public void insert(Point point) {
        point.setHit(areaChecker.checkArea(point));
        point.setUserId(getUserId());
        pointRepository.save(point);
    }

    public List<Point> getPointsByUserId() {
        return pointRepository.findAllByUserId(getUserId());
    }

    public Long getUserId() {
        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
