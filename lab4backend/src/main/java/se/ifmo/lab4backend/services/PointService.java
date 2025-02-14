package se.ifmo.lab4backend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.repositories.PointRepository;
import se.ifmo.lab4backend.utils.AreaChecker;

import java.util.List;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final AreaChecker areaChecker;

    @Autowired
    public PointService(PointRepository pointRepository, AreaChecker areaChecker) {
        this.pointRepository = pointRepository;
        this.areaChecker = areaChecker;
    }

    public void insert(Point point) {
        point.setHit(checkHit(point) ? '1' : '0');
        point.setUserId(getCurrentUserId());
        pointRepository.save(point);
    }

    public List<Point> findAllForCurrentUser() {
        return pointRepository.findAllByUserId(getCurrentUserId());
    }

    public boolean checkHit(Point point) {
        return areaChecker.checkArea(point);
    }

    private Long getCurrentUserId() {
        CustomAuthentication authentication =
                (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
