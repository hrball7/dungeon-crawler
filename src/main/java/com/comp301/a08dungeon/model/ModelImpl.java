package com.comp301.a08dungeon.model;

import com.comp301.a08dungeon.model.board.Board;
import com.comp301.a08dungeon.model.board.BoardImpl;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.CollisionResult;
import com.comp301.a08dungeon.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private int curScore;
    private int highScore;
    private STATUS status;
    private BoardImpl board;
    private int level;
    private List<Observer> observers = new ArrayList<>();
    private int labelScore;

    public ModelImpl(int width, int height) {
        board = new BoardImpl(width, height);
        status = STATUS.END_GAME;
        highScore = 0;
        curScore = 0;
        level = 0;
    }

    public ModelImpl(Board board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        this.board = (BoardImpl) board;
        status = STATUS.END_GAME;
        highScore = 0;
        curScore = 0;
        level = 0;
    }

    @Override
    public int getWidth() {
        return board.getWidth();
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

    @Override
    public Piece get(Posn p) {
        return board.get(p);
    }

    @Override
    public int getCurScore() {
        return curScore;
    }

    @Override
    public int getHighScore() {
        return highScore;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public void startGame() {
        status = STATUS.IN_PROGRESS;
        this.curScore = 0;
        level = 1;
        int enemy = level + 1;
        int treasure = level + 2;
        int area = board.getHeight() * board.getWidth();
        int wall = 4;

        board.clear();
        board.init(enemy, treasure, wall);

        if ((enemy + treasure + wall + 2) > (board.getHeight()) * board.getWidth()) {
            endGame();
        } else {
        notifyObservers();}
    }

    @Override
    public void endGame() {
        status = STATUS.END_GAME;
        if (curScore > highScore) {
            highScore = curScore;
        }
        labelScore = curScore;
        curScore = 0;
        notifyObservers();
    }

    public int getlabelScore() {
        return labelScore;
    }

    @Override
    public void moveUp() {
        CollisionResult result = board.moveHero(-1, 0);
        handleCollision(result);
        notifyObservers();
    }

    @Override
    public void moveDown() {
        CollisionResult result = board.moveHero(1, 0);
        handleCollision(result);
        notifyObservers();
    }

    @Override
    public void moveLeft() {
        CollisionResult result = board.moveHero(0, -1);
        handleCollision(result);
        notifyObservers();
    }

    @Override
    public void moveRight() {
        CollisionResult result = board.moveHero(0, 1);
        handleCollision(result);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        observers.add(o);
    }

    private void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    private void handleCollision(CollisionResult result) {
        if (result.getResults() == CollisionResult.Result.CONTINUE) {
            curScore = result.getPoints();
        } else if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
            level++;
            int enemy = level + 1;
            int treasure = level + 2;
            int wall = 4;
            board.init(enemy, treasure, wall);
        } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
            curScore = result.getPoints();
            endGame();
        }
    }
}
