import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private VictoryPanel victoryPanel;

    public Main() {
        setTitle("A Última Relíquia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        showMenu();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMenu() {
        getContentPane().removeAll();
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void showStory() {
        remove(getContentPane().getComponent(0));
        StoryPanel storyPanel = new StoryPanel(this);
        add(storyPanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void showControls() {
        remove(getContentPane().getComponent(0));
        ControlsPanel controlsPanel = new ControlsPanel(this);
        add(controlsPanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void showMap() {
        MapPanel mapPanel = new MapPanel(this);
        setContentPane(mapPanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void startGame() {
        getContentPane().removeAll();
        gamePanel = new GamePanel();
        setContentPane(gamePanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
            gamePanel.startGame();
        });
    }

    public void showVictory() {
        victoryPanel = new VictoryPanel(this);
        setContentPane(victoryPanel);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}