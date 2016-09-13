package com.doulighan.game.gfx;

import com.doulighan.game.main.Game;


public class Screen {

  public int[] pixels;
  private int xOffset = 0;
  private int yOffset = 0;
  public int width;
  public int height;

  private SpriteSheet sheet;
  private int spriteSize;

  public Screen(int width, int height, SpriteSheet sheet) {
    this.width = width;
    this.height = height;
    this.sheet = sheet;
    this.spriteSize = sheet.getSpriteSize();
    pixels = new int[width * height];
  }

  public void render(int xPos, int yPos, int xTile, int yTile) {
    this.render(xPos, yPos, xTile, yTile, false, false);
  }

  public void render(int xPos, int yPos, int xTile, int yTile, boolean mirrorHor, boolean mirrorVer) {
    xPos -= xOffset;
    yPos -= yOffset;
    int tileOffset = (xTile * spriteSize) + (yTile * spriteSize) * sheet.getWidth();
    for (int y = 0; y < spriteSize; y++) {
      if (y + yPos < 0 || y + yPos >= height) {
        continue;
      }
      int ySheet = y;
      if (mirrorVer) {
        ySheet = (spriteSize - 1) - y;
      }
      for (int x = 0; x < spriteSize; x++) {
        if (x + xPos < 0 || x + xPos >= width) {
          continue;
        }
        int xSheet = x;
        if (mirrorHor) {
          xSheet = (spriteSize - 1) - x;
        }
        if (sheet.getPixel(xSheet + ySheet * sheet.getWidth() + tileOffset) != -1) {
          pixels[(x + xPos) + (y + yPos) * width] = sheet.getPixel(xSheet + ySheet * sheet.getWidth() + tileOffset);
        }
      }
    }
  }

  public void renderBackground(Background bg, int xPos, int yPos) {
    for (int y = 0; y < bg.getHeight(); y++) {
      if (y + yPos < 0 || y + yPos >= height - 1) {
        continue;
      }
      for (int x = 0; x < bg.getWidth(); x++) {
        if (x + xPos < 0 || x + xPos >= width - 1) {
          continue;
        }
        if (bg.getPixel(x + y * bg.getWidth()) != -65537 && bg.getPixel(x + y * bg.getWidth()) != -1) {
          pixels[(x + xPos) + (y + yPos) * width] = bg.getPixel(x + y * bg.getWidth());
        }
      }
    }
  }
  public static Screen screen = new Screen(Game.WIDTH, Game.HEIGHT, SpriteSheet.sheet);

  public void setOffset(int x, int y) {
    this.xOffset = x;
    this.yOffset = y;
  }
  public int getXOffset() { return xOffset; }
  public int getYOffset() { return yOffset; }

  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public int[] getPixels() { return pixels; }
  public int getPixel(int i) { return pixels[i]; }

}
