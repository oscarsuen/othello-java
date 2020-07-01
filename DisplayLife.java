import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class DisplayLife extends JPanel implements MouseListener, MouseMotionListener {
	GameLife game;
	int lastXMouse = -1;
	int lastYMouse = -1;
	
	public DisplayLife() {
		game = new GameLife(120,120,RuleLife.LIFE);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
		setBackground(Color.BLACK);
		// for testing purposes
		for (int x = 0; x < 120; x++) {
			for (int y = 0; y < 120; y++) {
				if ((x + y) % 7 == 2) {
					game.flip(x,y);
				}
			}
		}
	}

	public GameLife getGame() {
		return game;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int x = 0; x < game.getWidth(); x++) {
			for (int y = 0; y < game.getWidth(); y++) {
					if(game.get(x,y)){
						g.setColor(Color.GREEN);
						g.fillRect(5 * x, 5 * y, 5, 5);
					}
				}
			}
		}

	public void paint() {
		paintComponent(getGraphics());
	}
	
	public void mouseReleased(MouseEvent e) {
		game.flip(e.getX()/5, e.getY()/5);
		repaint();
	}

	public boolean[][] getArray() {
		return game.getArray();
	}

	// we don't need to change these
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getX()/5 != lastXMouse/5 || e.getX()/5 != lastYMouse/5){
			game.flip(e.getX()/5, e.getY()/5);
			repaint();
			lastXMouse = e.getX();
			lastYMouse = e.getY();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}