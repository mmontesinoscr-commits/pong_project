package mx.unison.pong;

import java.awt.geom.Ellipse2D;

public class BallRunner {
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;


    private final Ellipse2D.Double ball;
    private final Paddle paddle;

    // Posición y velocidad representadas mediante valores numéricos precisos (double)
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double ballDiameter;

    private GameState state;
    private int score;

    public BallRunner(Ellipse2D.Double shape, Paddle p) {
        this.ball = shape;
        this.paddle = p;
        this.ballDiameter = ball.width;

        // Velocidades horizontal y vertical separadas
        this.velocityX = 4.0;
        this.velocityY = 4.0;

        resetBall();
        this.state = GameState.STOPPED;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public void resetBall() {
        this.x = DEFAULT_WIDTH / 2.0 - ballDiameter / 2.0;
        this.y = DEFAULT_HEIGHT / 2.0 - ballDiameter / 2.0;
        syncBallShape();
    }

    private void syncBallShape() {
        ball.x = x;
        ball.y = y;
    }

    public void update(int panelWidth, int panelHeight) {
        if (state != GameState.RUNNING) {
            return;
        }

        // Aplicar velocidad al siguiente fotograma
        x += velocityX;
        y += velocityY;

        if (x <= 0) {
            x = 0;
            velocityX = Math.abs(velocityX); // Asegura dirección positiva, evitando inversiones múltiples
        } else if (x + ballDiameter >= panelWidth) {
            x = panelWidth - ballDiameter;
            velocityX = -Math.abs(velocityX); // Asegura dirección negativa
        }
// 2. Colisión con el límite superior
        if (y <= 0) {
            y = 0;
            velocityY = Math.abs(velocityY);
        }

        // Colisión con la raqueta (considerando dimensiones reales y evitando que la atraviese)
        if (paddle.intersects(ball)) {
            // Reposicionar la pelota justo encima de la paleta para evitar que se atore o la atraviese
            y = paddle.getPaddle().getY() - ballDiameter;
            velocityY = -Math.abs(velocityY); // Invierte la velocidad hacia arriba garantizando el rebote
            score++;
        }


        if (y + ballDiameter >= panelHeight) {

            resetBall();
            velocityY = -Math.abs(velocityY);
        }

        syncBallShape();
    }
}