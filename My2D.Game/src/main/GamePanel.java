package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTEINGS
	final int originalTileSize = 16; // 16*16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48*48 tile
	public final int maxScreenCol = 18;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;// 768 pixels
	public final int screeHeight = tileSize * maxScreenRow;// 576 pixels

	// world settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int worldHight = tileSize * maxWorldRow;

	// FPS
	int FPS = 60;

	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker= new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this, keyH);
	public SuperObject obj[]=new SuperObject[10];
	
	

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screeHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setUpGame() {
		aSetter.setObject();
	}
	
	public void startsGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {

		double drowInterval = 1000000000 / FPS;// 0.016666 seconds
		double nextDrawTime = System.nanoTime() + drowInterval;

		while (gameThread != null) {

			// UPDATE: update information such as character position
			update();

			// DRAW: draw the screen with updated information
			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);

				nextDrawTime += drowInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void update() {

		player.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        //TILE
		tileM.draw(g2);
        //OBJECT
		for (int i = 0; i < obj.length; i++) {
			if(obj[i]!=null) {
				obj[i].draw(g2,this);
			}
		}
		
		//PLAYER
		player.draw(g2);

		g2.dispose();
	}

}
