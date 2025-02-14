package se.ifmo.lab4backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.ifmo.lab4backend.models.Point;

import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<Point, Long> {
    List<Point> findAllByUserId(Long userId);
}
