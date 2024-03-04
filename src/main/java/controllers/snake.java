package controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class snake extends Application {
    static int speed = 5;
    static int foodColor = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornerSize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.LEFT;
    static boolean gameOver = false;
    static Random rand = new Random();

    public enum Dir {
        LEFT, RIGHT, UP, DOWN
    }

    public static class Corner {
        int x;
        int y;

        public Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = new VBox();
            Canvas c = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (gameOver) {
                        gc.setFill(Color.RED);
                        gc.setFont(new Font("", 50));
                        gc.fillText("GAME OVER", 100, 250);
                        return;
                    }

                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * cornerSize, height * cornerSize);

            // control
            scene.setOnKeyPressed(e -> {
                if (!gameOver) {
                    switch (e.getCode()) {
                        case UP:
                            if (direction != Dir.DOWN) direction = Dir.UP;
                            break;
                        case DOWN:
                            if (direction != Dir.UP) direction = Dir.DOWN;
                            break;
                        case LEFT:
                            if (direction != Dir.RIGHT) direction = Dir.LEFT;
                            break;
                        case RIGHT:
                            if (direction != Dir.LEFT) direction = Dir.RIGHT;
                            break;
                    }
                } else {
                    if (e.getCode() == KeyCode.ENTER) {
                        restartGame();
                    }
                }
            });

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));

            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tick
    public static void tick(GraphicsContext gc) {
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
            case UP:
                snake.get(0).y--;
                break;
            case DOWN:
                snake.get(0).y++;
                break;
            case LEFT:
                snake.get(0).x--;
                break;
            case RIGHT:
                snake.get(0).x++;
                break;
        }

        // check if game over
        if (isGameOver()) {
            gameOver = true;
            return;
        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                break;
            }
        }

        // fill background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornerSize, height * cornerSize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (speed - 6), 10, 30);

        // random food color
        Color cc = Color.WHITE;
        switch (foodColor) {
            case 0:
                cc = Color.PURPLE;
                break;
            case 1:
                cc = Color.LIGHTBLUE;
                break;
            case 2:
                cc = Color.YELLOW;
                break;
            case 3:
                cc = Color.PINK;
                break;
            case 4:
                cc = Color.ORANGE;
                break;
        }
        gc.setFill(cc);
        gc.fillOval(foodX * cornerSize, foodY * cornerSize, cornerSize, cornerSize);

        // snake
        for (Corner c : snake) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(c.x * cornerSize, c.y * cornerSize, cornerSize - 1, cornerSize - 1);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.x * cornerSize, c.y * cornerSize, cornerSize - 2, cornerSize - 2);
        }
    }

    // check if game over
    public static boolean isGameOver() {
        return snake.get(0).x < 0 || snake.get(0).x >= width ||
                snake.get(0).y < 0 || snake.get(0).y >= height;
    }

    // food
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodColor = rand.nextInt(5);
            speed++;
            break;
        }
    }

    // restart game
    public static void restartGame() {
        snake.clear();
        direction = Dir.LEFT;
        gameOver = false;
        speed = 5;
        newFood();
        snake.add(new Corner(width / 2, height / 2));
        snake.add(new Corner(width / 2, height / 2));
        snake.add(new Corner(width / 2, height / 2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
