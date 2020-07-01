//Display.java
/*
*
* @Authors: Vinayak Kurup, Oscar Suen, Stuart Rucker
* @Version: February 26, 2015
*
* The display of the program that has the squares. Actual GUI implementation
*
*/

//Necessary imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.*;
import javax.sound.sampled.*;

//this window is the one with the board drawn
public class Display extends JPanel implements MouseListener {
    private int width, height;
    private int squareWidth, squareHeight;
    private boolean CPUhasNoMove;
    private Game g;
    int depth;
    int movesplayed = 0;
    //the minimum amount of time the cpu wait before playing
    long MIN_WAIT = 2000;


    public Display(int depth1) {
        addMouseListener(this);
        setBackground(new Color(65, 140, 35));
        depth = depth1;
        g = new Game(8, 8, depth);
    }
    
    public void size(int x, int y) {
        this.setPreferredSize(new Dimension(x, y));
        width = x;
        height = y;
        squareWidth = x / 8;
        squareHeight = y / 8;
    }

    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        graph.setColor(Color.BLACK);
        
        //drawing lines inbetween squares
        for (int x = squareWidth; x < width - 4; x += squareWidth) {
            graph.fillRect(x, 0, 4, height);
        }
        for (int y = squareHeight; y < height - 4; y += squareWidth) {
            graph.fillRect(0, y, width, 4);
        }

        //draw tiles based on board
        for (int x = 0; x < 8; x ++) {
            for (int y = 0; y < 8; y ++) {
                //piece is white
                if (g.getBoard().get(x, y) == -1) {
                    graph.setColor(new Color(242, 235, 201));
                    graph.fillOval(x * squareHeight + 4, y * squareWidth + 4, squareWidth - 4, squareHeight - 4);
                } else if (g.getBoard().get(x, y) == 1) {
                    graph.setColor(Color.BLACK);
                    graph.fillOval(x * squareHeight + 4, y * squareWidth + 4, squareWidth - 4, squareHeight - 4);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (e.getX()) / squareWidth;
        int y = (e.getY()) / squareHeight;

        //if you can play, then make the cpu play
        if (g.play(x, y)) {
            paintComponent(getGraphics());
            Runner.changePrompt("move " + (++movesplayed));
            long beforeCPU = System.currentTimeMillis();
            g.CPUPLay((byte)x, (byte)y);
            long afterCPU = System.currentTimeMillis();
            
            //wait untill the minimum amount of time has passed
            if (afterCPU < beforeCPU + MIN_WAIT) {
                try {
                    Thread.sleep(MIN_WAIT - (afterCPU - beforeCPU));
                } catch (InterruptedException e1) {}
            }

            //update graphics
            paintComponent(getGraphics());
        
        } else {
            //if your'e play is invalid, make a little window
            JOptionPane.showMessageDialog(new JFrame(), "You can't play there");
        }

        //if only one person can play, then make that person play until the end
        boolean c = g.canPLay((byte) (-1));
        boolean u = g.canPLay((byte) (1));
        while (!u && c) {
            System.out.println("Only CPU can play");
            g.CPUPLay((byte)(-1), (byte)(-1));
            paintComponent(getGraphics());
            c = g.canPLay((byte) (-1));
            u = g.canPLay((byte) (1));
        }
        if (!u && !c) {
            gg();
        }

    }

    //goog game
    public void gg() {
        int b = g.getScore((byte)1);
        int w = g.getScore((byte)(-1));
        String whoWins = "";

        //display who won
        if (b > w)whoWins = "you win!\n";
        if (b < w)whoWins = "you lose!\n";
        if (w == b) whoWins = "tie\n";
        whoWins += "you have " + b + " tiles, CPU has " + w;
        JOptionPane.showMessageDialog(new JFrame(), whoWins);
        
        //go back to the start screen
        Runner.startStarterScreen();
        Runner.changePrompt("start Screen");
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
