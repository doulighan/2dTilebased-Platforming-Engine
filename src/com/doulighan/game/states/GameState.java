package com.doulighan.game.states;

import com.doulighan.game.gfx.Screen;
import com.doulighan.game.gfx.SpriteSheet;
import com.doulighan.game.handlers.GSM;
import com.doulighan.game.main.Game;


public abstract class GameState {

  protected GSM gsm;
  protected Game game;

  protected Screen screen;
  protected SpriteSheet sheet;

  public GameState(GSM gsm) {
    this.gsm = gsm;
    ;
    this.game = gsm.getGame();
  }

  protected abstract void handleInput();
  public abstract void tick();
  public abstract void render();
  public abstract void dispose();

  public int[] getPixels() {
    return screen.getPixels();
  }
  public int getPixel(int i) {
    return screen.getPixel(i);
  }
  public SpriteSheet getSpriteSheet() {
    return sheet;
  }
  public Screen getScreen() {
    return screen;
  }

  public abstract int getPlayerX();
  public abstract int getPlayerY();
  public abstract int getPlayerCol();
  public abstract int getPlayerRow();
  public abstract boolean getPlayerFalling();
}

