package PingPong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;


public class Game extends JFrame {

    private KeyListener keyListener;
    private Board board;
    private HashSet<String> keys;

    private JLabel timeCounter = new JLabel("Time", SwingUtilities.CENTER);


    private void startGame(){

        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                int code = e.getKeyCode();

                switch (code){
                    case KeyEvent.VK_LEFT -> keys.add("LEFT");
                    case KeyEvent.VK_RIGHT -> keys.add("RIGHT");
                    case KeyEvent.VK_UP -> keys.add("UP");
                    case KeyEvent.VK_DOWN -> keys.add("DOWN");
                    case KeyEvent.VK_SPACE -> keys.add("SPACE");


                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int code = e.getKeyCode();

                switch (code){

                    case KeyEvent.VK_LEFT -> keys.remove("LEFT");
                    case KeyEvent.VK_RIGHT -> keys.remove("RIGHT");
                    case KeyEvent.VK_UP -> keys.remove("UP");
                    case KeyEvent.VK_DOWN -> keys.remove("DOWN");
                    case KeyEvent.VK_SPACE -> keys.remove("SPACE");

                }
            }

        };

        board = new Board(keyListener, 5);

        keys = board.getKeys();


        add(board, BorderLayout.CENTER);
        setTitle("Delay: " + board.getDelay());


    }

    public Game(){

        setTitle("Game");

        startGame();

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}