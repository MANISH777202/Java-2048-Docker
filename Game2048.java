import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Game2048 extends JFrame {
    private static final int SIZE = 4;
    private int[][] board = new int[SIZE][SIZE];
    private JPanel panel;
    private JLabel[][] labels;
    private boolean isGameOver = false;

    // Constructor for setting up the game UI
    public Game2048() {
        this.setTitle("2048 Game");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(SIZE, SIZE, 5, 5));
        labels = new JLabel[SIZE][SIZE];
        
        // Initialize labels for grid cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labels[i][j] = new JLabel("", SwingConstants.CENTER);
                labels[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground(new Color(205, 193, 180)); // light brown
                labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(labels[i][j]);
            }
        }

        this.add(panel);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGameOver) return;
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) move("W");
                if (keyCode == KeyEvent.VK_A) move("A");
                if (keyCode == KeyEvent.VK_S) move("S");
                if (keyCode == KeyEvent.VK_D) move("D");
            }
        });
        this.setFocusable(true);
        this.setVisible(true);
        
        initialize();
    }

    // Initialize the game by adding two random tiles
    private void initialize() {
        addRandomTile();
        addRandomTile();
        updateBoard();
    }

    // Add a random tile (2 or 4) at an empty position
    private void addRandomTile() {
        ArrayList<int[]> emptyTiles = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyTiles.add(new int[]{i, j});
                }
            }
        }

        if (!emptyTiles.isEmpty()) {
            Random rand = new Random();
            int[] tile = emptyTiles.get(rand.nextInt(emptyTiles.size()));
            board[tile[0]][tile[1]] = (rand.nextInt(2) + 1) * 2;
        }
    }

    // Update the UI board
    private void updateBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int value = board[i][j];
                labels[i][j].setText(value == 0 ? "" : String.valueOf(value));
                // Color the labels based on the value of the tile
                if (value == 0) {
                    labels[i][j].setBackground(new Color(205, 193, 180));
                } else {
                    labels[i][j].setBackground(getTileColor(value));
                }
            }
        }
        this.repaint();
    }

    // Return color based on the tile value
    private Color getTileColor(int value) {
        switch (value) {
            case 2: return new Color(238, 228, 218);
            case 4: return new Color(237, 224, 200);
            case 8: return new Color(242, 177, 121);
            case 16: return new Color(245, 149, 99);
            case 32: return new Color(246, 124, 95);
            case 64: return new Color(246, 94, 59);
            case 128: return new Color(237, 207, 114);
            case 256: return new Color(237, 204, 97);
            case 512: return new Color(237, 200, 80);
            case 1024: return new Color(237, 197, 63);
            case 2048: return new Color(237, 194, 46);
            default: return new Color(204, 192, 179);
        }
    }

    // Move method based on the user input
    public void move(String direction) {
        switch (direction) {
            case "W": moveUp(); break;
            case "A": moveLeft(); break;
            case "S": moveDown(); break;
            case "D": moveRight(); break;
        }
        addRandomTile();
        updateBoard();
    }

    private void moveUp() {
        // Implement logic for moving up
        for (int col = 0; col < SIZE; col++) {
            int[] newCol = new int[SIZE];
            int insertPos = 0;
            for (int row = 0; row < SIZE; row++) {
                if (board[row][col] != 0) {
                    newCol[insertPos++] = board[row][col];
                }
            }
            mergeColumn(newCol);
            for (int row = 0; row < SIZE; row++) {
                board[row][col] = newCol[row];
            }
        }
    }

    private void moveDown() {
        // Implement logic for moving down
    }

    private void moveLeft() {
        for (int row = 0; row < SIZE; row++) {
            int[] newRow = new int[SIZE];
            int insertPos = 0;
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != 0) {
                    newRow[insertPos++] = board[row][col];
                }
            }
            mergeRowLeft(newRow);
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = newRow[col];
            }
        }
    }

    private void moveRight() {
        // Implement logic for moving right
    }

    // Merge row logic (for moving left)
    private void mergeRowLeft(int[] row) {
        for (int i = 0; i < SIZE - 1; i++) {
            if (row[i] == row[i + 1] && row[i] != 0) {
                row[i] *= 2;
                row[i + 1] = 0;
                i++; // Skip the next cell as it's already merged
            }
        }
    }

    private void mergeColumn(int[] col) {
        for (int i = 0; i < SIZE - 1; i++) {
            if (col[i] == col[i + 1] && col[i] != 0) {
                col[i] *= 2;
                col[i + 1] = 0;
                i++; // Skip the next cell as it's already merged
            }
        }
    }

    public static void main(String[] args) {
        new Game2048();
    }
}
