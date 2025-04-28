package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.ModelImpl;
import com.comp301.a08dungeon.model.Observer;
import com.comp301.a08dungeon.model.board.BoardImpl;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        //BorderPane root = new BorderPane();

        GridPane board = new GridPane();
        board.setGridLinesVisible(true);
        board.setPrefSize(500,500);
        board.setAlignment(Pos.CENTER);
        board.getStyleClass().add("grid-pane");
        int height = model.getHeight();
        int width = model.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Piece piece = model.get(new Posn(row, col));

                if (piece != null) {
                    String path = piece.getResourcePath();
                    Image img = new Image(new File(path).toURI().toString());
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(70);
                    imgView.setFitHeight(70);
                    board.add(imgView, col, row);
                } else {
                    Rectangle rectangle = new Rectangle(70, 70);
                    rectangle.setFill(Color.TRANSPARENT);
                    board.add(rectangle, col, row);
                }
            }
        }
//        board.setHgap(0);
//        board.setVgap(0);
//        root.setCenter(board);

        Label scoreLabel = new Label("Score: " + model.getCurScore());
        scoreLabel.getStyleClass().add("score-high");

        Button up = new Button("↑");
        Button down = new Button("↓");
        Button left = new Button("←");
        Button right = new Button("→");

//        up.setOnAction(e -> Platform.runLater(() -> controller.moveUp()));
//        down.setOnAction(e -> controller.moveDown());
//        left.setOnAction(e -> controller.moveLeft());
//        right.setOnAction(e -> controller.moveRight());

        up.setOnAction(e -> Platform.runLater(() -> controller.moveUp()));
        down.setOnAction(e -> Platform.runLater(() -> controller.moveDown()));
        left.setOnAction(e -> Platform.runLater(() -> controller.moveLeft()));
        right.setOnAction(e -> Platform.runLater(() -> controller.moveRight()));


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
