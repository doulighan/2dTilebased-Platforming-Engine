package com.doulighan.game.handlers;

import com.doulighan.game.main.Game;
import com.doulighan.game.states.GameState;
import com.doulighan.game.states.Level1;
import com.doulighan.game.states.Menu;

import java.util.Stack;


public class GSM {

  private Game game;
  private Stack<GameState> gameStates;

  public static final int MENU = 000010;
  public static final int PAUSE = 000001;
  public static final int LEVEL1 = 100000;
  public int currentState;

  public GSM(Game game) {
    this.game = game;
    gameStates = new Stack<GameState>();
    if (!Game.debug) {
      pushState(MENU);
    }
    if (Game.debug) {
      pushState(LEVEL1);
    }
  }

  public void tick() {
    gameStates.peek().tick();
  }

  public void render() {
    gameStates.peek().render();
  }

  public GameState peekState() {
    return gameStates.peek();
  }

  private GameState getState(int state) {
    if (state == MENU) {
      return new Menu(this);
    }
    if (state == LEVEL1) {
      return new Level1(this);
    }
    return null;
  }

  public void setState(int state) {
    popState();
    pushState(state);
  }

  public void pushState(int state) {
    gameStates.push(getState(state));
  }

  public void popState() {
    GameState g = gameStates.pop();
    g.dispose();
  }

  public void dispose() {

  }

  public Game getGame() { return game; }
}
