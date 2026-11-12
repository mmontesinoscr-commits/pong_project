package mx.unison.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

public class Board extends JComponent implements KeyListener {
    private Dimension preferredSize = null;
    private Ellipse2D.Double ball;
    private Paddle paddle;
    private BallRunner ballRunner;
    private Timer gameTimer;

    private final Font theFont = new Font("SansSerif", Font.BOLD, 24);

    public Board() {
        setOpaque(true);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));

        ball = new Ellipse2D.Double(320, 240, 20, 20);
        paddle = new Paddle();
        ballRunner = new BallRunner(ball, paddle);

        // Timer Swing de 16ms
        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballRunner.update(getWidth(), getHeight());
                repaint();
            }
        });

        ballRunner.setState(GameState.RUNNING);
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics graphicsContext) {
        super.paintComponent(graphicsContext);
        Graphics2D g = (Graphics2D) graphicsContext;

        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(getForeground());
        }

        if (ballRunner.getState() == GameState.PAUSED) {
            g.setFont(theFont);
            g.setColor(Color.BLUE);
            g.drawString("Paused", 20, 30);
        }

        g.setStroke(new BasicStroke(5.0f));
        g.setColor(Color.RED);
        g.fill(ball);
        g.setColor(Color.BLACK);
        g.fill(paddle.getPaddle());
    }

    @Override
    public Dimension getPreferredSize() {
        if (preferredSize == null) {
            return new Dimension(640, 480);
        } else {
            return super.getPreferredSize();
        }
    }

    @Override
    public void setPreferredSize(Dimension newPrefSize) {
        preferredSize = newPrefSize;
        super.setPreferredSize(newPrefSize);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (ballRunner.getState() == GameState.RUNNING) {
                    paddle.moveLeft();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (ballRunner.getState() == GameState.RUNNING) {
                    paddle.moveRight(getWidth());
                }
                break;
            case KeyEvent.VK_SPACE:
                ballRunner.setState(GameState.PAUSED);
                break;
            case KeyEvent.VK_R:
                ballRunner.setState(GameState.RUNNING);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        ballRunner.setState(GameState.STOPPED);
    }
}

