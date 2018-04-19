/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author User
 */
public class UI {

    private JFrame jFrame;
    private JLabel jImage;
    private final Size GridSize;
    private final int Scale;

    public UI(Size GridSize, int Scale) {
        this.GridSize = GridSize;
        this.Scale = Scale;

        createFrame();
    }

    private void createFrame() {
        jFrame = new JFrame("Minesweeper");
        jFrame.setSize(GridSize.getWidth() * Scale, GridSize.getHeight() * Scale);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jImage = new JLabel(new ImageIcon(new BufferedImage(GridSize.getWidth(), GridSize.getHeight(), BufferedImage.TYPE_INT_RGB).getScaledInstance(GridSize.getWidth() * Scale, GridSize.getHeight() * Scale, 100)));
        jFrame.add(jImage);
        jFrame.setVisible(true);
    }

    public void drawMap(int[][] map) {
        BufferedImage newImage = new BufferedImage(GridSize.getWidth(), GridSize.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < GridSize.getHeight(); y++) {
            for (int x = 0; x < GridSize.getWidth(); x++) {
                newImage.setRGB(x, y, Block.getBlockById(map[x][y]).getColor().getRGB());
            }
        }

        jImage.setIcon(new ImageIcon(newImage.getScaledInstance(GridSize.getWidth() * Scale, GridSize.getHeight() * Scale, 100)));
        
        //outputImage(newImage, "C:/Users/User/Desktop/", "Minesweeper", new Timestamp(System.currentTimeMillis()).getTime());
    }
    
    public void outputImage(BufferedImage image, String Path, String Name, long Time) {
        try {
            new File(Path + Name + "/").mkdirs();
            
            File outputfile = new File(Path + Name + "/" + Name + "_" + Time + ".png");

            BufferedImage bImage = new BufferedImage(GridSize.getWidth() * Scale, GridSize.getHeight() * Scale, BufferedImage.TYPE_INT_RGB);

            Graphics2D bImageGraphics = bImage.createGraphics();

            bImageGraphics.drawImage(image.getScaledInstance(GridSize.getWidth() * Scale, GridSize.getHeight() * Scale, 100), null, null);

            ImageIO.write(bImage, "png", outputfile);
        } catch (IOException e) {
        }
    }
}
