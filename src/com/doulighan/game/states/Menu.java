package com.doulighan.game.states;

import com.doulighan.game.gfx.Background;
import com.doulighan.game.gfx.SpriteSheet;
import com.doulighan.game.handlers.GSM;
import com.doulighan.game.handlers.Keys;
import com.doulighan.game.main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * TODO - Create SpriteSheet-to-text Class
 * TODO - Background rendering
 * TODO - Menu text and Selector cleanup
 * dunno
 */

public class Menu extends GameState {

  private int[] pixels;

  //animation
  private static final int ANIMATIONS_PER_SECOND = 60 / 8;
  private int animate = 0;
  private int xTile, yTile;
  private int currentAnimation;
  private int[] selectorFrames = {0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1};

  private int currentChoice = 0;
  private String[] options = {" START " + " OPTIONS " + " EXIT "};

  private Background background;

  public Menu(GSM gsm) {
    super(gsm);
    sheet = new SpriteSheet("/HUD/menu.png", 16);
    this.yTile = 0;
    this.xTile = 0;
    background = new Background("/HUD/menuBG.png", 0, 0);


    pixels = new int[Game.WIDTH * Game.HEIGHT];
  }

  public void animate() {
    animate++;
    boolean nextAnimation = false;
    if (animate >= ANIMATIONS_PER_SECOND) {
      animate = 0;
      nextAnimation = true;
    }
    if (nextAnimation) {
      currentAnimation++;
      if (currentAnimation >= selectorFrames.length) {
        currentAnimation = 0;
      }
      xTile = selectorFrames[currentAnimation];
    }
  }

  public void tick() {
    handleInput();
    animate();
  }



  //EXTREMELY CLUDGE - remember to make Background class able to handle non-parallax images and make Background render auto-parallax
  public void render() {
    clearSelector();
    renderBackground("/HUD/menuBG.png");
    renderSelector(104, ((Game.HEIGHT / 2) + 39) + (currentChoice * 24), xTile, yTile);
    renderString((Game.WIDTH / 2) - 20, (Game.HEIGHT / 2) + 40, 0, 1, 3);
    renderString((Game.WIDTH / 2) - 28, (Game.HEIGHT / 2) + 64, 0, 2, 4);
    renderString((Game.WIDTH / 2) - 16, (Game.HEIGHT / 2) + 88, 0, 3, 2);

  }

  private void renderSelector(int xPos, int yPos, int xTile, int yTile) {
    int tileOffset = (xTile * sheet.getSpriteSize()) + (yTile * sheet.getSpriteSize()) * sheet.getWidth();
    for (int y = 0; y < sheet.getSpriteSize(); y++) {
      if (y + yPos < 0 || y + yPos >= Game.WIDTH) {
        continue;
      }
      for (int x = 0; x < sheet.getSpriteSize(); x++) {
        if (x + xPos < 0 || x + xPos >= Game.HEIGHT) {
          continue;
        }
        pixels[(x + xPos) + (y + yPos) * Game.WIDTH] = sheet.getPixel(x + y * sheet.getWidth() + tileOffset);
      }
    }
  }

  private void renderBackground(String path) {
    try {
      BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
      int bgw = image.getWidth();
      int bgh = image.getHeight();
      pixels = image.getRGB(0, 0, bgw, bgh, null, 0, bgw);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void renderString(int xPos, int yPos, int xTile, int yTile, int tileLength) {
    int tileOffset = (xTile * sheet.getSpriteSize()) + (yTile * sheet.getSpriteSize()) * sheet.getWidth();
    for (int y = 0; y < sheet.getSpriteSize(); y++) {
      if (y + yPos < 0 || y + yPos >= Game.WIDTH) {
        continue;
      }
      for (int x = 0; x < sheet.getSpriteSize() * tileLength; x++) {
        if (x + xPos < 0 || x + xPos >= Game.HEIGHT) {
          continue;
        }
        pixels[(x + xPos) + (y + yPos) * Game.WIDTH] = sheet.getPixel(x + y * sheet.getWidth() + tileOffset);
      }
    }
  }

  private void clearSelector() {
    int yPos = (Game.HEIGHT / 2) - 7;
    int xPos = 104;
    for (int y = 0; y < 80; y++) {
      for (int x = 0; x < 20; x++) {
        pixels[(x + xPos) + (y + yPos) * Game.WIDTH] = 0xFF000000;
      }
    }
  }

  public void handleInput() {
    if (Keys.isPressed(Keys.ENTER)) {
      select();
    }

    if (Keys.isPressed(Keys.UP)) {
      if (currentChoice == 0) {
        currentChoice = 2;
      }
      else {
        currentChoice--;
      }
    }
    if (Keys.isPressed(Keys.DOWN)) {
      if (currentChoice == 2) {
        currentChoice = 0;
      }
      else {
        currentChoice++;
      }
    }
  }

  private void select() {
    if (currentChoice == 0) {
      gsm.setState(GSM.LEVEL1);
    }
    else if (currentChoice == 2) {
      System.exit(0);
    }
  }
  public int getPixel(int i) {
    return this.pixels[i];
  }

  @Override
  public void dispose() {
  }

  @Override
  public int getPlayerX() {
    return 0;
  }

  @Override
  public int getPlayerY() {
    return 0;
  }

  @Override
  public int getPlayerCol() {
    return 0;
  }

  @Override
  public int getPlayerRow() {
    return 0;
  }

  @Override
  public boolean getPlayerFalling() {
    return false;
  }

}
