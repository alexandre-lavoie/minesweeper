/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Main {

    private final static Size GRIDSIZE = new Size(9, 9);
    private final static int NUMOFBOMBS = 10;
    private final static int SCALE = 100;
    private static final int SPEED = 100;
    private List<Coordinate> Bombs;
    private List<List<Coordinate>> EmptyRegions;
    private int CurrentRegion;
    private int[][] map, playMap;
    private final UI ui = new UI(GRIDSIZE, SCALE);
    private AI ai;
    private int Win = 0;
    private int Lost = 0;
    private Coordinate firstMove;

    public Main() {
        newGame();
    }

    private void newGame() {
        startGame();
    }

    private void startGame() {
        playMap = new int[GRIDSIZE.getWidth()][GRIDSIZE.getHeight()];

        CurrentRegion = 0;

        EmptyRegions = new ArrayList();

        ai = new AI(this);

        firstMove = new Coordinate(-1, -1);

        ai.playMove(playMap);

        update();
    }

    private void generateMap() {
        insertBombs();

        getNeighbors();

        createEmptyRegions();
    }

    private void update() {
        ai.playMove(playMap);
        try {
            Thread.sleep(SPEED);
        } catch (InterruptedException ex) {
        }
        update();
    }

    public int[][] getMap(){
        return playMap;
    }
    
    public void playMove(Move move, Coordinate coordinate) {
        if (firstMove.getX() == -1) {
            firstMove.set(coordinate);
            generateMap();
        }

        switch (move) {
            case DIG:
                if (map[coordinate.getX()][coordinate.getY()] >= Block.REGIONOFFSET.getID()) {
                    EmptyRegions.get(map[coordinate.getX()][coordinate.getY()] - Block.REGIONOFFSET.getID()).forEach((Point) -> {
                        playMap[Point.getX()][Point.getY()] = map[Point.getX()][Point.getY()];
                    });
                } else {
                    if (map[coordinate.getX()][coordinate.getY()] == Block.BOMB.getID()) {
                        Lost++;
                        map[coordinate.getX()][coordinate.getY()] = Block.WRONGMOVE.getID();
                        ui.drawMap(map);
                        newGame();
                    } else {
                        playMap[coordinate.getX()][coordinate.getY()] = map[coordinate.getX()][coordinate.getY()];
                    }
                }
                break;
            case FLAG:
                if (playMap[coordinate.getX()][coordinate.getY()] == Block.FLAG.getID()) {
                    playMap[coordinate.getX()][coordinate.getY()] = Block.EMPTY.getID();
                } else {
                    playMap[coordinate.getX()][coordinate.getY()] = Block.FLAG.getID();
                }
                break;
        }

        if (checkWin()) {
            Win++;
            System.out.println(Win + "/" + Lost);
            newGame();
        }

        ui.drawMap(playMap);
    }

    private boolean checkWin() {
        for (int y = 0; y < GRIDSIZE.getHeight(); y++) {
            for (int x = 0; x < GRIDSIZE.getWidth(); x++) {
                if (playMap[x][y] == Block.EMPTY.getID()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void insertBombs() {
        map = new int[GRIDSIZE.getWidth()][GRIDSIZE.getHeight()];

        Bombs = new ArrayList();

        int currentNumberOfBombs = 0;

        while (currentNumberOfBombs < NUMOFBOMBS) {
            Coordinate randomCoordinate = new Coordinate(GRIDSIZE);

            if (map[randomCoordinate.getX()][randomCoordinate.getY()] != Block.BOMB.getID() && randomCoordinate.distanceTo(firstMove)>2) {
                map[randomCoordinate.getX()][randomCoordinate.getY()] = Block.BOMB.getID();
                Bombs.add(randomCoordinate);
                currentNumberOfBombs++;
            }
        }
    }

    private void getNeighbors() {
        Bombs.forEach((Bomb) -> {
            Bomb.adjacentCoordinates(GRIDSIZE).forEach((AdjacentBlock) -> {
                map[AdjacentBlock.getX()][AdjacentBlock.getY()] += 1;
            });
        });

        Bombs.forEach((Bomb) -> {
            map[Bomb.getX()][Bomb.getY()] = Block.BOMB.getID();
        });
    }

    private void createEmptyRegions() {
        for (int y = 0; y < GRIDSIZE.getHeight(); y++) {
            for (int x = 0; x < GRIDSIZE.getWidth(); x++) {
                if (map[x][y] == 0) {
                    List<Coordinate> Region = new ArrayList();
                    List<Coordinate> CurrentSearchPoints = new ArrayList();
                    CurrentSearchPoints.add(new Coordinate(x, y));

                    while (CurrentSearchPoints.size() > 0) {

                        List<Coordinate> NextSearchPoints = new ArrayList();

                        CurrentSearchPoints.forEach((Point) -> {
                            Region.add(Point);
                            map[Point.getX()][Point.getY()] = CurrentRegion + Block.REGIONOFFSET.getID();
                            Point.adjacentCoordinates(GRIDSIZE).forEach((AdjacentBlock) -> {
                                if (map[AdjacentBlock.getX()][AdjacentBlock.getY()] == 0) {
                                    NextSearchPoints.add(new Coordinate(AdjacentBlock.getX(), AdjacentBlock.getY()));
                                } else if (map[AdjacentBlock.getX()][AdjacentBlock.getY()] > -1 && map[AdjacentBlock.getX()][AdjacentBlock.getY()] < 9) {
                                    if (!inRegion(Region, AdjacentBlock)) {
                                        Region.add(new Coordinate(AdjacentBlock.getX(), AdjacentBlock.getY()));
                                    }
                                }
                            });
                        });

                        CurrentSearchPoints = NextSearchPoints;
                    }

                    EmptyRegions.add(Region);
                    CurrentRegion++;
                }
            }
        }
    }

    private boolean inRegion(List<Coordinate> Region, Coordinate coordiante) {
        return Region.stream().anyMatch((Point) -> (coordiante.equals(Point)));
    }
}
