import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public final class GamePanel extends JPanel implements ActionListener{
    static final int g_width = 600;
    static final int g_height = 600;
    static final int screen_unit = 25;
    static final int g_units = (g_width * g_height) / screen_unit;      //For appropriate sizing
    static int delay = 150; //Higher the delay slower the game
    final int x[] = new int[g_units];
    final int y[] = new int[g_units];
    int bodyParts = 5;
    int applesEaten;
    int appleX;
    int appleY;
    char s_direction = 'U';
    char restart = 'N';
    boolean running = false;
    Timer timer;
    Random random;
    
    GamePanel(){        //Constructor
        random = new Random();
        this.setPreferredSize(new Dimension(g_width, g_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();

        x[0] = 100;
        y[0] = 100;
    }
    public void start(){
        addApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        
        // for(int i=0 ; i < (g_height/g_units) ; i++){
        //     g.drawLine(i*g_units, 0, i*g_units, g_height);
        //     g.drawLine(0, i*g_units, g_width, i*g_units);
        // }
        if(running){
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, screen_unit, screen_unit);

            for (int i=0; i<bodyParts;i++){
                if (i==0){
                   g.setColor(Color.green);
                    switch (s_direction) {
                        case 'U':
                            g.fillArc(x[i], y[i], screen_unit, screen_unit, 135, 270);
                            break;
                        case 'D':
                            g.fillArc(x[i], y[i], screen_unit, screen_unit, 315, 270);
                            break;
                        case 'L':
                            g.fillArc(x[i], y[i], screen_unit, screen_unit, 225, 270);
                            break;
                        case 'R':
                            g.fillArc(x[i], y[i], screen_unit, screen_unit, 45, 270);
                            break;
                    }
                }
                else{
                    g.setColor(new Color(0,199,9));
                    g.fillOval(x[i], y[i], screen_unit, screen_unit);
                }
            }

            score(g);
        }
        else{
            gameOver(g);
        }
    }
    public void addApple(){
        appleX = random.nextInt((int)(g_width/screen_unit))*screen_unit;
        appleY = random.nextInt((int)(g_height/screen_unit))*screen_unit;
    }
    public void move(){
        for (int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (s_direction){
            case 'U':
			y[0] = y[0] - screen_unit;
			break;
		    case 'D':
			y[0] = y[0] + screen_unit;
			break;
		    case 'L':
			x[0] = x[0] - screen_unit;
			break;
		    case 'R':
			x[0] = x[0] + screen_unit;
			break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            addApple();
            delay -= 5;
        }
    }
    public void checkCollision(){
        for(int i=bodyParts; i>0; i--){
            if ((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        if (x[0]<0){
            running = false;
        }
        if (x[0]>=g_width){
            running = false;
        }
        if (y[0]<0){
            running = false;
        }
        if(y[0]>=g_height){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(g_width/4, g_height/3, g_width/2, g_height/3);
        System.out.println(g_width/4 + " " + g_height/3);
        score(g);
    }
    public void score(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics metrics = g.getFontMetrics(getFont());
        if (running){
            g.setColor(Color.yellow);
            g.drawString("Score: "+(bodyParts-5), (g_width - (metrics.stringWidth("Score: "+(bodyParts-5)))/2)/2, screen_unit);
        }
        else{
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.ITALIC, 40));
            g.drawString("Game Over", g_width/3, g_height/2);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Score: "+(bodyParts-5), (g_width - metrics.stringWidth("Score: "+(bodyParts-5)))/2-25, 350);
            // System.out.println((g_width - (metrics.stringWidth("Score: "+(bodyParts-5)))/2)/2 +" "+ metrics.stringWidth("Score: "+(bodyParts-5)));
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if (s_direction != 'R'){
                    s_direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:
                if(s_direction != 'L'){
                    s_direction ='R';
                }
                break;
                case KeyEvent.VK_UP:
                if(s_direction != 'D'){
                    s_direction = 'U';
                }
                break;
                case KeyEvent.VK_DOWN:
                if(s_direction != 'U'){
                    s_direction = 'D';
                }
                break;
            }
        }
    }
}
