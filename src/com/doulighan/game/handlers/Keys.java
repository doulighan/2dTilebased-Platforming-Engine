package com.doulighan.game.handlers;

import java.awt.event.KeyEvent;


public class Keys {

  public static final int NUM_KEYS = 16;

  public static boolean keyState[] = new boolean[NUM_KEYS];
  public static boolean prevKeyState[] = new boolean[NUM_KEYS];

  public static int UP = 0;
  public static int DOWN = 1;
  public static int LEFT = 2;
  public static int RIGHT = 3;
  public static int BUTTON1 = 4;
  public static int BUTTON2 = 5;
  public static int BUTTON3 = 6;
  public static int BUTTON4 = 7;
  public static int ENTER = 8;
  public static int ESCAPE = 9;

  public static void keySet(int keyCode, boolean b) {
    if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      keyState[UP] = b;
    }
    else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      keyState[DOWN] = b;
    }
    else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
      keyState[LEFT] = b;
    }
    else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
      keyState[RIGHT] = b;
    }
    else if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
      keyState[ENTER] = b;
    }
    else if (keyCode == KeyEvent.VK_ESCAPE) {
      keyState[ESCAPE] = b;
    }
  }

  public static void tick() {
    for (int i = 0; i < NUM_KEYS; i++) {
      prevKeyState[i] = keyState[i];
    }
  }

  public static boolean isPressed(int i) {
    return keyState[i] && !prevKeyState[i];
  }

  public static boolean anyKeyPress() {
    for (int i = 0; i < NUM_KEYS; i++) {
      if (keyState[i]) {
        return true;
      }
    }
    return false;
  }
}
