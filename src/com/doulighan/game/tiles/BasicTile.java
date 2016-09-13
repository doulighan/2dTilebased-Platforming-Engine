package com.doulighan.game.tiles;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.states.Level1;


public class BasicTile extends Tile {

  protected int tileid;
  protected int tileColor;

  public BasicTile(int id, int x, int y, boolean isSolid, int tileLoad) {
    super(id, isSolid, false, tileLoad);
    this.tileid = (x + y);
    this.xTile = x;
    this.yTile = y;
  }

  public void render(Screen screen, Level1 level, int x, int y) {
    screen.render(x, y, tileid, tileColor);
  }

  public void renderFullColor(Screen screen, int x, int y) {
    screen.render(x, y, xTile, yTile);
  }

}
