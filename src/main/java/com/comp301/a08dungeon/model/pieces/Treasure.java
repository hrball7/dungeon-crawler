package com.comp301.a08dungeon.model.pieces;

public class Treasure extends APiece {
  private final int value;

  public Treasure() {
    super("treasure", "src/main/resources/Images/Treasure.png");
    value = 100;
  }

  public int getValue() {
    return value;
  }
}
