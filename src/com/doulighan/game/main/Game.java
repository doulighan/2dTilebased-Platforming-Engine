package com.doulighan.game.main;

import com.doulighan.game.handlers.GSM;
import com.doulighan.game.handlers.Keys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class Game extends Canvas implements Runnable, KeyListener, FocusListener {

  //JFrame stuff
  public static final int WIDTH = 320;
  public static final int HEIGHT = 240;
  public static final int SCALE = 3;
  public static final Dimension DIM = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
  public static final String NAME = "Game";
  private static final long serialVersionUID = 1L;
  //Game loop stuff
  private static final double TICKS_PER_SECOND = 60;
  public static boolean debug = false;
  private static FocusListener fl;
  private static boolean focused;
  private static int tickCount = 0;
  private static int secondsElapsed = tickCount / 60;
  private int ticksPS;
  private int framesPS;
  private boolean running = false;
  //Objects
  private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
  private GSM gsm;

  ////constructor////
  public Game() {
    this.setMinimumSize(new Dimension(DIM));
    this.setMaximumSize(new Dimension(DIM));
    this.setPreferredSize(new Dimension(DIM));
    JFrame frame = new JFrame(NAME);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(this, BorderLayout.CENTER);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  ///main
  public static void main(String[] args) {
    new Game().start();
  }
  public synchronized void start() {
    running = true;
    System.out.print("Running...\n");
    addKeyListener(this);
    addFocusListener(this);
    Thread thread = new Thread(this);
    thread.start();
    requestFocus();
  }
  public synchronized void restart() {
    running = true;
  }
  public synchronized void stop() {
    running = false;
  }
  public void run() {
    int ticks = 0;
    int frames = 0;
    long currentTime;
    double nsPerTick = 1000000000D / TICKS_PER_SECOND;
    double accumulator = 0;
    boolean shouldRender = true;
    long lastTime = System.nanoTime();
    long lastTimer = System.currentTimeMillis();

    init();

    while (running) {

      while (! focused) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      currentTime = System.nanoTime();
      double frameTime = currentTime - lastTime;
      accumulator += frameTime / nsPerTick;
      lastTime = currentTime;
      while (accumulator >= 1) {
        ticks++;
        tick();
        accumulator--;
        secondsElapsed = tickCount / 60;
      }

      frames++;
      render();

      if (System.currentTimeMillis() - lastTimer >= 1000) {
        lastTimer += 1000;
        ticksPS = ticks;
        framesPS = frames;
        frames = 0;
        ticks = 0;
      }
    }
  }
  public void init() {
    gsm = new GSM(this);
  }
  private void tick() {
    tickCount++;
    gsm.tick();
    Keys.tick();
  }
  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }

    gsm.render();
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        pixels[x + y * WIDTH] = gsm.peekState().getPixel(x + y * WIDTH);
      }
    }


    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    g.setColor(Color.WHITE);
    String data = ticksPS + " ticks, " + framesPS + " frames";
    g.drawString(data, 5, 15);
    data = "X: " + gsm.peekState().getPlayerX() + " Y: " + gsm.peekState().getPlayerY();
    g.drawString(data, 5, 30);
    data = "Col: " + gsm.peekState().getPlayerCol() + " Row: " + gsm.peekState().getPlayerRow();
    g.drawString(data, 5, 45);
    data = "Seconds elapsed: " + secondsElapsed;
    g.drawString(data, 5, 60);
    data = "Falling: " + gsm.peekState().getPlayerFalling();
    g.drawString(data, 5, 75);

    g.dispose();
    bs.show();
  }
  public void keyTyped(KeyEvent e) {
  }
  public void keyPressed(KeyEvent e) {
    Keys.keySet(e.getKeyCode(), true);
  }
  public void keyReleased(KeyEvent e) {
    Keys.keySet(e.getKeyCode(), false);
  }
  @Override
  public void focusGained(FocusEvent e) {
    System.out.println("focus gained");
    focused = true;
  }
  @Override
  public void focusLost(FocusEvent e) {
    System.out.println("focus lost");
    focused = false;
  }

}

