import processing.core.*;

public class SnakeApplet extends PApplet {
    private int w = 320, h = 320;
    private boolean gameStarted = false;
    private Snake snake;
    private Food food;
    private int score = 0;
    private int cellW = 10;

    private void gameOver() {
        snake.setSpeed(0, 0);
        gameStarted = false;
    }

    private void initGame() {
        int snakeLen = 4;
        snake = new Snake(w / 2 - cellW / 2,
                h / 2 - cellW / 2,
                cellW,
                snakeLen);
        food = null;
        score = 0;
        gameStarted = true;
    }

    public static void main(String args[]) {
        PApplet.main("SnakeApplet");
    }

    @Override
    public void setup() {
        surface.setSize(w, h);
        background(0);
        this.frameRate(10);
    }

    @Override
    public void keyPressed() {
        if (!gameStarted) {
            initGame();
        }
        if (keyCode == UP) {
            snake.setSpeed(0, -1);
        } else if (keyCode == RIGHT) {
            snake.setSpeed(1, 0);
        } else if (keyCode == DOWN) {
            snake.setSpeed(0, 1);
        } else if (keyCode == LEFT) {
            snake.setSpeed(-1, 0);
        }
    }

    @Override
    public void draw() {
        clear();
        if (gameStarted) {
            if (food == null) {
                int x = floor(random(0, w / cellW)) * cellW - cellW / 2;
                x = x < 0 ? cellW / 2 : x;
                int y = floor(random(0, h / cellW)) * cellW - cellW / 2;
                y = y < 0 ? cellW / 2 : y;
                food = new Food(x, y, cellW);
            }
            fill(255, 0, 0);
            food.display(this);
            fill(255);
            snake.display(this);
            snake.move(w, h);
            if (snake.checkFail()) {
                gameOver();
            }
            if (snake.tryEat(food)) {
                food = null;
                score++;
            }
        } else {
            textAlign(CENTER);
            if (score == 0) {
                text("Press any key", w / 2, h / 2);
            } else {
                text("Game Over\nYour score: " + score, w / 2, h / 2);
            }
        }
    }
}

