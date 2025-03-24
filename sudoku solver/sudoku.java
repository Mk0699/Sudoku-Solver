import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//class containing the gui and backend of sudoku solver
class sudoku implements ActionListener{
    //Intializing components names globally in the class
    JFrame frame;
    JPanel panel1,panel2;
    JPanel[] panels;
    JTextField[] slots;
    JButton[] buttons;
    char[][] board;

    sudoku(){

        //creating objects of respective components

        //frame holds the entire application
        frame = new JFrame("Sudoku Solver");
        //panel1 contains all the input fields of sudoku
        panel1 = new JPanel();
        //panel2 contains buttons to operate with inputs or take action
        panel2 = new JPanel();
        //panels is an array of 9 panels where each panel has 9 input fields and represent a square in sudoku board.All these 9 panels are added to main panel1 which completes the input field
        panels = new JPanel[9];
        //slots is array of 81 textfields which take input from user . They are added in the 9 square panels according to there positions 
        slots = new JTextField[81];
        //buttons is an array of buttons used to operate with the input data. "button[0]" is basically "solve" button which solves the given sudoku if input is valid and "button[1]" is "clear" button which clears the input of sudoku board
        buttons = new JButton[2];
        //board is a 2-D 9x9 character array which represents a 9x9 sudoku board.The input from the "slots" text field is transfered to "board" array when we press the "solve" button and the input is processed if it is valid
        board = new char[9][9];
        
        //setting backgroud color of frame to black
        frame.getContentPane().setBackground(Color.black);
        //Making the frame or window non resizable(u cant expand or contract the size of window) 
        frame.setResizable(false);
        //Setting the default operation of 'X'(close) button to exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Selecting no specific layout for the frame
        frame.setLayout(null);


        //setting the background of panel1 to gray
        panel1.setBackground(Color.gray);
        //setting the layout of panel1 to grid layout with 3 rows and 3 columns and with a horizontal gap and vertical gap of 20px between each component of panel1
        panel1.setLayout(new GridLayout(3,3,20,20));
        //setting the bounds of panel1 the top left corner of the panel1 is at coordinates x:30px and y:50px with a width of 600px and height of 600px
        panel1.setBounds(30,50,600,600);
        
        //setting the backgroud of panel2 to blue
        panel2.setBackground(Color.blue);
        //setting the layout of panel2 to grid layout with 1 rows and 2 columns and with a horizontal gap of 40px and vertical gap of 25px between each component of panel2
        panel2.setLayout(new GridLayout(1,2,40,25));
        //setting the bounds of panel1 the top left corner of the panel2 is at coordinates x:30px and y:700px with a width of 600px and height of 50px
        panel2.setBounds(30,700,600,50);
        
        //k is variable to keep track of index of the slots array
        int k=0;

        //creating 9 sub panels , setting color to cyan , setting layout of sub panels as  grid layout with rows=3 columns=3 and horizontal and vertical gap as 20px, adding the sub panel to main panel1 
        for(int i=0 ; i<9 ; i++){
            panels[i] = new JPanel();
            panels[i].setBackground(Color.cyan);
            panels[i].setLayout(new GridLayout(3,3,20,20));
            panel1.add(panels[i]);
        }

        //creating
        for(int i=0 ; i<9 ; i++){
            for(int j=0 ; j < 9 ; j++){
                slots[k] = new JTextField();
                slots[k].setFont(new Font("MV BOLI",Font.BOLD,30));
                slots[k].setBorder(BorderFactory.createEmptyBorder());
                panels[checkSquare(i,j)].add(slots[k]);
                k++;
            }
        }

        for(int i=0 ; i <2 ; i++){
            buttons[i] = new JButton();
            buttons[i].setFocusable(false);
            buttons[i].setBackground(Color.cyan);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            panel2.add(buttons[i]);
        }

        buttons[0].setText("solve");
        buttons[0].addActionListener(this);
        buttons[0].setFont(new Font("MV BOLI", Font.BOLD, 50));

        buttons[1].setText("reset");
        buttons[1].addActionListener(this);
        buttons[1].setFont(new Font("MV BOLI", Font.BOLD, 50));
        
        frame.add(panel1);
        frame.add(panel2);
        frame.setSize(700, 900);
        frame.setVisible(true);
    }

    static void warning(String message){
        JOptionPane.showMessageDialog(null, message, "Invalid sudoku input", JOptionPane.INFORMATION_MESSAGE);
    }

    static boolean isNumeric(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    static String validSudoku(char[][] board){

        List<Set<Character>> squares = new ArrayList<>();

        for(int i=0 ; i<9 ; i++){
            squares.add(new HashSet<>());
        }

        for(int i=0 ; i<9 ; i++){
            for(int j=0 ; j<9 ; j++){
                if(board[i][j]=='.'){continue;}
                if(!isNumeric(String.valueOf(board[i][j]))){
                    return "Input is not numeric";
                }else if(board[i][j]<'1' || board[i][j]>'9'){
                    return "Only number from 1 to 9 are allowed";
                }
                int count=0;
                for(int a=0 ; a<9 ; a++){
                    if(board[i][a]==board[i][j]){
                        count++;
                    }
                    if(board[a][j]==board[i][j]){
                        count++;
                    }
                    if(count>2){
                        return "Each row and column should contain unique numbers";
                    }
                }
                if(squares.get(checkSquare(i,j)).contains(board[i][j])){
                    return "Each square should contain unique numbers";
                }else{
                    squares.get(checkSquare(i,j)).add(board[i][j]);
                }
            }
        }


        return "";
    }

    static int checkSquare(int a,int b){

        if(a>=0 && a<3 && b>=0 && b<3){return 0;}
        if(a>=0 && a<3 && b>=3 && b<6){return 1;}
        if(a>=0 && a<3 && b>=6 && b<9){return 2;}
        if(a>=3 && a<6 && b>=0 && b<3){return 3;}
        if(a>=3 && a<6 && b>=3 && b<6){return 4;}
        if(a>=3 && a<6 && b>=6 && b<9){return 5;}
        if(a>=6 && a<9 && b>=0 && b<3){return 6;}
        if(a>=6 && a<9 && b>=3 && b<6){return 7;}
        if(a>=6 && a<9 && b>=6 && b<9){return 8;}

        return -1;
    }

    static boolean backtrack(char[][] board,List<Set<Character>> rows,List<Set<Character>> columns,List<Set<Character>> squares){
        
        for(int i=0 ; i < 9 ; i++){
            for(int j=0 ; j < 9 ; j++){
                if(board[i][j]!='.'){continue;}
                for(char ch='1' ; ch<='9' ; ch++){
                    if(!rows.get(i).contains(ch) && !columns.get(j).contains(ch) && !squares.get(checkSquare(i,j)).contains(ch)){
                        rows.get(i).add(ch);
                        columns.get(j).add(ch);
                        squares.get(checkSquare(i,j)).add(ch);
                        board[i][j]=ch;
                        if(backtrack(board,rows,columns,squares)){
                            return true;
                        }
                        board[i][j]='.';
                        rows.get(i).remove(ch);
                        columns.get(j).remove(ch);
                        squares.get(checkSquare(i,j)).remove(ch);
                    }
                }
                return false;
            }
        }

        return true;
    }
    

    static void solve(char[][] board){

        List<Set<Character>> rows = new ArrayList<>();
        List<Set<Character>> columns = new ArrayList<>();
        List<Set<Character>> squares = new ArrayList<>();

        for(int i=0 ; i<9 ; i++){
            rows.add(new HashSet<>());
            columns.add(new HashSet<>());
            squares.add(new HashSet<>());
        }

        for(int i=0 ; i<9 ; i++){
            for(int j=0 ; j<9 ; j++){
                if(board[i][j]=='.'){continue;}
                rows.get(i).add(board[i][j]);
                columns.get(j).add(board[i][j]);
                squares.get(checkSquare(i,j)).add(board[i][j]);
            }
        }

        backtrack(board, rows, columns, squares);
    }

    public void actionPerformed(ActionEvent e){
        //the action which is performed when we press the solve button
        if(e.getSource()==buttons[0]){
            

            //retrive the intial inputs in sudoku board
            int k=0;
            for(int i=0 ; i<9 ; i++){
                for(int j=0; j<9 ; j++){
                    if(slots[k].getText().length()==1){
                        board[i][j]=slots[k].getText().charAt(0);
                    }else if(slots[k].getText().isEmpty()){
                        board[i][j]='.';
                    }else{
                        warning("Input is only from 1 to 9");
                        for(int a=0 ; a < 81 ; a++){
                            slots[a].setText("");
                        }
                        return;
                    }
                    k++;
                }
            }

            String message=validSudoku(board);
            
            if(message!=""){
                warning(message);
                for(int i=0 ; i < 81 ; i++){
                    slots[i].setText("");
                }
                return;
            }

            solve(board);

            k=0;

            for(int i=0 ; i<9  ;i++){
                for(int j=0 ; j <9;j++){
                    slots[k].setText(String.valueOf(board[i][j]));
                    k++;
                }
            }

        }else{
            for(int i=0 ; i < 81 ; i++){
                slots[i].setText("");
            }
        }

    }

    public static void main(String[] args) {
        new sudoku();
        return;
    }
}