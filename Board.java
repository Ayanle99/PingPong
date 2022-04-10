package PingPong;


import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashSet;
import java.util.Random;

public class Board extends JPanel implements ActionListener {


    private Graphics2D g2d;
    private KeyListener keyListener;
    private HashSet<String> keys = new HashSet<>();

    private Ellipse2D ball;
    private int width, height;

    private int delay=5;
    private Timer timer = new Timer(delay, this);
    private Rectangle2D paddle;
    private int SPEED = 1;
    private int paddleWidth = 40, paddleHeight = 10;
    private int paddle_x, paddle_y;
    private int inset = 10;

    private double ballx,bally, belx = 1, velx =1, vely=1, ballSize = 20;


    private int score = 0;
    private boolean initiateGamer;


    private Random random = new Random();
    public HashSet<String> getKeys() {
        return keys;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private int timerCounter = 0;

    public int getDelay() {
        return delay;
    }

    public Board(){}


    public Timer getTimer() {
        return timer;
    }


    public Board(KeyListener keyListener, int delay){


        this.delay = delay;
        setBackground(Color.black);
        this.keyListener=keyListener;

        addKeyListener(keyListener);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        initiateGamer = true;
        timer.setInitialDelay(100);
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        width = getWidth();
        height = getHeight();


        if (initiateGamer){

            paddle_x = 160;
            paddle_y = 352;


            ballx = width/2-ballSize/2;
            bally = height/2-ballSize/2;
            initiateGamer=false;
        }

        makePaddle(paddle_x,paddle_y, paddleWidth,paddleHeight);
        makeBall(ballx,bally,ballSize,ballSize);

        getResult();


        timerCounter++;

        int seconds = (timerCounter / 60 ) % 60;
        int minutes = (timerCounter / 60 )/60;
        int hours = ((timerCounter/60)/60)/60;


        String timeTracker = hours + ":"+minutes+":"+seconds;

        try {

            String  filename = "src/PingPong/digital_7/digital-7.ttf";
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
            font = font.deriveFont(Font.BOLD,28);

            g2d.setFont(font);
            g2d.drawString(timeTracker, (getWidth()/2)-50, 40);
            g2d.setColor(Color.white);

        }catch (Exception e){
            System.out.println(e.toString());
        }



    }

    private void getResult() {

        String yourScore = "Score: "+String.valueOf(score);

        g2d.setColor(Color.yellow);
        g2d.setBackground(Color.cyan);
        g2d.drawString(yourScore, (getWidth()/2)-50, getHeight()/2);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        g2d.drawLine(0, (height/2)+10, width-1, (height/2)+10);


    }
    private void makeBall(double ballx, double bally, double ballSize, double ballSize1) {

        ball = new Ellipse2D.Double(ballx, bally, ballSize, ballSize);
        g2d.setColor(Color.cyan);
        g2d.setBackground(Color.green);
        g2d.fill(ball);


    }
    private void makePaddle(int paddle_x, int i, int paddleWidth, int paddleHeight) {

        paddle = new Rectangle(paddle_x, i, paddleWidth, paddleHeight);
        g2d.setColor(Color.yellow);
        g2d.fill(paddle);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        // side walls
        if (ballx < 0 || (ballx > (width-ballSize))){
            velx = -velx;
        }

        if (bally < 0){
            vely = -vely;
        }


        double ballPosition = bally + ballSize;

        if (ballPosition > height){
            vely = -vely;
            score++;
        }


        if (ball != null && paddle != null){

            int x = (int) ball.getX();
            int y = (int) ball.getY();
            int w = (int) ball.getWidth();
            int h = (int) ball.getHeight();

            Rectangle2D rect = new Rectangle(x,y,w,h);

            if (paddle.intersects(rect)){
                vely = -vely;
            }


        }

        if ((bally + ballSize) >= height - paddleHeight - inset && vely > 0){
            if (ballx + ballSize >= paddle_x && ballx <= paddle_x+paddleWidth){
                vely = -vely;
            }
        }

        ballx += velx;
        bally += vely;


        if (keys.size()==1){

            if (keys.contains("LEFT")){

                if (paddle_x > 0){
                    paddle_x -= SPEED;
                }
                //paddle_x -= (paddle_x > 0) ? SPEED : 0;

            }else if (keys.contains("RIGHT")){
                boolean moveRight = paddle_x < (width-paddleWidth);
                //paddle_x +=  (paddle_x < width-paddleWidth) ? SPEED : 0;
                paddle_x += moveRight ? SPEED : 0;

            }
            else if (keys.contains("UP")){
                paddle_y -= (paddle_y > 10) ? SPEED : 0;
            }
            else if (keys.contains("DOWN")){
                paddle_y += (paddle_y < (height-paddleHeight)  ? SPEED : 0);
            }

        }

        repaint();


    }
}