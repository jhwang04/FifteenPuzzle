import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class FifteenPuzzle extends Application {

    final Color PURPLE = Color.rgb(106, 13, 173);
    final Color LIGHT_BLUE = Color.rgb(110, 220, 220);

    int[][] game = new int[4][4];
    Rectangle[][] rectangles = new Rectangle[4][4];
    Label[][] labels = new Label[4][4];
    long startMillis = 0;
    Label timeLabel = new Label("Last time: ");
    Button startButton = new Button("Reset");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        /* Initialize game */

        resetGame();

        /* Initialize labels */
        timeLabel.setFont(new Font("Comic Sans MS", 30));
        timeLabel.setTextFill(Color.WHITE);
        startButton.setFont(new Font("Comic Sans MS", 30));
        startButton.setTextFill(PURPLE);
        startButton.setStyle("-fx-background-color: #6edcdc; -fx-border-color: ffffff; -fx-border-width: 3");
        startButton.setOnAction(e -> {
                resetGame();
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
        gridPane.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(0, 0, 10000, 10000);
        background.setFill(PURPLE);
        //vbox.getChildren().add(startButton);
        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(timeLabel);
        vbox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(background);
        stackPane.getChildren().add(vbox);

        /* Add key handlers */
        Scene scene = new Scene(stackPane, 800, 800);
        scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                case UP: moveUp(); break;
                case DOWN: moveDown(); break;
                case LEFT: moveLeft(); break;
                case RIGHT: moveRight(); break;
                }
                updateGridPane();
                if (checkIfWin()) {
                    long time = System.currentTimeMillis() - startMillis;
                    timeLabel.setText("Last time: " + (int) (time) / 1000.0);
                }
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

    private void moveUp() {
        for (int r = 0; r < 3; ++r) { // can't move up if the space is at bottom
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r + 1][c];
                    game[r + 1][c] = temp;
                    return;
                }
            }
        }
    }

    private void moveDown() {
        for (int r = 1; r < 4; ++r) { // can't move down if the space is at top
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r - 1][c];
                    game[r - 1][c] = temp;
                    return;
                }
            }
        }
    }

    private void moveRight() {
        for (int r = 0; r < 4; ++r) { // can't move right if the space is at left
            for (int c = 1; c < 4; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r][c - 1];
                    game[r][c - 1] = temp;
                    return;
                }
            }
        }
    }

    private void moveLeft() {
        for (int r = 0; r < 4; ++r) { // can't move left if space is at right
            for (int c = 0; c < 3; ++c) {
                if (game[r][c] == 0) {
                    int temp = game[r][c];
                    game[r][c] = game[r][c + 1];
                    game[r][c + 1] = temp;
                    return;
                }
            }
        }
    }

    private boolean checkIfWin() {
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                if (game[r][c] != 4 * r + c + 1 && !(r == 3 && c == 3)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        startMillis = System.currentTimeMillis();
        
        // Creates a solved board
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                game[r][c] = r * 4 + c + 1;
            }
        }
        game[3][3] = 0;

        // Scrambles the board. Only 81 are necessary, but some "moves" don't do anything because they're at the wrong edge.
        for (int i = 0; i < 500; ++i) {
            int rand = (int) (Math.random() * 4);
            if (rand == 0) {
                moveUp();
            } else if (rand == 1) {
                moveDown();
            } else if (rand == 2) {
                moveRight();
            } else {
                moveLeft();
            }
        }
    }
    
}