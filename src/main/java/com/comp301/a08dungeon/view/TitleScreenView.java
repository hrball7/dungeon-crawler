package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.Observer;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TitleScreenView implements FXComponent {
    private final ControllerImpl controller;
    private final ModelImpl model;


    public TitleScreenView(ModelImpl model, ControllerImpl controller) {
        this.controller = controller;
        this.model = model;
    }
    @Override
    public Parent render() {
        Label title = new Label("Helaina's Dungeon Crawler");
        title.getStyleClass().add("title");

        Label lastScore = new Label("Last Score: " + model.getCurScore());
        Label highScore = new Label("High Score: " + model.getHighScore());

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startGame());

        VBox vbox = new VBox(20, title, lastScore, highScore, startButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        root.setPrefSize(800, 600);
        root.getStyleClass().add("title-screen");

        return root;
    }
}
