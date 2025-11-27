import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class CollectorGame extends JPanel implements KeyListener, MouseListener{

    public int playerRow, playerCol;
    int score = 0;
    boolean gameWon = false;

    final int tile = 30;
    int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };

    public CollectorGame(int l, int h){
        setPreferredSize(new Dimension(l,h));
        setBackground(new Color(255,255,255));
        addMouseListener(this);
        addKeyListener(this);
        playerRow = 1;
        playerCol = 1;
        map[playerRow][playerCol] = 3;
        setFocusable(true);
    }

    public int remainingCoins(){
        int t = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++ ) {
                if (map[i][j] == 2) t++;
            }
        }
        return t;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++ ) {
                
                int x = j * tile;
                int y = i * tile;

                if (map[i][j] == 0) g.setColor(Color.WHITE); 
                else if (map[i][j] == 1) g.setColor(Color.BLACK); 
                else if (map[i][j] == 2) g.setColor(new Color(255,223,0));
                else if (map[i][j] == 3) g.setColor(new Color(137, 207, 240)); 

                g.fillRect(x, y, tile, tile);
                g.setColor(Color.GRAY);
                g.drawRect(x, y, tile, tile);
                
            }
        }
        g.setColor(Color.BLACK);
        String res = "Score: " + score;
        g.drawString(res,280,650);

        if (gameWon) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("YOU WIN!", 200, 300);
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e){
        int newRow = playerRow;
        int newCol = playerCol;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP : newRow--;
            break;
            case KeyEvent.VK_LEFT : newCol--;
            break;
            case KeyEvent.VK_DOWN : newRow++;
            break;
            case KeyEvent.VK_RIGHT : newCol++;
            break;
        }

        if (newRow < 0 || newRow >= map.length) return;
        if (newCol < 0 || newCol >= map[0].length) return;

        if (map[newRow][newCol] == 1) return;
        if (map[newRow][newCol] == 2) score++;

        map[playerRow][playerCol] = 0;
        playerRow = newRow;
        playerCol = newCol;
        map[playerRow][playerCol] = 3;

        if (remainingCoins() == 0 && !gameWon){
            gameWon = true;
        }
        
        this.repaint();
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / tile ;
        int y = e.getY() / tile ;
        if (map[y][x] == map[playerRow][playerCol] || map[y][x] == 2 ) return;
            switch (map[y][x]){
            case 0 : map[y][x] = 1;
            break;
            case 1 : map[y][x] = 0;
            break;
        }
        
        this.repaint();
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Collector Game");
        CollectorGame panel = new CollectorGame(600,700);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
