package tictactoegui;

import javax.swing.JFrame;

public class TicTacToeMain {
    
    public static void main(String[] args){
        OpponentChooser chooser = new OpponentChooser();
        
        chooser.setSize(400, 300);
        chooser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooser.setLocationRelativeTo(null);
        chooser.setVisible(true);
    }
}