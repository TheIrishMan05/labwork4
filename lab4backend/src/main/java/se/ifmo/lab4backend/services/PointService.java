package se.ifmo.lab4backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.Point;
import se.ifmo.lab4backend.utils.AreaChecker;

import java.util.List;

@Service
public class PointService {
    private final AreaChecker areaChecker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PointService(AreaChecker areaChecker, NamedParameterJdbcTemplate template) {
        this.areaChecker = areaChecker;
        this.template = template;
    }

    @Transactional
    public void insert(Point point) {
        Long newId = template.queryForObject("SELECT point_seq.NEXTVAL FROM DUAL", new MapSqlParameterSource(), Long.class);
        String sql = "INSERT INTO point (id, x, y, r, hit, userId) VALUES (:id, :x, :y, :r, :hit, :userId)";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", newId)
                .addValue("x", point.getX())
                .addValue("y", point.getY())
                .addValue("r", point.getR())
                .addValue("hit", areaChecker.checkArea(point) ? 1 : 0)
                .addValue("userId", getUserId());
        template.update(sql, source);
    }

    public List<Point> getPointsByUserId() {
        String sql = "SELECT * FROM point WHERE userId = :userId";
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("userId", getUserId());
        List<Point> points = template.queryForList(sql, source, Point.class);
        return points;
    }

    public Long getUserId() {
        CustomAuthentication authentication = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
