 package mx.unison.pong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Ellipse2D;
import static org.junit.jupiter.api.Assertions.*;

    public class PongTest {

        private Ellipse2D.Double ball;
        private Paddle paddle;
        private BallRunner ballRunner;

        @BeforeEach
        public void setUp() {
            ball = new Ellipse2D.Double(320, 240, 20, 20);
            paddle = new Paddle();
            ballRunner = new BallRunner(ball, paddle);
        }

        @Test
        public void testInitialGameState() {
            assertEquals(GameState.STOPPED, ballRunner.getState(), "El juego debe iniciar en estado STOPPED.");
        }

        @Test
        public void testStateTransition() {
            ballRunner.setState(GameState.RUNNING);
            assertEquals(GameState.RUNNING, ballRunner.getState(), "El estado debe cambiar a RUNNING.");
        }

        @Test
        public void testPaddleBoundariesLeft() {
            for (int i = 0; i < 20; i++) {
                paddle.moveLeft();
            }
            assertTrue(paddle.getPaddle().getX() >= 0, "La raqueta no debe rebasar el límite izquierdo de 0.");
        }

        @Test
        public void testBallResetPosition() {
            ballRunner.resetBall();
            assertTrue(ball.getX() >= 0 && ball.getY() >= 0, "La pelota debe reposicionarse dentro del tablero.");
        }
    }

