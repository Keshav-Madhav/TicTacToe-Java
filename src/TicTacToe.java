import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class TicTacToe implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel= new JPanel();
    JPanel button_panel= new JPanel();
    JPanel btm_panel= new JPanel();
    JLabel text_field = new JLabel();
    JButton reset_btn = new JButton();
    JButton crosses_btn = new JButton();
    JButton zeros_btn =new JButton();
    JButton ai_btn=new JButton();
    JButton[] buttons =new JButton[9];
    boolean X_deploy = true;
    boolean ai_true=false;
    boolean ai_turn=false;
    boolean draw=false;

    TicTacToe() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,900);
        frame.getContentPane().setBackground(new Color(20,20,20));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        text_field.setBackground(new Color(20,20,20));
        text_field.setForeground(new Color(171, 171, 171));
        text_field.setFont(new Font("Ink Free", Font.BOLD,75));
        text_field.setHorizontalAlignment(JLabel.CENTER);
        text_field.setText("Tic-Tac-Toe");
        text_field.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100 );
        title_panel.setBackground(new Color(20,20,20));

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(20, 20, 20));

        btm_panel.setBackground(new Color(20,20,20));

        for(int i=0; i<9; i++){

            buttons[i]= new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setBackground(new Color(224, 234, 244));
            buttons[i].setFont(new Font("Verdana", Font.PLAIN,180));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);

        }

        reset_btn.setText("Reset");
        reset_btn.setFont(new Font("Ink Free", Font.BOLD,25));
        reset_btn.addActionListener(this);
        reset_btn.setLayout(null);

        crosses_btn.setText("Crosses");
        crosses_btn.setFont(new Font("Ink Free", Font.BOLD,25));
        crosses_btn.addActionListener(this);
        crosses_btn.setLayout(null);

        zeros_btn.setText("Zeros");
        zeros_btn.setFont(new Font("Ink Free", Font.BOLD,25));
        zeros_btn.addActionListener(this);
        zeros_btn.setLayout(null);

        ai_btn.setText("AI off");
        ai_btn.setFont(new Font("Ink Free", Font.BOLD,25));
        ai_btn.addActionListener(this);
        ai_btn.setLayout(null);

        btm_panel.add(reset_btn);
        btm_panel.add(crosses_btn);
        btm_panel.add(zeros_btn);
        btm_panel.add(ai_btn);
        btm_panel.setBounds(0, 0, 800, 400);


        title_panel.add(text_field);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        frame.add(btm_panel,BorderLayout.SOUTH);
        firstTurn();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

//        Action listener for playing buttons
        for (int i=0;i<9;i++){
            if (e.getSource()==buttons[i]){
                if (!draw) {
                    if (X_deploy) {
                        System.out.println("calling X-Turn");
                        XTurn(i);


                    }
                    else {
                        System.out.println("calling O-Turn");
                        OTurn(i);
                    }
                }

                if (ai_true) {
                    ai_turn=true;
                    aiMove();
                }
            }
        }

//        ACTION LISTENER FOR CROSS BTN
        if (e.getSource()==crosses_btn){
            X_deploy =true;
            text_field.setText("X turn");
            System.out.println("X is playing first");
            crosses_btn.setEnabled(false);
            zeros_btn.setEnabled(true);
        }

//        ACTION LISTENER FOR ZEROS BTN
        if (e.getSource()==zeros_btn){
            X_deploy =false;
            text_field.setText("O turn");
            System.out.println("O is playing first");
            zeros_btn.setEnabled(false);
            crosses_btn.setEnabled(true);
        }

//        ACTION LISTENER FOR RESET BTN
        if(e.getSource()==reset_btn){
            System.out.println();
            System.out.println("Game reset");
            System.out.println();
            for(int i=0;i<9;i++) {
                buttons[i].setText("");
                buttons[i].setEnabled(true);
                buttons[i].setBackground(new Color(224, 234, 244));
            }
            crosses_btn.setEnabled(true); 
            zeros_btn.setEnabled(true);
            ai_true=false;
            draw=false;
            ai_btn.setText("AI off");
            ai_btn.setEnabled(true);
            firstTurn();

        }

//        ACTION LISTENER FOR AI BTN
        if (e.getSource()==ai_btn){
            ai_true=true;
            ai_btn.setText("AI on");
            System.out.println("AI is on");
            ai_btn.setEnabled(false);
            UIManager.put("Button.disabledText", new ColorUIResource(new Color(117, 117, 117)));

            aiMove();
        }

    }

    public void firstTurn() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(random.nextInt(2)==1){
            X_deploy =true;
            text_field.setText("X turn");
        }
        else{
            X_deploy =false;
            text_field.setText("O turn");
        }

    }

    public void XTurn(int i){

        if (Objects.equals(buttons[i].getText(), "")){
            buttons[i].setForeground(new Color(0,0,255));
            buttons[i].setText("X");
            buttons[i].setBackground(new Color(230, 240, 250));
            System.out.println("X deployed: "+i);
            System.out.println();
            X_deploy =false;
            text_field.setText("O turn");
            crosses_btn.setEnabled(false);
            zeros_btn.setEnabled(false);
            check();
        }

    }

    public void OTurn(int i){

        if (Objects.equals(buttons[i].getText(), "")){
            buttons[i].setForeground(new Color(255,0,0));
            buttons[i].setText("O");
            buttons[i].setBackground(new Color(230, 240, 250));
            System.out.println("O deployed: "+i);
            System.out.println();
            X_deploy =true;
            text_field.setText("X turn");
            crosses_btn.setEnabled(false);
            zeros_btn.setEnabled(false);
            check();
        }

    }

    public void aiMove(){

        if (ai_turn && !draw){

            int i=9;
            if (Objects.equals(buttons[0].getText(), "")) {
                if (    Objects.equals(buttons[1].getText(), buttons[2].getText()) && !Objects.equals(buttons[1].getText(), "") ||
                        Objects.equals(buttons[3].getText(), buttons[6].getText()) && !Objects.equals(buttons[3].getText(), "")||
                        Objects.equals(buttons[4].getText(), buttons[8].getText()) && !Objects.equals(buttons[4].getText(), "")) {
                    i = 0;
                }
            }

            if (Objects.equals(buttons[1].getText(), "")) {
                if (    Objects.equals(buttons[0].getText(), buttons[2].getText()) && !Objects.equals(buttons[0].getText(), "") ||
                        Objects.equals(buttons[4].getText(), buttons[7].getText()) && !Objects.equals(buttons[4].getText(), "")) {
                    i = 1;
                }
            }

            if (Objects.equals(buttons[2].getText(), "")) {
                if (    Objects.equals(buttons[0].getText(), buttons[1].getText()) && !Objects.equals(buttons[0].getText(), "") ||
                        Objects.equals(buttons[4].getText(), buttons[6].getText()) && !Objects.equals(buttons[4].getText(), "") ||
                        Objects.equals(buttons[5].getText(), buttons[8].getText()) && !Objects.equals(buttons[5].getText(), "")) {
                    i = 2;
                }
            }

            if (Objects.equals(buttons[3].getText(), "")) {
                if (    Objects.equals(buttons[4].getText(), buttons[5].getText()) && !Objects.equals(buttons[4].getText(), "") ||
                        Objects.equals(buttons[0].getText(), buttons[6].getText()) && !Objects.equals(buttons[0].getText(), "") ) {
                    i = 3;
                }
            }

            if (Objects.equals(buttons[4].getText(), "")) {
                if (    Objects.equals(buttons[1].getText(), buttons[7].getText()) && !Objects.equals(buttons[1].getText(), "")||
                        Objects.equals(buttons[3].getText(), buttons[5].getText()) && !Objects.equals(buttons[3].getText(), "")||
                        Objects.equals(buttons[0].getText(), buttons[8].getText()) && !Objects.equals(buttons[0].getText(), "")||
                        Objects.equals(buttons[2].getText(), buttons[6].getText()) && !Objects.equals(buttons[2].getText(), "")) {
                    i = 4;
                }
            }

            if (Objects.equals(buttons[5].getText(), "")) {
                if (    Objects.equals(buttons[2].getText(), buttons[8].getText()) && !Objects.equals(buttons[2].getText(), "") ||
                        Objects.equals(buttons[3].getText(), buttons[4].getText()) && !Objects.equals(buttons[3].getText(), "")) {
                    i = 5;
                }
            }

            if (Objects.equals(buttons[6].getText(), "")) {
                if (    Objects.equals(buttons[0].getText(), buttons[3].getText()) && !Objects.equals(buttons[0].getText(), "")||
                        Objects.equals(buttons[2].getText(), buttons[4].getText()) && !Objects.equals(buttons[2].getText(), "")||
                        Objects.equals(buttons[7].getText(), buttons[8].getText()) && !Objects.equals(buttons[7].getText(), "")) {
                    i = 6;
                }
            }

            if (Objects.equals(buttons[7].getText(), "")) {
                if (    Objects.equals(buttons[1].getText(), buttons[4].getText()) && !Objects.equals(buttons[1].getText(), "")||
                        Objects.equals(buttons[6].getText(), buttons[8].getText()) && !Objects.equals(buttons[6].getText(), "")) {
                    i = 7;
                }
            }

            if (Objects.equals(buttons[8].getText(), "")) {
                if (    Objects.equals(buttons[0].getText(), buttons[4].getText()) && !Objects.equals(buttons[0].getText(), "")||
                        Objects.equals(buttons[2].getText(), buttons[5].getText()) && !Objects.equals(buttons[2].getText(), "")||
                        Objects.equals(buttons[6].getText(), buttons[7].getText()) && !Objects.equals(buttons[6].getText(), "")) {
                    i = 8;
                }
            }
            if (i>8){
                i= random.nextInt(9);
                System.out.println("ai chose randomly");
            }

            System.out.println("ai: "+i);

            //  0    1    2
            //  3    4    5
            //  6    7    8

            if (Objects.equals(buttons[i].getText(), "")) {
                if (X_deploy) {
                    XTurn(i);
                } else {
                    OTurn(i);
                }

            }
            else{
                System.out.println("<ai re-roll>");
                aiMove();
            }
        }
        ai_turn=false;

    }

    public void check(){

        if ((Objects.equals(buttons[0].getText(), "X")) && (Objects.equals(buttons[1].getText(), "X")) && (Objects.equals(buttons[2].getText(), "X"))){
            xWins(0, 1, 2) ;
        }
        if ((Objects.equals(buttons[3].getText(), "X")) && (Objects.equals(buttons[4].getText(), "X")) && (Objects.equals(buttons[5].getText(), "X"))){
            xWins(3,4, 5) ;
        }
        if ((Objects.equals(buttons[6].getText(), "X")) && (Objects.equals(buttons[7].getText(), "X")) && (Objects.equals(buttons[8].getText(), "X"))){
            xWins(6, 7, 8) ;
        }
        if ((Objects.equals(buttons[0].getText(), "X")) && (Objects.equals(buttons[4].getText(), "X")) && (Objects.equals(buttons[8].getText(), "X"))){
            xWins(0, 4, 8) ;
        }
        if ((Objects.equals(buttons[2].getText(), "X")) && (Objects.equals(buttons[4].getText(), "X")) && (Objects.equals(buttons[6].getText(), "X"))){
            xWins(2, 4, 6) ;
        }
        if ((Objects.equals(buttons[0].getText(), "X")) && (Objects.equals(buttons[3].getText(), "X")) && (Objects.equals(buttons[6].getText(), "X"))){
            xWins(0, 3, 6) ;
        }
        if ((Objects.equals(buttons[1].getText(), "X")) && (Objects.equals(buttons[4].getText(), "X")) && (Objects.equals(buttons[7].getText(), "X"))){
            xWins(1, 4, 7) ;
        }
        if ((Objects.equals(buttons[2].getText(), "X")) && (Objects.equals(buttons[5].getText(), "X")) && (Objects.equals(buttons[8].getText(), "X"))){
            xWins(2, 5, 8) ;
        }

        //  0    1    2
        //  3    4    5
        //  6    7    8

        if ((Objects.equals(buttons[0].getText(), "O")) && (Objects.equals(buttons[1].getText(), "O")) && (Objects.equals(buttons[2].getText(), "O"))){
            oWins(0, 1, 2) ;
        }
        if ((Objects.equals(buttons[3].getText(), "O")) && (Objects.equals(buttons[4].getText(), "O")) && (Objects.equals(buttons[5].getText(), "O"))){
            oWins(3,4, 5) ;
        }
        if ((Objects.equals(buttons[6].getText(), "O")) && (Objects.equals(buttons[7].getText(), "O")) && (Objects.equals(buttons[8].getText(), "O"))){
            oWins(6, 7, 8) ;
        }
        if ((Objects.equals(buttons[0].getText(), "O")) && (Objects.equals(buttons[4].getText(), "O")) && (Objects.equals(buttons[8].getText(), "O"))){
            oWins(0, 4, 8) ;
        }
        if ((Objects.equals(buttons[2].getText(), "O")) && (Objects.equals(buttons[4].getText(), "O")) && (Objects.equals(buttons[6].getText(), "O"))){
            oWins(2, 4, 6) ;
        }
        if ((Objects.equals(buttons[0].getText(), "O")) && (Objects.equals(buttons[3].getText(), "O")) && (Objects.equals(buttons[6].getText(), "O"))){
            oWins(0, 3, 6) ;
        }
        if ((Objects.equals(buttons[1].getText(), "O")) && (Objects.equals(buttons[4].getText(), "O")) && (Objects.equals(buttons[7].getText(), "O"))){
            oWins(1, 4, 7) ;
        }
        if ((Objects.equals(buttons[2].getText(), "O")) && (Objects.equals(buttons[5].getText(), "O")) && (Objects.equals(buttons[8].getText(), "O"))) {
            oWins(2, 5, 8);
        }


        int count=0;
        for (int i=0;i<9;i++){
            if(!Objects.equals(buttons[i].getText(), "")){
                count++;
            }
        }
        if (count==9){
            text_field.setText("Draw");
            draw=true;
            System.out.println("Game is Drawn");
        }
    }

    public void xWins(int a, int b, int c){

        for (int i=0;i<9;i++){
            buttons[i].setBackground(new Color(23, 23, 23));
            buttons[i].setEnabled(false);
        }

        UIManager.put("Button.disabledText", new ColorUIResource(new Color(255, 255 ,255)));

        buttons[a].setBackground(new Color(21, 145, 21));
        buttons[b].setBackground(new Color(21, 145, 21));
        buttons[c].setBackground(new Color(21, 145, 21));

        text_field.setText("X wins");
        System.out.println("X won with: ("+a+", "+b+", "+c+")");
        ai_true=false;

    }

    public void oWins(int a, int b, int c){

        for (int i=0;i<9;i++){
            buttons[i].setBackground(new Color(23, 23, 23));
            buttons[i].setEnabled(false);
        }

        UIManager.put("Button.disabledText", new ColorUIResource(new Color(255, 255 ,255)));

        buttons[a].setBackground(new Color(21, 145, 21));
        buttons[b].setBackground(new Color(21, 145, 21));
        buttons[c].setBackground(new Color(21, 145, 21));

        text_field.setText("O wins");
        System.out.println("O won with: ("+a+", "+b+", "+c+")");
        ai_true=false;

    }
}
