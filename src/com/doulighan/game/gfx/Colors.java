package com.doulighan.game.gfx;

//parse colors from long int index
public class Colors {

  public static int get(int color1, int color2, int color3, int color4) {
    return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1);
  }

  private static int get(int color) {
    if (color < 0) {
      return 255;
    }
    int r = color / 100 % 10; // return value 0-5 for R channel
    int g = color / 10 % 10;  // 0-5 for G
    int b = color % 10;     // 0-5 for B
    return r * 36 + g * 6 + b;
  }
}
