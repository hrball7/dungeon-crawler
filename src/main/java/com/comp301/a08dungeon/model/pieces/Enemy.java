package com.comp301.a08dungeon.model.pieces;

public class Enemy extends APiece implements MovablePiece {

  public Enemy() {
    super("enemy", "src/main/resources/EnemyPlant.png");
  }

  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }
    if (other.getName().equals("hero")) {
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    }
    if (other.getName().equals("treasure")) {
      other.setPosn(null);
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else throw new IllegalArgumentException();
  }
}