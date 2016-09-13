package com.doulighan.game.states;

import com.doulighan.game.entities.Enemy;
import com.doulighan.game.entities.Entity;
import com.doulighan.game.entities.Player;
import com.doulighan.game.gfx.Background;
import com.doulighan.game.gfx.Screen;
import com.doulighan.game.gfx.SpriteSheet;
import com.doulighan.game.handlers.GSM;
import com.doulighan.game.handlers.Keys;
import com.doulighan.game.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class Level1 extends GameState {


  private byte[] tiles;
  private BufferedImage image;
  private int width, height;
  private Player player;
  private Enemy enemy;
  private Background bg, bg2;

  private List<Entity> entities = new ArrayList<Entity>();

/////// Constructor ////////////////////////////////////////////////////

  public Level1(GSM gsm) {
    super(gsm);
    init();
  }

  private void init() {
    screen = Screen.screen;
    sheet = SpriteSheet.sheet;
    player = new Player(this, 40, 40);
    enemy = new Enemy(this, 40, 40);

    bg = new Background("/bg_stars1.png", .08, .08);
    bg2 = new Background("/bg_stars2.png", .10, .10);
    loadTiles("/testLevel.png");

    this.addEntity(enemy);
    this.addEntity(player);
  }


/////// Loading level and tiles /////////////////////////////////////////

  private void loadTiles(String path) {
    try {
      image = ImageIO.read(Level.class.getResource(path));
      width = image.getWidth();
      height = image.getHeight();
      tiles = new byte[width * height];
      this.setTiles();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void setTiles() {
    int[] tileLoads = image.getRGB(0, 0, width, height, null, 0, width);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        tileCheck:
        for (Tile t : Tile.tiles) {
          if (t != null && t.getTileLoad() == tileLoads[x + y * width]) {
            this.tiles[x + y * width] = t.getid();
            break tileCheck;
          }
        }
      }
    }
  }


/////// update //////////////////////////////////////////////////////////

  public void tick() {
    handleInput();
    for (Entity e : entities) {
      e.tick();
    }
  }

/////// render ///////////////////////////////////////////////////////////

  public void render() {
    renderBackgrounds();
    renderTilesFullColor(screen, player.getx() - screen.getWidth() / 2, player.gety() - screen.getHeight() / 2);
    renderEntities();
    player.render(screen);
  }

  private void renderTilesFullColor(Screen screen, int xOffset, int yOffset) {
    if (xOffset < 0) {
      xOffset = 0;
    }
    if (xOffset > (width << 5) - screen.getWidth()) {
      xOffset = ((width << 5) - screen.getWidth());
    }
    if (yOffset < 0) {
      yOffset = 0;
    }
    if (yOffset > (height << 5) - screen.getHeight()) {
      yOffset = ((height << 5) - screen.getHeight());
    }

    screen.setOffset(xOffset, yOffset);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (getTile(x, y) == Tile.CLEAR) {
          continue;
        }
        getTile(x, y).renderFullColor(screen, x << 5, y << 5);
      }
    }
  }

  public Tile getTile(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return Tile.VOID;
    }
    return Tile.tiles[tiles[x + y * width]];
  }


  private void renderEntities() {
    for (Entity e : entities) {
      e.render(screen);
    }
  }

  private void renderBackgrounds() {
    bg.render(screen, screen.getXOffset() * -bg.getScale(), screen.getYOffset() * -bg.getScale());
    bg2.render(screen, screen.getXOffset() * -bg2.getScale(), screen.getYOffset() * -bg2.getScale());
  }


/////// Other ///////////////////////////////////////////////////////////////

  private void addEntity(Entity e) {
    this.entities.add(e);
  }

  protected void handleInput() {
    player.setJumping(Keys.keyState[Keys.UP]);
    player.setDown(Keys.keyState[Keys.DOWN]);
    player.setLeft(Keys.keyState[Keys.LEFT]);
    player.setRight(Keys.keyState[Keys.RIGHT]);
    if (Keys.isPressed(Keys.ESCAPE)) {
      System.exit(0);
    }
  }

  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public byte[] getTiles() { return tiles; }

  public int getPlayerX() { return player.getx(); }
  public int getPlayerY() { return player.gety(); }
  public int getPlayerCol() { return player.getCurrCol(); }
  public int getPlayerRow() { return player.getCurrRow(); }
  public boolean getPlayerFalling() {return player.isFalling(); }


  public void dispose() {
  }
}
