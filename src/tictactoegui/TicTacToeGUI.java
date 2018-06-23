package tictactoegui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TicTacToeGUI extends JFrame{
    
    protected JLabel currentTurn;
    
    protected final int ROWS = 3;
    protected final int COLLUMS = 3;
    
    protected char currentPlayer = 'X';
    protected char winner = '_';
    protected int roundNumber = 1;
    protected int gameNumber = 1;
    
    private JPanel gamePanel;
    
    protected char[][] board;
    protected JLabel[][] boardGUI;
    
    private ImageIcon xIcon;
    protected ImageIcon oIcon;
    
    protected LineBorder border = new LineBorder(Color.BLACK, 2);
    private LineBorder winBorder = new LineBorder(Color.red, 4);
        
    private JPanel winPanel;
    private JLabel xWinsLabel;
    private JLabel oWinsLabel;
    protected int xWins;
    protected int oWins;
    
    protected TicTacToeGUI(){
        
        super("Tic Tac Toe - Human vs. Human");
        
        winPanel = new JPanel(new BorderLayout());
        oWinsLabel = new JLabel("O's Wins: 0");
        xWinsLabel = new JLabel("X's Wins: 0");
        winPanel.add(xWinsLabel, BorderLayout.WEST);
        winPanel.add(oWinsLabel, BorderLayout.EAST);
        
        currentTurn = new JLabel("X's turn!");
        currentTurn.setHorizontalAlignment(JLabel.CENTER);
        currentTurn.setVerticalAlignment(JLabel.CENTER);
        currentTurn.setFont(new Font("Arial", Font.BOLD, 17));
        
        URL xURL = OpponentChooser.class.getResource("/resources/X.png");
        xIcon = new ImageIcon(xURL);
        URL oURL = OpponentChooser.class.getResource("/resources/O.png");
        oIcon = new ImageIcon(oURL);
        
        gamePanel = new JPanel(new GridLayout(3, 3));
        
        board = new char[][] {{'_','_','_'},{'_','_','_'},{'_','_','_'}};
        boardGUI = new JLabel[ROWS][COLLUMS];
        
        for(int i = 0; i < ROWS; i++){
            final int i2 = i; //<- final i2 is used instead of i, in updateBoard, becuase an error would occur otherwise
            for(int j = 0; j < COLLUMS; j++){
                final int j2 = j; //<- Same reason as why i2 is used
                boardGUI[i][j] = new JLabel();
                
                boardGUI[i][j].addMouseListener(
                        new MouseAdapter(){
                            @Override
                            public void mouseReleased(MouseEvent event){
                                if(roundNumber == 0){
                                    resetGame();
                                }
                                else if(board[i2][j2] == '_')
                                    if(currentPlayer == 'X')
                                        updateBoard(i2, j2, xIcon);
                                    else
                                        updateBoard(i2, j2, oIcon);
                            }
                        }
                );
                
                boardGUI[i][j].setHorizontalAlignment(JLabel.CENTER);
                boardGUI[i][j].setVerticalAlignment(JLabel.CENTER);
                boardGUI[i][j].setBorder(border);
                
                gamePanel.add(boardGUI[i][j]);
            }
        }
        super.add(currentTurn, BorderLayout.NORTH);
        super.add(gamePanel, BorderLayout.CENTER);
        super.add(winPanel, BorderLayout.SOUTH);
    }
    
    protected void resetGame(){
        winner = '_';
        gameNumber ++;
        roundNumber = 1;
        board = new char[][] {{'_','_','_'},{'_','_','_'},{'_','_','_'}};
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLLUMS; j++){
                boardGUI[i][j].setIcon(null);
                boardGUI[i][j].setBorder(border);
            }
        }
        currentPlayer = (gameNumber % 2 == 0)? 'O': 'X';
        currentTurn.setText(currentPlayer + "'s turn!");
    }
    
    protected void updateBoard(int row, int collum, ImageIcon icon){
        
        board[row][collum] = currentPlayer;
        boardGUI[row][collum].setIcon(icon);
        
        evaluateScores();
        
        if(winner == '_' && roundNumber < 9){
            if(currentPlayer == 'O')
                currentPlayer = 'X';
            else
                currentPlayer = 'O';

            currentTurn.setText(currentPlayer + "'s turn!");
            
            roundNumber++;
        }else{
            updateWins();
        }
    }
    
    protected void updateWins(){
        if(winner != '_'){
            currentTurn.setText("Winner is " + winner);

            if(winner == 'X')
                xWins++;
            else if(winner == 'O')
                oWins++;

            xWinsLabel.setText("X's Wins: " + xWins);
            oWinsLabel.setText("O's Wins: " + oWins);
        }else
            currentTurn.setText("Game is a tie!");
        roundNumber = 0;
    }
    
    protected void evaluateScores(){
        for(int row = 0; row < ROWS && winner == '_'; row++){ //Checks all horizontal lines for a winner
            if((board[row][0] == 'X' && board[row][1] == 'X' && board[row][2] == 'X')
                    || (board[row][0] == 'O' && board[row][1] == 'O' && board[row][2] == 'O')){
                //Mark the winning spots red
                for(int i = 0; i < COLLUMS; i++)
                    boardGUI[row][i].setBorder(winBorder);
                winner = currentPlayer;
            }
            for(int collum = 0; collum < COLLUMS && winner == '_'; collum++){ //Checks all vertical lines for a winner
                if((board[0][collum] == 'X' && board[1][collum] == 'X' && board[2][collum] == 'X')
                    || (board[0][collum] == 'O' && board[1][collum] == 'O' && board[2][collum] == 'O')){
                //Mark the winning spots red
                for(int i = 0; i < ROWS; i++)
                    boardGUI[i][collum].setBorder(winBorder);
                winner = currentPlayer;
                }
            }
        }
        if((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X' && winner == '_') //Checks both diagonal lines for a winner
            || (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O' && winner == '_')){
            //Mark the winning spots red
            for(int i = 0; i < ROWS; i++)
                boardGUI[i][i].setBorder(winBorder);
            winner = currentPlayer;
        }
        if((board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X' && winner == '_')
            || (board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O' && winner == '_')){
            winner = currentPlayer;
            //Mark the winning spots red
            boardGUI[0][2].setBorder(winBorder);
            boardGUI[1][1].setBorder(winBorder);
            boardGUI[2][0].setBorder(winBorder);
        }
    }
}