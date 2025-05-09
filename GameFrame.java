import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame(){        //Constructor
        this.add(new GamePanel());      //New Instance is directly used
        this.setTitle("Snake Game");        //title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        //used to close or terminate the application from JVM
        this.setResizable(false);       //makes it non resizeable
        this.pack();     //will pack all the components in the frame
        this.setVisible(true);      //shows the whole component
        this.setLocationRelativeTo(null);       //will make the frame in the middle of the screen
    }
}
