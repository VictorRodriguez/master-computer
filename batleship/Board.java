public class Board{

    public String[][] Tablero;

    public Board(){
        this.Tablero = new String[10][10];
    }

    public boolean mark(int row,int column,String symbol){
   
        if (row > 9){
            return false;
        }
            
        if (column > 9){
            return false;
        }
        
        if (this.Tablero[row][column] != symbol){
            this.Tablero[row][column] = symbol;
            return true;
        }
        else{
            return false;
        }
        
    }

    public String toString(){
        String str_tablero = ""; 

        for (int fila = 0; fila < 10; fila ++ ){
            for(int columna = 0 ; columna < 10 ; columna ++){
                str_tablero += Tablero[fila][columna] + " ";
            }
            str_tablero += "\n";
        }
        return str_tablero;
    }


    public static void main(String[] args){

        Board tt = new Board();
        System.out.println(tt.mark(8,9,"X"));
        System.out.println();
        System.out.println(tt.mark(1,2,"X"));
        System.out.println();
        System.out.println(tt.toString());
    }
}


