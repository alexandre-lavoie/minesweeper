/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Color;

/**
 *
 * @author User
 */
public enum Block {
    WRONGMOVE(-4, Color.ORANGE), FLAG(-3,Color.GREEN),DISCOVERED(-2, Color.BLACK), BOMB(-1,Color.RED), EMPTY(0,Color.BLUE), REGIONOFFSET(9,Color.BLACK),
    ONE(1,Color.getHSBColor(0, 0, 0.125f)), TWO(2,Color.getHSBColor(0, 0, 0.25f)), THREE(3,Color.getHSBColor(0, 0, 0.375f)), FOUR(4,Color.getHSBColor(0, 0, 0.5f)), FIVE(5,Color.getHSBColor(0, 0, 0.625f)), SIX(6,Color.getHSBColor(0, 0, 0.75f)), SEVEN(7,Color.getHSBColor(0, 0, 0.875f)), EIGHT(8,Color.getHSBColor(0, 0, 1f)),;
    
    private final int id;
    private final Color color;
    
    Block(int id, Color color){
        this.id = id;
        this.color = color;
    }
    
    public int getID(){
        return id;
    }
    
    public Color getColor(){
        return color;
    }
    
    public static Block getBlockById(int id){
        for(Block b:values()){
            if(b.id == id) return b;
        }
        return DISCOVERED;
    }
}
