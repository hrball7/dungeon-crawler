package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.Observer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TitleScreenView implements FXComponent {
  private final ControllerImpl controller;
  private final ModelImpl model;

  public TitleScreenView(ModelImpl model, ControllerImpl controller) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    Label title = new Label("Dungeon Crawler");
    title.getStyleClass().add("title");

    Label last = new Label("Last Score: " + model.getlabelScore());
    last.getStyleClass().add("score-last");

    Label high = new Label("High Score: " + model.getHighScore());
    high.getStyleClass().add("score-high");

    Label label = new Label("Created by Helaina Ball");

    Text text = new Text("Created by Helaina Ball");
    text.getStyleClass().add("text-info");

    Button startButton = new Button("Start Game");
    startButton.setOnAction(e -> Platform.runLater(() -> controller.startGame()));

    VBox vbox = new VBox(20, title, last, high, startButton, label);
    vbox.setAlignment(Pos.CENTER);

    StackPane root = new StackPane(vbox);
    root.setPrefSize(500, 500);
    root.getStyleClass().add("title-screen");

    return root;
  }
}
