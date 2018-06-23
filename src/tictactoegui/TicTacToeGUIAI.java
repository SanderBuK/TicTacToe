package tictactoegui;

import javax.swing.ImageIcon;

public class TicTacToeGUIAI extends TicTacToeGUI{
    
    private boolean takingTurn;
    
    private final int[][] COLLUMBLOCK = new int[][] 
        {{1,4,7},{2,5,8},{3,6,9}};
    private final int[][] TEMPLATE = new int[][] 
        {{1,2,3},{4,5,6},{7,8,9}};
    
    private String difficulty;
    
    private int placement;
    
    TicTacToeGUIAI(String difficulty){
        super.setTitle("Tic Tac Toe - Human vs. AI - " + difficulty);
        
        this.difficulty = difficulty;
    }
    
    @Override
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
        if(currentPlayer == 'O')
            aiTurn();
    }
    
    @Override 
    protected void updateBoard(int row, int collum, ImageIcon icon){
        
        board[row][collum] = currentPlayer;
        boardGUI[row][collum].setIcon(icon);
        
        evaluateScores();
        
        if(winner == '_' && roundNumber < 9){
            if(currentPlayer == 'O')
                currentPlayer = 'X';
            else{
                currentPlayer = 'O';
            }

            currentTurn.setText(currentPlayer + "'s turn!");
            
            roundNumber++;
            
            if (currentPlayer == 'O')
                aiTurn();
            
        }else{
            updateWins();
        }
    }
    
    private void aiTurn(){
        switch(difficulty){
            case "Easy":
                aiTurnEasy();
                break;
            case "Medium":
                aiTurnMedium();
                break;
            case "Hard":
                aiTurnHard();
                break;
        }
    }
    
    private void aiTurnEasy(){
        takingTurn = true;
        
        while(takingTurn){
            placement = (int) (Math.random() * 9 + 1);
            evaluatePlacement(placement);
        }
    }
    
    private void aiTurnMedium(){
        takingTurn = true;
        placement = 0;
        
        while(takingTurn){
            if(takingTurn){
                aiRow(2, 'O', 'X');
                aiCollum(2, 'O', 'X');
                aiDiagonal(2, 1, 4, 'O', 'X');
                aiDiagonal(2, 3, 2, 'O', 'X');

                aiRow(2, 'X', 'O');
                aiCollum(2, 'X', 'O');
                aiDiagonal(2, 1, 4, 'X', 'O');
                aiDiagonal(2, 3, 2, 'X', 'O');
            }

            if(takingTurn){
                placement = (int) (Math.random() * 9 + 1);
                evaluatePlacement(placement);
            }
        }
    }
    
    private void aiTurnHard(){
        takingTurn = true;
        placement = 0;
        
        while(takingTurn){
            if(roundNumber == 1){
                placement = placeCorner();
                evaluatePlacement(placement);
            }
            
            if(roundNumber == 2){
                if(board[1][1] == 'X'){
                    placement = placeCorner();
                    evaluatePlacement(placement);
                }
                else if(board[0][0] == 'X' || board[0][2] == 'X' || board[2][0] == 'X' || board[2][2] == 'X'){
                    placement = 5;
                    evaluatePlacement(placement);
                }
            }
            
            if(takingTurn){
                aiRow(2, 'O', 'X');
                aiCollum(2, 'O', 'X');
                aiDiagonal(2, 1, 4, 'O', 'X');
                aiDiagonal(2, 3, 2, 'O', 'X');
                
                aiRow(2, 'X', 'O');
                aiCollum(2, 'X', 'O');
                aiDiagonal(2, 1, 4, 'X', 'O');
                aiDiagonal(2, 3, 2, 'X', 'O');
                
                aiCollum(1, 'O', 'X');
                aiRow(1, 'O', 'X');
                aiDiagonal(1, 1, 4, 'O', 'X');
                aiDiagonal(1, 3, 2, 'O', 'X');
            }
            
            if(takingTurn){
                placement = (int) (Math.random() * 9 + 1);
                evaluatePlacement(placement);
            }
        }
    }
    
    public void evaluatePlacement(int placement){
        for(int row = 0; row < ROWS; row++){
            for(int collum = 0; collum < COLLUMS; collum++){
//                Check using the 2D array template if the number in the corresponding place is taken
//                in the actual 2D array
                if(placement == TEMPLATE[row][collum]){
                    if(board[row][collum] == '_'){
                        updateBoard(row, collum, oIcon);
                        takingTurn = false;
                    }
                }
            }
        }
    }
    
    //Check each row, collum and diagonal if there are two competitor marks, and if so block them.
    //Possibly increment a value for each oponent mark and if the value is 2 mark the remaining spot.
    public void aiRow(int close, char W, char L){
        for(int row = 0; row < ROWS; row++){    
            int num1 = 0;
            int num2 = 0;

            for(int collum = 0; collum < COLLUMS; collum++){
                if(board[row][collum] == W)
                    num1++;
                else if(board[row][collum] == L)
                    num2++;
            }
            while(num1 == close && num2 == 0 && takingTurn == true){ //Keep trying to fill the spot
                placement = (int)(Math.random() * 3 + 1 + (3 * row)); //Make the placement number 1-3, 4-6, 7-9
                evaluatePlacement(placement);
            }
            if(close == 3){
                while(num1 == 1 && num2 == 0 && takingTurn == true){
                    
                }
            }
        }
    }
    
    public void aiCollum(int close, char W, char L){
        for(int collum = 0; collum < COLLUMS; collum++){    
            int num1 = 0;
            int num2 = 0;

            for(int row = 0; row < ROWS; row++){
                if(board[row][collum] == W)
                    num1++;
                else if(board[row][collum] == L)
                    num2++;
            }
            while(num1 == close && num2 == 0 && takingTurn == true){ //Keep trying to fill the spot
                int random = (int) (Math.random() * 3);
                placement = COLLUMBLOCK[collum][random];
                evaluatePlacement(placement);
            }
        }
    }
    
    public void aiDiagonal(int close, int adder, int add, char W, char L){ //Check diagonal both ways
        int num1 = 0;
        int num2 = 0;
        int placeTry = adder;
        int[] placements = new int[3];
        
        for(int i = 0; i < placements.length; i++)
            placements[i] = adder + (add * i);
        
        for(int i = 0; i < 3; i++){
            for(int row = 0; row < ROWS; row++){
                for(int collum = 0; collum < COLLUMS; collum++){
                    if(placements[i] == TEMPLATE[row][collum]){
                        if(board[row][collum] == W)
                            num1++;
                        else if(board[row][collum] == L)
                            num2++;
                    }
                }
            }
        }
        
        while(num1 == close && num2 == 0 && takingTurn == true){ //Keep trying to fill the spot
            placement = placeTry;
            evaluatePlacement(placement);
            placeTry += add;
        }
    }
    
    private int placeCorner(){
        int chance = (int) (Math.random() * 4 + 1);
        switch(chance){
            case 1:
                placement = 1;
                break;
            case 2:
                placement = 3;
                break;
            case 3:
                placement = 7;
                break;
            case 4:
                placement = 9;
                break;
        }
        return placement;
    }
    
    private void delay(double from, double to){ //Delay feature used for AI's turn
        double currentTime = System.currentTimeMillis();
        
        int random = (int)(Math.random() * (to - from) + from);
        
        while(currentTime > System.currentTimeMillis() - random){
            //Wait for time to pass
        }
    }
}