package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	int hasKey=0;

	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screeHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 6;
		solidArea.y = 12;
		solidAreaDefultX=solidArea.x;
		solidAreaDefultY=solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;

		setDefultValuse();
		getPlayerImage();

	}

	public void setDefultValuse() {

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		try {
//			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
//			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
//			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
//			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
//			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
//			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
//			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
//			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

			up1 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/down2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/right2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player2ironman/left2.png"));

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void update() {
		if (keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
				|| keyH.upPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";
				

			} else if (keyH.downPressed == true) {
				direction = "down";
				

			} else if (keyH.leftPressed == true) {
				direction = "left";
				

			}

			else if (keyH.rightPressed == true) {
				direction = "right";
				

			}
			//CHECK TILE COLLISION
			collisionOn=false;
			gp.cChecker.checkTile(this);
			
			//check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn==false) {
				
				switch (direction) {
				case "up":worldY -= speed;break;
				case "down":worldY += speed;break;
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
						
				}
			}

			spriteCounter++;
			if (spriteCounter > 15) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}

		}

	}
	public void pickUpObject(int i) {
		
		if (i != 999) {
			
			String objectName = gp.obj[i].name;

			switch (objectName) {
			case "key":
				hasKey++;
				gp.obj[i] = null;
				System.out.println("key:" + hasKey);
				break;
			case "door":
				if (hasKey > 0) {
					gp.obj[i] = null;
					hasKey--;
				}
				System.out.println("key:" + hasKey);
				break;
			}

		}
	}


	public void draw(Graphics2D g2) {

//		g2.setColor(Color.white);
//
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;
		switch (direction) {
		case "up":
			image = up1;
			if (spriteNum == 1) {
				image = up1;

			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			image = down1;
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			image = left1;
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			image = right1;
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;

		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

	}

}
