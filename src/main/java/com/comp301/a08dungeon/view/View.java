package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.Model;
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

  private Scene scene;
  private StackPane root;


  public View(ControllerImpl controller, ModelImpl model, int width, int height, Stage stage){
    this.controller = controller;
    this.model = model;
    this.width = width;
    this.height = height;
    this.stage = stage;

    root = new StackPane();
    scene = new Scene(root, width, height);
    scene.getStylesheets().add("dungeon.css");
    stage.setScene(scene);

    update();

  }


  public Parent render() {

    if (model.getStatus() == Model.STATUS.END_GAME) {
      return new TitleScreenView(model, controller).render();
    } else {
      return new GameView(model, controller).render();
    }
  }

  @Override
  public void update() {
    root.getChildren().clear();
    root.getChildren().add(render());
//    Scene scene = new Scene(render(), width, height);
//    scene.getStylesheets().add("dungeon.css");
//    stage.setScene(scene);
  }

}
