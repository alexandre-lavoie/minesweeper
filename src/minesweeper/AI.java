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
public class AI {

    private final Main main;
    private int Turns = 0;

    public AI(Main main) {
        this.main = main;
    }

    public void playMove(int[][] playMap) {
        if (Turns == 0) {
            randomDig(playMap);
        }

        checkCorners(playMap);
        dig(playMap);

    }

    private void randomDig(int[][] playMap) {
        Coordinate randomCoordinate = new Coordinate((int) playMap.length / 2, (int) playMap[0].length / 2);

        while (playMap[randomCoordinate.getX()][randomCoordinate.getY()] != Block.EMPTY.getID()) {
            randomCoordinate = new Coordinate(new Size(playMap.length, playMap[0].length));
        }

        main.playMove(Move.DIG, randomCoordinate);
    }

    public void dig(int[][] playMap) {
        List<Coordinate> digCoordinates = new ArrayList();

        for (int y = 0; y < playMap[0].length; y++) {
            for (int x = 0; x < playMap.length; x++) {
                if (playMap[x][y] != Block.EMPTY.getID() && playMap[x][y] != Block.DISCOVERED.getID()) {
                    List<Coordinate> adjacentCoordinates = new Coordinate(x, y).adjacentCoordinates(new Size(playMap.length, playMap[0].length));
                    int NumOfFlag = 0;

                    NumOfFlag = adjacentCoordinates.stream().filter((Adjacent) -> (playMap[Adjacent.getX()][Adjacent.getY()] == Block.FLAG.getID())).map((_item) -> 1).reduce(NumOfFlag, Integer::sum);

                    if (NumOfFlag == playMap[x][y]) {
                        adjacentCoordinates.stream().filter((Adjacent) -> (playMap[Adjacent.getX()][Adjacent.getY()] == Block.EMPTY.getID())).forEachOrdered((Adjacent) -> {
                            digCoordinates.add(Adjacent);
                        });
                    }
                }
            }
        }

        if (digCoordinates.isEmpty()) {
            randomDig(playMap);
            Turns++;
        } else {
            digCoordinates.forEach((dig) -> {
                int[][] map = main.getMap();
                if (map[dig.getX()][dig.getY()] == Block.EMPTY.getID()) {
                    main.playMove(Move.DIG, dig);
                    Turns++;
                }
            });
        }
    }

    public void checkCorners(int[][] playMap) {
        List<Coordinate> Corners = new ArrayList();

        for (int y = 0; y < playMap[0].length; y++) {
            for (int x = 0; x < playMap.length; x++) {
                if (playMap[x][y] == Block.EMPTY.getID()) {
                    for (Coordinate Point : new Coordinate(x, y).adjacentCoordinates(new Size(playMap.length, playMap[0].length))) {

                        int NumOfFlag = 0;

                        NumOfFlag = Point.adjacentCoordinates(new Size(playMap.length, playMap[0].length)).stream().filter((Adjacent) -> (playMap[Adjacent.getX()][Adjacent.getY()] == Block.FLAG.getID())).map((_item) -> 1).reduce(NumOfFlag, Integer::sum);

                        if (playMap[Point.getX()][Point.getY()] - NumOfFlag == Block.ONE.getID() && !isSideBlock(playMap, Point)) {
                            if (!inList(Corners, new Coordinate(x, y))) {
                                Corners.add(new Coordinate(x, y));
                                break;
                            }
                        }
                    }
                }
            }
        }

        Corners.stream().map((Corner) -> {
            main.playMove(Move.FLAG, Corner);
            return Corner;
        }).forEachOrdered((_item) -> {
            Turns++;
        });
    }

    private boolean inList(List<Coordinate> list, Coordinate coordiante) {
        return list.stream().anyMatch((Point) -> (coordiante.equals(Point)));
    }

    private boolean isSideBlock(int[][] playMap, Coordinate coordinate) {
        return coordinate.sideAdjacentCoordiantes(new Size(playMap.length, playMap[0].length)).stream().anyMatch((Point) -> (playMap[Point.getX()][Point.getY()] == Block.EMPTY.getID()));
    }
}
