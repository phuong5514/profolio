/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JButton;

public class LikeButton extends JButton {
    private Graphics2D g2d;
    private Color[] fillColors;
    private boolean like;
    private int x = 25; // Adjust as needed
    private int y = 25; // Adjust as needed
    private int width = 40; // Adjust as needed
    private int height = 40; // Adjust as needed
    
    public LikeButton(boolean like) {
        super();
        setSize(50, 50);
        setVisible(true);
        
        fillColors = new Color[2];
        fillColors[0] = Color.LIGHT_GRAY;
        fillColors[1] = Color.RED;
        
        this.like = like;
    }
    
    public LikeButton(boolean like, int width, int height) {
        super();
        setSize(50, 50);
        setVisible(true);
        
        fillColors = new Color[2];
        fillColors[0] = Color.LIGHT_GRAY;
        fillColors[1] = Color.RED;
        
        this.like = like;
        
        x = (width);
        y = (height);
    }
    
    private Polygon getHeartShape() {
        
        Polygon heartShape = new Polygon();
        heartShape.addPoint(x + width / 2, y + height); 
        heartShape.addPoint(x, y + height / 2); 
        heartShape.addPoint(x, y + height / 4);
        heartShape.addPoint(x + width / 6, y);
        heartShape.addPoint(x + 2 * width / 6, y);
        heartShape.addPoint(x + width / 2, y + height / 4);
        heartShape.addPoint(x + 4 * width / 6, y);
        heartShape.addPoint(x + 5 * width / 6, y);
        heartShape.addPoint(x + width, y+ height / 4);
        heartShape.addPoint(x + width, y + height / 2);
        return heartShape;
    }
    
    public int toggleLike() {
        like = !like;
        if (like) {
            return 1;
        }
        else {
            return 0;
        }
    }
    
    public void paintHeart() {
        int choice = 0;
        if (like) {
            choice = 1;
        }
        g2d.setColor(this.fillColors[choice]);
        Polygon heart = getHeartShape();
        g2d.fill(heart);
        g2d.setColor(Color.BLACK);
        g2d.draw(heart);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        
        paintHeart();
    }


}
