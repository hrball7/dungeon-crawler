package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.Observer;
import com.comp301.a08dungeon.model.board.BoardImpl;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

public class GameView implements FXComponent {
    private final ControllerImpl controller;
    private final ModelImpl model;

    public GameView(ModelImpl model, ControllerImpl controller) {
        this.controller = controller;
        this.model = model;

    }
    @Override
    public Parent render() {
        GridPane board = new GridPane();
        board.setGridLinesVisible(true);
        int height = model.getHeight();
        int width = model.getWidth();

//        BackgroundImage backgroundImage = new BackgroundImage(
//                new Image(getClass().getResource("src/main/resources/Background.png").toExternalForm(), width * 40, height * 40, true, true),
//        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Posn pos = new Posn(row, col);
                Piece piece = model.get(pos);

                if (piece != null) {
                    Image img = new Image(new File(piece.getResourcePath()).toURI().toString());
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(100);
                    imgView.setFitHeight(100);
                    board.add(imgView, col, row);
                }
                board.setAlignment(Pos.CENTER);
            }
        }

        Label scoreLabel = new Label("Score: " + model.getCurScore());

        Button up = new Button("↑");
        Button down = new Button("↓");
        Button left = new Button("←");
        Button right = new Button("→");

        up.setOnAction(e -> controller.moveUp());
        down.setOnAction(e -> controller.moveDown());
        left.setOnAction(e -> controller.moveLeft());
        right.setOnAction(e -> controller.moveRight());

        VBox vertical = new VBox(up, new HBox(left, right), down);
        vertical.setAlignment(Pos.CENTER);
        vertical.setSpacing(5);

        HBox controlRow = new HBox(vertical);
        controlRow.setAlignment(Pos.CENTER);
        controlRow.setSpacing(10);

        VBox root = new VBox(10, board, scoreLabel, controlRow);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("game-view");

        return root;
    }
}
