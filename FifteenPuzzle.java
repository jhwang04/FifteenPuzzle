import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.GameController;
import src.GameState;

public class FifteenPuzzle extends Application {

    final Color PURPLE = Color.rgb(106, 13, 173);
    final Color LIGHT_BLUE = Color.rgb(110, 220, 220);

    Rectangle[][] rectangles = new Rectangle[4][4];
    Label[][] labels = new Label[4][4];
    Label timeLabel = new Label("Last time:");
    Label movesLabel = new Label("Moves:");
    Button startButton = new Button("Reset");

    GameController controller = new GameController();

    int[][] game;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        /* Initialize game */

        resetGame();

        /* Initialize labels */
        timeLabel.setFont(new Font("Comic Sans MS", 20));
        timeLabel.setTextFill(Color.WHITE);
        movesLabel.setFont(new Font("Comic Sans MS", 20));
        movesLabel.setTextFill(Color.WHITE);
        startButton.setFont(new Font("Comic Sans MS", 30));
        startButton.setTextFill(PURPLE);
        startButton.setFocusTraversable(false);
        startButton.setStyle("-fx-background-color: #6edcdc; -fx-border-color: ffffff; -fx-border-width: 3; -fx-background-radius: 10; -fx-border-radius: 10");
        startButton.setOnAction(e -> {
            controller.resetGame();
            updateGridPane();
        });
        StackPane stackPane = new StackPane();
        VBox vbox = new VBox();
        GridPane gridPane = new GridPane();
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                StackPane stack = new StackPane();

                Rectangle backgroundRectangle = new Rectangle(0, 0, 100, 100);
                backgroundRectangle.setFill(PURPLE);
                Rectangle rectangle = new Rectangle(10, 10, 80, 80);
                rectangle.setArcHeight(10.0);
                rectangle.setArcWidth(10.0);
                rectangles[r][c] = rectangle;

                Label label = new Label("" + game[r][c]);
                label.setTextFill(Color.WHITE);
                label.setFont(new Font("Comic Sans MS", 30));
                labels[r][c] = label;

                stack.getChildren().add(backgroundRectangle);
                stack.getChildren().add(rectangle);
                stack.getChildren().add(label);
                gridPane.add(stack, c, r);
            }
        }
        vbox.setMaxWidth(gridPane.getWidth() - 100);
        gridPane.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(0, 0, 10000, 10000);
        background.setFill(PURPLE);

        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(timeLabel);
        Region spacing = new Region();
        labelBox.getChildren().add(spacing);
        labelBox.getChildren().add(movesLabel);
        labelBox.setHgrow(spacing, Priority.ALWAYS);

        vbox.getChildren().add(labelBox);
        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(startButton);

        vbox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(background);
        stackPane.getChildren().add(vbox);

        /* Add key handlers */
        Scene scene = new Scene(stackPane, 800, 800);
        scene.setOnKeyPressed(e -> {
            if (controller.getGameState() == GameState.WAITING) {
                controller.startTimer();
            } else if (controller.getGameState() == GameState.COMPLETE) {
                return;
            }

            controller.enterKey(e.getCode());

            updateGridPane();
            timeLabel.setText("Last time: " + (int) (controller.getLastTimeMillis()) / 1000.0);
            movesLabel.setText("Moves: " + controller.getNumMoves());
        });

        updateGridPane();

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void updateGridPane() {
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                String text = game[r][c] == 0 ? "" : "" + game[r][c];
                labels[r][c].setText(text);
                if (text.isBlank()) {
                    rectangles[r][c].setFill(PURPLE);
                } else if (game[r][c] == 1 + 4 * r + c) {
                    rectangles[r][c].setFill(Color.ORANGE);
                } else {
                    rectangles[r][c].setFill(LIGHT_BLUE);
                }
            }
        }
    }

    private void resetGame() {
        controller.resetGame();
        game = controller.getGame();
    }
}

