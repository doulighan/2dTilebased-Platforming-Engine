package com.doulighan.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * TODO - Render parallax internally
 * TODO - Set up menu bg
 */

public class Background {

  private String path;

  private BufferedImage image, image2;
  private int[] pixels;

  private int width;
  private int height;

  private int xScroll = 0;
  private int yScroll;

  private double xScale;
  private double yScale;


  public Background(String path, double xScale, double yScale) {
    this.path = path;
    this.xScale = xScale;
    this.yScale = yScale;
    init();
  }

  public Background(String path, double xScale, double yScale, int xScroll, int yScroll) {
    this.path = path;
    this.xScale = xScale;
    this.yScale = yScale;
    this.xScroll = xScroll;
    this.yScroll = yScroll;
    init();
  }
  private void init() {
    loadBackground();
  }

  private void loadBackground() {
    try {
      image = ImageIO.read(getClass().getResourceAsStream(path));
      width = image.getWidth();
      height = image.getHeight();
      pixels = image.getRGB(0, 0, width, height, null, 0, width);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////////

  public void render(Screen screen, double x, double y) {
    screen.renderBackground(this, (int) x, (int) y);
    if (x + width < screen.getWidth()) {
      render(screen, x + width, y);
    }
    if (y + height < screen.getHeight()) {
      render(screen, x, y + height);
    }
  }

  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public double getScale() { return xScale; }
  public double getXScale() { return xScale; }
  public double getYScale() { return yScale;}
  public int getPixel(int i) { return pixels[i]; }
}
