package com.doulighan.game.entities;

import java.awt.Rectangle;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.states.Level1;


public class Player extends Entity {

  private Rectangle ar;
  private Rectangle aur;
  private Rectangle cr;
  private int scale;

  //animation
  private static final int APS = 60 / 6;
  private boolean still = true;
  private int animate = 0;
  private int yTile = 9;
  private int xTile = 0;
  private int currentAnimation = 6;
  private int[] idle = {6, 7, 8, 9, 0, 0, 1, 2, 3, 4, 5};

  public Player(Level1 level, double x, double y) {
    super(level);
    this.name = "Player";
    this.x = x;
    this.y = y;
    this.scale = 1;
    up = down = left = right = false;


    ar = new Rectangle(0, 0, 0, 0);
    ar.width = 32;
    ar.height = 32;
    //aur = new Rectangle((int)x -15, (int)y - 45, 30, 30);
    cr = new Rectangle(0, 0, 0, 0);
    cr.width = 34;
    cr.height = 34;

    width = 32;
    height = 32;
    cwidth = 16;
    cheight = 30;

    moveSpeed = 1.0;
    maxSpeed = 3.6;
    airFriction = 0.15;
    groundFriction = 0.6;
    fallSpeed = 0.25;
    maxFallSpeed = 10.0;
    jumpStart = -8.8;
    gravity = 0.3;
  }

  private void getNextPosition() {
    if (left) {
      facingLeft = true;
      dx -= moveSpeed;
      if (dx < -maxSpeed) {
        dx = -maxSpeed;
      }
    }
    else if (right) {
      facingLeft = false;
      dx += moveSpeed;
      if (dx > maxSpeed) {
        dx = maxSpeed;
      }
    }
    else {
      if (dx > 0) {
        if (falling) {
          dx -= airFriction;
        }
        else {
          dx -= groundFriction;
        }
        if (dx < 0) {
          dx = 0;
        }
      }
      else if (dx < 0) {
        if (falling) {
          dx += airFriction;
        }
        else {
          dx += groundFriction;
        }
        if (dx > 0) {
          dx = 0;
        }
      }
    }

    if (jumping && !falling) {
      dy = jumpStart;
    }
    if (falling) {
      jumping = false;
      dy += fallSpeed;
      if (dy < 0) {
        dy += gravity;
      }
      if (dy > maxFallSpeed) {
        dy = maxFallSpeed;
      }
    }

    if (left || right || jumping || falling) {
      still = false;
      currentAnimation = 0;
    }

    else {
      still = true;
    }

  }

  private void animate() {
    if (still) {
      animate++;
      boolean nextAnimation = false;
      if (animate >= APS) {
        animate = 0;
        nextAnimation = true;
      }
      if (nextAnimation) {
        currentAnimation++;
        if (currentAnimation >= idle.length) {
          currentAnimation = 0;
        }
        xTile = idle[currentAnimation];
      }
    }
    if (!facingLeft) {
      yTile = 9;
    }
    else {
      yTile = 10;
    }
  }


  public void tick() {
    getNextPosition();
    checkTileCollision();
    setPosition(xtemp, ytemp);
    animate();
  }


  public void render(Screen screen) {
    int size = width;
    int xOffset = (int) x - size / 2;
    int yOffset = (int) y - size / 2;
    screen.render(xOffset, yOffset, xTile, yTile, false, false);
  }

  public void setUp(boolean b) { up = b;}
  public void setDown(boolean b) { down = b; }
  public void setLeft(boolean b) { left = b; }
  public void setRight(boolean b) { right = b; }
  public void setJumping(boolean b) { jumping = b; }
}





