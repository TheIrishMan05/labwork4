package se.ifmo.weblab4.models;


import lombok.Data;

@Data
public class Point {

    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;
    private Long userId;
}
