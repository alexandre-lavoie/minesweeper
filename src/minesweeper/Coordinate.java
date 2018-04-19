/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author User
 */
public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Created with a random position.
     * @param size Size of grid.
     */
    
    public Coordinate(Size size){
        this.x = ThreadLocalRandom.current().nextInt(0, size.getWidth());
        this.y = ThreadLocalRandom.current().nextInt(0, size.getHeight());
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public boolean equals(Coordinate coordinate){
        return coordinate.getX()==x&&coordinate.getY()==y;
    }
    
    /**
     * Gets all adjacent points.
     * @param size Size of grid.
     * @return List of adjacent points.
     */
    
    public List<Coordinate> adjacentCoordinates(Size size) {
        List<Coordinate> adjCoordinates = new ArrayList();

        for (int sx = -1; sx < 2; sx++) {
            for (int sy = -1; sy < 2; sy++) {
                if (sx == 0 && sy == 0) {
                } else if(x+sx>-1&&y+sy>-1&&x+sx<size.getWidth()&&y+sy<size.getHeight()) {
                    adjCoordinates.add(new Coordinate(x + sx, y + sy));
                }
            }
        }

        return adjCoordinates;
    }
    
     /**
     * Gets all corner adjacent points.
     * @param size Size of grid.
     * @return List of corner adjacent points.
     */
    
    public List<Coordinate> cornerAdjacentCoordinates(Size size){
        List<Coordinate> adjCoordinates = new ArrayList();

        for (int sx = 0; sx < 2; sx++) {
            for (int sy = 0; sy < 2; sy++) {
                if(x+(sx*2-1)>-1&&y+(sy*2-1)>-1&&x+(sx*2-1)<size.getWidth()&&y+(sy*2-1)<size.getHeight()) {
                    adjCoordinates.add(new Coordinate(x + (sx*2-1), y + (sy*2-1)));
                }
            }
        }

        return adjCoordinates;
    }
    
    public List<Coordinate> sideAdjacentCoordiantes(Size size){
        List<Coordinate> adjCoordinates = new ArrayList();

        if(x+1<size.getWidth())adjCoordinates.add(new Coordinate(x + 1, y));
        if(x-1>-1)adjCoordinates.add(new Coordinate(x - 1, y));
        if(y+1<size.getHeight())adjCoordinates.add(new Coordinate(x, y+1));
        if(y-1>-1)adjCoordinates.add(new Coordinate(x, y-1));

        return adjCoordinates;
    }
    
    public int distanceTo(Coordinate coordinate){
        return Math.abs(coordinate.getX()-x)+Math.abs(coordinate.getY()-y);
    }
    
    public void set(Coordinate coordinate){
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }
}
