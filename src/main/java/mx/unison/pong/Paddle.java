package mx.unison.pong;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Paddle {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final int BOARD_WIDTH = 640;

    private final Rectangle2D.Double paddle;
    private double x;
    private final double y = 450;
    private final double speed = 15.0;

    public Paddle() {
        super();
        this.x = BOARD_WIDTH / 2.0 - WIDTH / 2.0;
        paddle = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
    }

    public synchronized void moveRight(int panelWidth) {
        x += speed;

        if (x + WIDTH > panelWidth) {
            x = panelWidth - WIDTH;
        }
        paddle.x = x;
    }

    public synchronized void moveLeft() {
        x -= speed;

        if (x < 0) {
            x = 0;
        }
        paddle.x = x;
    }
    public Rectangle2D.Double getPaddle() {
        return paddle;
    }

    public boolean intersects(RectangularShape shape) {
        return paddle.intersects(shape.getBounds2D());
    }
}