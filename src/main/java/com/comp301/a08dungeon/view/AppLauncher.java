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
      stage.setTitle("Helaina's Dungeon Crawler");

      ModelImpl model = new ModelImpl(width, height);
      ControllerImpl pc = new ControllerImpl(model);
      View view = new View(pc, model, width, height, stage);
      model.addObserver(view);
      Scene scene = new Scene(view.render(), width, height);
      scene.getStylesheets().add("dungeon.css");
      stage.setScene(scene);
      stage.show();
  }
}
