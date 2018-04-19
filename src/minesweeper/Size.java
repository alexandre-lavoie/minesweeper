/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

/**
 *
 * @author User
 */
public class Size {
    
    private int Width = 0;
    private int Height = 0;
    
    public Size(int Width, int Height){
        this.Width = Width;
        this.Height = Height;
    }
     
    public int getWidth(){
        return Width;
    }
    
    public int getHeight(){
        return Height;
    }  
}
