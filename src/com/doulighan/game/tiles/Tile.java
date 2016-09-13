package com.doulighan.game.tiles;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.states.Level1;


public abstract class Tile {

  public static final Tile[] tiles = new Tile[256];

  public static final Tile VOID = new BasicTile(255, 0, 0, false, 0xFFF0F0F0);
  public static final Tile CLEAR = new BasicTile(1, 0, 0, false, 0xFF000000);
  public static final Tile CUBE = new BasicTile(6, 0, 1, true, 0xFFFF0000);
  public static final Tile METAL_HOR = new BasicTile(7, 1, 1, true, 0xFF424242);
  public static final Tile METAL_VER = new BasicTile(8, 2, 1, true, 0xFFD3D3D3);

  protected byte id;
  protected int xTile;
  protected int yTile;
  protected boolean solid;
  protected boolean emitter;
  private int tileLoad;

  public Tile(int id, boolean isSolid, boolean isEmitter, int tileLoad) {
    this.id = (byte) id;
    if (tiles[id] != null) {
      throw new RuntimeException("Duplicate tile id on " + id);
    }
    this.solid = isSolid;
    this.emitter = isEmitter;
    tiles[id] = this;
    this.tileLoad = tileLoad;
  }

  public int getTileLoad() { return this.tileLoad; }

  public byte getid() { return id; }
  public boolean isSolid() { return solid; }
  public boolean isEmitter() { return emitter; }
  public int getXTile() { return xTile; }
  public int getYTile() { return yTile; }

  public abstract void render(Screen screen, Level1 level, int x, int y);
  public abstract void renderFullColor(Screen screen, int x, int y);
}
