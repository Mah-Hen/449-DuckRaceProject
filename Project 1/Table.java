public class Table{
    private String [][] tab;
    private int rows;
    private int cols;


    public Table(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.tab = new String [rows][cols]; // (x,y)
        for(int x=0; x<rows; x++)
            for(int y=0; y<cols; y++)
                this.tab[x][y] =  " ";
    }

    public void createBoard(){
       for(int x=0; x<this.rows; x++){
        for(int y=0; y<this.cols; y++){
            if((x+y) % 2 == 0){
                this.tab[x][y] = "[] ";
            }
            else{
                this.tab[x][y] = "[] ";
            }
        }
       }

    }

    public void displayBoard(){
        for(int x=0; x<this.rows; x++){
            for(int y=0; y<this.rows; y++){
                System.out.print(this.tab[x][y] + " ");
            }
            System.out.println(); // this print moves to the next line after each row to give it the board like feel. 
        }

    }
}