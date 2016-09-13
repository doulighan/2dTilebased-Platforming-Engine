package com.doulighan.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


// Background is ff00ff
// Grid lines are 7f007f
// 1=000000, 2=555555, 3=AAAAAA, 4=FFFFFF
public class SpriteSheet {

  private String path;
  private int width;
  private int height;
  private int numXTiles;
  private int numYTiles;
  private int spriteSize;

  public int[] pixels;

  public SpriteSheet(String path, int spriteSize) {

    //load buffered image from file
    BufferedImage image = null;
    try {
      image = ImageIO.read(SpriteSheet.class.getResource(path));
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (image == null) {
      System.out.println("IMAGE NULL");
      return;
    }

    this.path = path;
    this.width = image.getWidth();
    this.height = image.getHeight();
    this.spriteSize = spriteSize;
    numXTiles = width / spriteSize;
    numYTiles = height / spriteSize;

    pixels = image.getRGB(0, 0, width, height, null, 0, width);
  }

  public static SpriteSheet sheet = new SpriteSheet("/SpriteSheets/ss_temp.png", 32);

  //getters
  public int[] getPixels() { return pixels; }
  public int getPixel(int i) { return pixels[i]; }
  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public int getNumXTiles() { return numXTiles; }
  public int getNumYTiles() { return numYTiles; }
  public int getSpriteSize() { return spriteSize; }


}
