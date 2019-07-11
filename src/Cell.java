import processing.core.PApplet;

public class Cell implements Cloneable {
    private int x, y, w;

    Cell(int x, int y, int w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    void move(int vx, int vy, int fieldW, int fieldH) {
        if (x + vx < 0) {
            x = fieldW - w / 2;
        } else if (y + vy < 0) {
            y = fieldH - w / 2;
        } else if (x + vx > fieldW) {
            x = w / 2;
        } else if (y + vy > fieldH) {
            y = w / 2;
        } else {
            x += vx;
            y += vy;
        }
    }
    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void display(PApplet context) {
        float halfW = w / 2;
        context.rect(x - halfW, y - halfW, w, w);
    }

    boolean intersects(Cell cell) {
        return this.x == cell.getX()
            && this.y == cell.getY();
    }

    @Override
    protected Object clone() {
        Cell cell = new Cell(this.x, this.y, this.w);
        return cell;
    }
}
