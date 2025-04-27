package com.comp301.a08dungeon.model.pieces;

public class Hero extends APiece implements MovablePiece {
  public Hero() {
    super("hero", "src/main/resources/FairyHero.png");
  }

  public CollisionResult collide(Piece other) {
    int points = 0;
    if (other == null) {
      return new CollisionResult(points, CollisionResult.Result.CONTINUE);
    }
    if (other.getName().equals("treasure")) {
      points += 100;
      return new CollisionResult(points, CollisionResult.Result.CONTINUE);
    }
    if (other.getName().equals("enemy")) {
      return new CollisionResult(points, CollisionResult.Result.GAME_OVER);
    }
    if (other.getName().equals("exit")) {
      return new CollisionResult(points, CollisionResult.Result.NEXT_LEVEL);
    } else {
      throw new IllegalArgumentException();
    }
  }
}
