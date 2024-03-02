import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    private static final int BOARD_SIZE = 3;
    private static final int WINDOW_SIZE = 600;
    private static final int SCORE_BOARD_HEIGHT = 100;
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";
    private static final int CELL_SIZE = WINDOW_SIZE / BOARD_SIZE;

    private int playerXWins = 0;
    private int playerOWins = 0;
    private JLabel playerXLabel;
    private JLabel playerOLabel;

    private int[] winningRowCol;
    private JButton[][] buttons;
    private boolean gameEnded = false;
    private String currentPlayer;

    public App() {
        setTitle("Tic-Tac-Toe");
        setSize(WINDOW_SIZE, WINDOW_SIZE + SCORE_BOARD_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_X;

        initializeScoreboard();
        initializeBoard();
    }

    private void initializeScoreboard() {
        JPanel scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new GridLayout(2, 1));

        playerXLabel = new JLabel("Player X wins: " + playerXWins);
        playerXLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerXLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreboardPanel.add(playerXLabel);

        playerOLabel = new JLabel("Player O wins: " + playerOWins);
        playerOLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerOLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreboardPanel.add(playerOLabel);

        add(scoreboardPanel, BorderLayout.NORTH);
    }

    private void initializeBoard() {
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (winningRowCol != null) {
                    int x = winningRowCol[0] * CELL_SIZE;
                    int y = winningRowCol[1] * CELL_SIZE;
                    int width = CELL_SIZE * 3;
                    int height = CELL_SIZE;
                    g.setColor(Color.YELLOW);
                    g.fillRect(x, y, width, height);
                }
            }
        };
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 100));
                buttons[row][col].addActionListener(this);
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) {
            return;
        }
        JButton buttonClicked = (JButton) e.getSource();

        if (buttonClicked.getText().equals("")) {
            buttonClicked.setText(currentPlayer);

            if (checkForWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                if (currentPlayer.equals(PLAYER_X)) {
                    playerXWins++;
                } else {
                    playerOWins++;
                }
                updateScoreboard();
                resetBoard();
            } else if (checkForDraw()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer.equals(PLAYER_X)) ? PLAYER_O : PLAYER_X;
            }
        }
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRowCol(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText())
                    || checkRowCol(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText())) {
                winningRowCol = new int[]{i, 0, i, 2};
                return true;
            }
        }

        if (checkRowCol(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText())
                || checkRowCol(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText())) {
            winningRowCol = new int[]{0, 0, 2, 2};
            return true;
        }

        if (checkRowCol(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText())) {
            winningRowCol = new int[]{0, 2, 2, 0};
            return true;
        }

        return false;
    }

    private boolean checkRowCol(String s1, String s2, String s3) {
        return s1.equals(s2) && s2.equals(s3) && !s1.equals("");
    }

    private boolean checkForDraw() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = PLAYER_X;
        winningRowCol = null;
        gameEnded = false;
    }

    private void updateScoreboard() {
        playerXLabel.setText("Player X wins: " + playerXWins);
        playerOLabel.setText("Player O wins: " + playerOWins);
    }

    public static void main(String[] args) {
        new App();
    }
}
