package com.comp301.a08dungeon.model.board;

import com.comp301.a08dungeon.model.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardImpl implements Board {
  private final int width;
  private final int height;
  private final Random random = new Random();
  private final Piece[][] board;
  private Hero hero;
  private Exit exit;
  private List<Treasure> treasuresList = new ArrayList<>();
  private List<Wall> wallsList = new ArrayList<>();
  private List<Enemy> enemiesList = new ArrayList<>();
  private Posn heroPos;
  private Posn exitPos;

  public BoardImpl(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException();
    }
    this.height = height;
    this.width = width;
    this.board = new Piece[this.height][this.width];
  }

  public BoardImpl(Piece[][] initialBoard) {
    this.height = initialBoard.length;
    if (height == 0) {
      throw new IllegalArgumentException();
    }
    this.width = initialBoard[0].length;
    if (width == 0) {
      throw new IllegalArgumentException();
    }
    this.board = new Piece[height][width];

    hero = null;
    exit = null;

    for (int i = 0; i < height; i++) {
      if (initialBoard[i].length != width) {
        throw new IllegalArgumentException();
      }
      for (int j = 0; j < width; j++) {
        Piece piece = initialBoard[i][j];
        board[i][j] = piece;
        if (piece != null) {
          Posn pos = new Posn(i, j);
          piece.setPosn(pos);
          if (piece instanceof Hero) {
            hero = (Hero) piece;
            heroPos = pos;
          } else if (piece instanceof Exit) {
            exit = (Exit) piece;
            exitPos = pos;
          } else if (piece instanceof Enemy) {
            enemiesList.add((Enemy) piece);
          } else if (piece instanceof Treasure) {
            treasuresList.add((Treasure) piece);
          } else if (piece instanceof Wall) {
            wallsList.add((Wall) piece);
          }
        }
      }
    }
  }

  @Override
  public void init(int enemies, int treasures, int walls) {

    // clear the board
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = null;
      }
    }
    if ((enemies + treasures + walls) >= width * height) {
      throw new IllegalArgumentException();
    }

    enemiesList.clear();
    treasuresList.clear();
    wallsList.clear();
    hero = null;
    exit = null;
    heroPos = null;
    exitPos = null;

    // create list of used positions, everytime i place something, add pos to list
    List<Posn> usedPosn = new ArrayList<>(height * width);

    // place hero
    Posn pos = getRandoPos(usedPosn);
    hero = new Hero();
    hero.setPosn(pos);
    heroPos = pos;
    board[pos.getRow()][pos.getCol()] = hero;
    usedPosn.add(pos);

    // place exit
    pos = getRandoPos(usedPosn);
    exit = new Exit();
    exit.setPosn(pos);
    exitPos = pos;
    board[pos.getRow()][pos.getCol()] = exit;
    usedPosn.add(pos);

    // place enemies
    for (int i = 0; i < enemies; i++) {
      pos = getRandoPos(usedPosn);
      Enemy enemy = new Enemy();
      enemy.setPosn(pos);
      board[pos.getRow()][pos.getCol()] = enemy;
      enemiesList.add(enemy);
      usedPosn.add(pos);
    }

    // place treasures
    for (int i = 0; i < treasures; i++) {
      pos = getRandoPos(usedPosn);
      Treasure treasure = new Treasure();
      treasure.setPosn(pos);
      board[pos.getRow()][pos.getCol()] = treasure;
      treasuresList.add(treasure);
      usedPosn.add(pos);
    }

    // place walls
    for (int i = 0; i < walls; i++) {
      pos = getRandoPos(usedPosn);
      Wall wall = new Wall();
      wall.setPosn(pos);
      board[pos.getRow()][pos.getCol()] = wall;
      wallsList.add(wall);
      usedPosn.add(pos);
    }
  }

  // helper method for init
  public Posn getRandoPos(List<Posn> used) {
    Posn pos = new Posn(0, 0);
    boolean found = false;

    while (!found) {
      int row = random.nextInt(height);
      int col = random.nextInt(width);

      pos = new Posn(row, col);
      if (!used.contains(pos)) {
        found = true;
      }
    }
    return pos;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Piece get(Posn posn) {
    if (posn.getRow() < 0
            || posn.getRow() >= height
            || posn.getCol() < 0
            || posn.getCol() >= width) {
      throw new IndexOutOfBoundsException();
    }
    return board[posn.getRow()][posn.getCol()];
  }

  @Override
  public void set(Piece p, Posn newPos) {
    if (newPos.getRow() < 0
            || newPos.getRow() >= height
            || newPos.getCol() < 0
            || newPos.getCol() >= width) {
      throw new IndexOutOfBoundsException();
    }
    Posn oldPos = p.getPosn();
    if (oldPos != null) {
      board[oldPos.getRow()][oldPos.getCol()] = null;
    }

    board[newPos.getRow()][newPos.getCol()] = p;
    p.setPosn(newPos);

    if (p instanceof Hero) {
      heroPos = newPos;
    } else if (p instanceof Exit) {
      exitPos = newPos;
    }
  }

  @Override
  public CollisionResult moveHero(int drow, int dcol) {
    int newRow = heroPos.getRow() + drow;
    int newCol = heroPos.getCol() + dcol;
    int score = 0;
    if (newRow >= height || newRow < 0 || newCol >= width || newCol < 0) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }

    Piece targetP = board[newRow][newCol];
    Posn newPos = new Posn(newRow, newCol);
    CollisionResult result = new CollisionResult(score, CollisionResult.Result.CONTINUE);

    if (targetP == null) {
      set(hero, newPos);
      heroPos = newPos;
    } else if (targetP instanceof Wall) {
      result = new CollisionResult(score, CollisionResult.Result.CONTINUE);
    } else if (targetP instanceof Enemy) {
      set(hero, newPos);
      heroPos = newPos;
      result = new CollisionResult(score, CollisionResult.Result.GAME_OVER);
    } else if (targetP instanceof Treasure) {
      treasuresList.remove((Treasure) targetP);
      set(hero, newPos);
      heroPos = newPos;
      score += hero.collide(targetP).getPoints();
      result = new CollisionResult(score, CollisionResult.Result.CONTINUE);
    } else if (targetP instanceof Exit) {
      set(hero, newPos);
      heroPos = newPos;
      result = new CollisionResult(score, CollisionResult.Result.NEXT_LEVEL);
    }

    // move enemies - if game over, return game over result
    for (Enemy enemy : enemiesList) {
      List<Posn> possiblePos = new ArrayList<>();
      Posn enemyPosOld = enemy.getPosn();
      int newEnRow = enemyPosOld.getRow();
      int newEnCol = enemyPosOld.getCol();
      possiblePos.add(new Posn(newEnRow + 1, newEnCol));
      possiblePos.add(new Posn(newEnRow, newEnCol + 1));
      possiblePos.add(new Posn(newEnRow - 1, newEnCol));
      possiblePos.add(new Posn(newEnRow, newEnCol - 1));

      Collections.shuffle(possiblePos);

      for (Posn position : possiblePos) {
        if (position.getRow() >= height
                || position.getRow() < 0
                || position.getCol() >= width
                || position.getCol() < 0) {
          continue;
        }
        Piece targetPos = board[position.getRow()][position.getCol()];
        if (targetPos instanceof Wall || targetPos instanceof Exit || targetPos instanceof Enemy) {
          continue;
        }
        if (targetPos == null || targetPos instanceof Hero) {
          CollisionResult cr = enemy.collide(targetPos);
          if (cr.getResults() == CollisionResult.Result.GAME_OVER) {
            result = new CollisionResult(score, CollisionResult.Result.GAME_OVER);
          }
          set(enemy, position);
          break;
        }
      }
    }
    return result;
  }
}
