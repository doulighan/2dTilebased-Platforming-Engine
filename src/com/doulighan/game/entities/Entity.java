package com.doulighan.game.entities;

import java.awt.Rectangle;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.states.Level1;

public abstract class Entity {

	protected String name;
	
	protected Level1 level; 
	protected int tileSize = 32;

	protected double x, y;
	
	protected double dx, dy;

	protected int width, height;
	protected int cwidth, cheight;

	protected boolean facingLeft;

	protected int currRow, currCol;
	protected double xdest, ydest;
	protected double xtemp, ytemp;
	protected boolean leftBlocked, rightBlocked;
	protected int xmap, ymap;
	protected boolean topLeft, topRight, botLeft, botRight;


	protected boolean left, right, up, down;
	protected boolean jumping, falling;
	protected boolean moving;

	protected double moveSpeed;
	protected double maxSpeed;
	protected double airFriction;
	protected double groundFriction;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double gravity;


	public Entity(Level1 level) {
		init(level);
	}

	public final void init(Level1 level) {
		this.level = level;
		tileSize = 32;
		facingLeft = false;
	}

	public boolean intersects(Entity e) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = e.getRectangle();
		return r1.contains(r2);
	}

	public boolean contains(Rectangle r2) {
		return this.getRectangle().contains(r2);
	}

	public Rectangle getRectangle()	{
		return new Rectangle((int)x - cwidth / 2, (int)y - cheight / 2, cwidth, cheight);
	}

	public void calculateCorners(double x, double y) {
		//System.out.println(x + " " + y);
		int leftTile = (int) (x - cwidth/2) / 32;
		int rightTile = (int) (x + cwidth/2 - 1) / 32;
		int topTile = (int) (y - cheight/2) / 32;
		int botTile = (int)	(y + cheight/2 - 1) / 32;
		//int diagonal tiles?
		//System.out.println(topTile + " " + botTile + " " + leftTile + " " + rightTile);
		if(topTile < 0 || botTile >= level.getHeight() << 5 ||
				leftTile < 0 || rightTile >= level.getWidth() << 5) {
			topLeft = topRight = botLeft = botRight = true;
			return;
		}
		topLeft = level.getTile(leftTile, topTile).isSolid();
		topRight = level.getTile(rightTile, topTile).isSolid();
		botLeft = level.getTile(leftTile, botTile).isSolid();
		botRight = level.getTile(rightTile, botTile).isSolid();		
		//System.out.println(topLeft + " " + topRight + " " + botLeft + " " + botRight)	;
	}

	public void checkTileCollision() {
		currRow = (int)y >> 5;
		currCol = (int)x >> 5;
		//System.out.println("Y: " + currRow + " X: " + currCol);

		xdest = x + dx;
		ydest = y + dy;
		xtemp = x;
		ytemp = y;

		//calculate Y collision
		calculateCorners(x, ydest);
		if(dy < 0) {                      ///////if moving up
			if(topLeft || topRight) {       //is blocked
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;;
			}
			else {                          //is not blocked
				ytemp += dy;
			}
		}
		if(dy > 0) {                      ///////if moving down
			if(botLeft || botRight) {       //is blocked
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;		
			}
			else {                          //is not blocked
				ytemp += dy;
			}
		}

		//calculate X collision
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || botLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
				leftBlocked = true;

			}
			else {
				xtemp += dx;
				leftBlocked = false;
			}
		}
		if(dx > 0) {
			if(topRight || botRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
				rightBlocked = true;
			}
			else {
				xtemp += dx;
				rightBlocked = false;
			}
		}

		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!botLeft && !botRight) {
				falling = true;
			}	
		}
	}

	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	public boolean isFacingRight() { return facingLeft; }
  public boolean isFalling() { return falling; }
	
	public int getCurrCol() { return currCol; }
	public int getCurrRow()	{ return currRow; }

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public abstract void tick();

	//public void setOffset(Screen screen) {
	//	xmap = screen.getXOffset();
		//ymap = screen.getYOffset();
	//}
	public void render(Screen screen) {
	}
}


