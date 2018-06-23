package tictactoegui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class OpponentChooser extends JFrame{
    
    private JLabel chooseOpponentLabel;
    
    private JPanel picPanel;
    
    private JLabel vsHuman;
    private JLabel vsAI;
    
    private ImageIcon iconHuman;
    private ImageIcon iconAI;
    
    OpponentChooser(){
        super("Choose Opponent");
        
        Font font = new Font("Arial", Font.BOLD, 25);
        LineBorder border = new LineBorder(Color.BLACK, 3);
        
        chooseOpponentLabel = new JLabel("Choose your opponent!", JLabel.CENTER);
        chooseOpponentLabel.setFont(font);
        
        picPanel = new JPanel(new GridLayout(1, 2));
        
        //Initialize the images with their path
        URL menneskeURL = OpponentChooser.class.getResource("/resources/menneske.png");
        iconHuman = new ImageIcon(menneskeURL);
        URL robotURL = OpponentChooser.class.getResource("/resources/robot.png");
        iconAI = new ImageIcon(robotURL);
        vsHuman = new JLabel();
        vsAI = new JLabel();
        
        //Add borders to the labels
        vsHuman.setBorder(border);
        vsAI.setBorder(border);
        //Add the icons to the labels
        vsHuman.setIcon(iconHuman);
        vsAI.setIcon(iconAI);
        //Centrialize the images in the labels
        vsHuman.setHorizontalAlignment(JLabel.CENTER);
        vsHuman.setVerticalAlignment(JLabel.CENTER);
        vsAI.setHorizontalAlignment(JLabel.CENTER);
        vsAI.setVerticalAlignment(JLabel.CENTER);
        
        
        //Add inner MouseListener classes for both human and ai labels
        vsHuman.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseClicked(MouseEvent event){
                TicTacToeGUI game = new TicTacToeGUI();
                //setVisible(false);
                game.setSize(500, 530);
                game.setLocationRelativeTo(null);
                game.setVisible(true);
            }  
        }); 
        
        vsAI.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseClicked(MouseEvent event){
                OpponentChooser chooseDifficulty = new OpponentChooser("Choose difficulty");
                
                chooseDifficulty.setSize(275, 150);
                chooseDifficulty.setLocationRelativeTo(null);
                chooseDifficulty.setVisible(true);
            }  
        }); 
        
        picPanel.add(vsHuman);
        picPanel.add(vsAI);
        
        super.add(chooseOpponentLabel, BorderLayout.NORTH);
        super.add(picPanel);
    }
    
    private String difficulty;
    
    private JPanel difficultyPanel = new JPanel(new GridLayout(1, 3));
    
    private JButton[] difficultyButtons = new JButton[3];
    
    private JButton easy = new JButton("Easy");
    private JButton medium = new JButton("Medium");
    private JButton hard = new JButton("Hard");
    
    OpponentChooser(String title){
        super.setTitle("Choose difficulty");
        
        Font font = new Font("Arial", Font.BOLD, 13);
        
        difficultyButtons[0] = easy;
        difficultyButtons[1] = medium;
        difficultyButtons[2] = hard;
        
        for(JButton but : difficultyButtons){
            but.setFont(font);
            but.addActionListener(new ButtonHandler());
            difficultyPanel.add(but);
        }
        
        super.add(difficultyPanel);
    }
    
    private class ButtonHandler implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent event){
            JButton temp = (JButton) event.getSource();
            difficulty = temp.getText();
            TicTacToeGUIAI game = new TicTacToeGUIAI(difficulty);
            setVisible(false);
            game.setSize(500, 530);
            game.setLocationRelativeTo(null);
            game.setVisible(true);
        }
    }
}