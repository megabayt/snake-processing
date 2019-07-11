import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;

public class Snake {
    private ArrayList<Cell> body;
    private int cellW;
    private int vx, vy = 0;
    private boolean init = false;

    Snake(int x, int y, int cellW, int len) {
        this.cellW = cellW;
        body = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            body.add(new Cell(x, y, cellW));
        }
    }

    void setSpeed(int x, int y) {
        vy = y < 0 ? -cellW : y > 0 ? cellW : 0;
        vx = x < 0 ? -cellW : x > 0 ? cellW : 0;
    }

    void move(int w, int h) {
        if (vx == 0 && vy == 0) {
            return;
        }
        ArrayList<Cell> bodyClone = cloneBody();
        body.get(0).move(vx, vy, w, h);
        for (int i = 1; i < body.size(); i++) {
            int x = bodyClone.get(i - 1).getX();
            int y = bodyClone.get(i - 1).getY();
            body.get(i).move(x, y);
        }
        if (!init) {
            checkInit();
        }
    }

    void checkInit() {
        boolean init = true;
        for (int i = 1; i < body.size(); i++) {
            int x1 = body.get(i - 1).getX();
            int y1 = body.get(i - 1).getY();
            int x2 = body.get(i).getX();
            int y2 = body.get(i).getY();
            if (x1 == x2 && y1 == y2) {
                init = false;
            }
        }
        this.init = init;
    }

    void grow() {
        int lastX = body.get(body.size() - 1).getX();
        int lastY = body.get(body.size() - 1).getY();
        body.add(new Cell(lastX, lastY, cellW));
    }

    boolean checkFail() {
        if (!init) {
            return false;
        }
        boolean fail = false;
        for (int i = 0; i < body.size() && !fail; i++) {
            for (int j = 0; j < body.size() && !fail; j++) {
                fail = i != j && body.get(i).intersects(body.get(j));

            }
        }
        return fail;
    }

    boolean tryEat(Food food) {
        if (food == null) {
            return false;
        }
        if (body.get(0).intersects(food)) {
            grow();
            return true;
        }
        return false;
    }

    ArrayList<Cell> cloneBody() {
        Iterator<Cell> iterator = body.iterator();
        ArrayList<Cell> clone = new ArrayList<>();

        while(iterator.hasNext())
        {
            clone.add((Cell) iterator.next().clone());
        }
        return clone;
    }

    void display(PApplet context) {
        for (int i = 0; i < body.size(); i++) {
            body.get(i).display(context);
        }
    }
}
