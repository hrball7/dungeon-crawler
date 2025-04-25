package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View implements FXComponent, Observer {
  private ControllerImpl controller;
  private ModelImpl model;
  private int width;
  private int height;
  private Stage stage;

  public View(ControllerImpl controller, ModelImpl model, int width, int height, Stage stage){
    this.controller = controller;
    this.model = model;
    this.width = width;
    this.height = height;
  }


  public Parent render() {
    Pane s = new StackPane();
    s.getChildren().add(new Label("Hello, World"));
    return s;
  }

  @Override
  public void update() {
    Scene scene = new Scene(render(), width, height);
    scene.getStylesheets().add("dungeon.css");
    stage.setScene(scene);
  }
}
