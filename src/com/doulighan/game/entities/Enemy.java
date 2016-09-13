package com.doulighan.game.entities;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.states.Level1;


public class Enemy extends Entity {


  public Enemy(Level1 level, int x, int y) {
    super(level);
    name = "goon";

    this.x = x;
    this.y = y;

    width = 32;
    height = 32;
    cwidth = 30;
    cheight = 32;

    moving = true;
    facingLeft = false;

    moveSpeed = .5;
    airFriction = 1.0;
    fallSpeed = .3;
    maxFallSpeed = 4.0;
  }

  private void getNextPosition() {

    if (moving) {
      if (rightBlocked) {
        facingLeft = true;
      }
      if (leftBlocked) {
        facingLeft = false;
      }
      if (facingLeft) {
        dx = -moveSpeed;
      }
      else if (!facingLeft) {
        dx = moveSpeed;
      }
      else {
        dx = 0;
      }

      if (falling) {
        dy += fallSpeed;
        if (fallSpeed > maxFallSpeed) {
          dy = maxFallSpeed;
        }
      }
    }
  }
  public void tick() {
    getNextPosition();
    checkTileCollision();
    setPosition(xtemp, ytemp);
    if (dx == 0) {
      x = (int) x;
    }
  }

  public void render(Screen screen) {
    int xOffset = (int) x - height / 2;
    int yOffset = (int) y - width / 2;
    screen.render(xOffset, yOffset, 0, 11, false, false);
  }


}
