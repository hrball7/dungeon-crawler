package com.comp301.a08dungeon.controller;

import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.board.BoardImpl;

public class ControllerImpl implements Controller{
    private final ModelImpl model;

    public ControllerImpl(ModelImpl model){
        this.model = model;
    }

    @Override
    public void moveUp() {
        this.model.moveUp();
    }

    @Override
    public void moveDown() {
        this.model.moveDown();
    }

    @Override
    public void moveLeft() {
        this.model.moveLeft();
    }

    @Override
    public void moveRight() {
        this.model.moveRight();
    }

    @Override
    public void startGame() {
        this.model.startGame();
    }
}
