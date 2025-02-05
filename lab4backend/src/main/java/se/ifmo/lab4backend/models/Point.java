package se.ifmo.lab4backend.models;


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
