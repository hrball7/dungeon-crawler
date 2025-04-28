package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    private final int width = 6;
    private final int height = 6;

  @Override
  public void start(Stage stage) {

      ModelImpl model = new ModelImpl(width, height);
      ControllerImpl pc = new ControllerImpl(model);
      View view = new View(pc, model, 800, 800, stage);
      model.addObserver(view);

      int sceneWidth = 600;
      int sceneHeight = 600;

      //Scene scene = new Scene(view.render(), sceneWidth, sceneHeight);
      //scene.getStylesheets().add("dungeon.css");

      //stage.setScene(scene);
      stage.setTitle("Dungeon Crawler");
      stage.show();
  }
}
