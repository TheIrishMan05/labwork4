package se.ifmo.lab4backend.services;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.repositories.PointRepository;
import se.ifmo.lab4backend.utils.AreaChecker;

import java.util.List;

@Service
@Log4j2
public class PointService {
    private final PointRepository pointRepository;
    private final AreaChecker areaChecker;

    @Autowired
    public PointService(PointRepository pointRepository, AreaChecker areaChecker) {
        this.pointRepository = pointRepository;
        this.areaChecker = areaChecker;
    }

    public void insert(Point point) {
        point.setHit(checkHit(point) ? 1 : 0);
        Long userId = getCurrentUserId();
        point.setUserId(userId);
        pointRepository.save(point);
    }

    public List<Point> findAllForCurrentUser() {
        Long userId = getCurrentUserId();
        return pointRepository.findAllByUserId(userId);
    }

    public boolean checkHit(Point point) {
        return areaChecker.checkArea(point);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication in PointService: {}", authentication);

        if (authentication == null) {
            throw new IllegalStateException("Пользователь не аутентифицирован: отсутствует объект аутентификации");
        }
        if (!(authentication instanceof CustomAuthentication)) {
            throw new IllegalStateException("Аутентификация имеет неверный тип: ожидается CustomAuthentication");
        }
        return ((CustomAuthentication) authentication).getUserId();
    }


}
