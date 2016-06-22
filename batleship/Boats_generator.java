import java.util.Random;

public class Boats_generator{

    private Board board; 
    private String ship_symbol = "S";
    
    public Boats_generator(Board board){
       this.board = board;
    }
    
    
    public boolean check_fit(int row,int column,int type){
       boolean ret;
       ret = true;
       try{
            for (int count_1 = 0 ; count_1 < type + 1; count_1 ++){
                    if (board.Tablero[row][column + count_1] != null){
                        ret = false;
                        break;
                    }
                }
            }
         catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Array is out of Bounds"+e);
            ret = false;
            }
       return ret;
      }
    
    public boolean generate_ship(int type,int row,int column){
  
        boolean ret1,ret2,ret3,ret4 = false;
        
        if (!check_fit(row,column,type)){
            return false;
        }
        
        if (type == 1){
            ret1 = board.mark(row,column,ship_symbol);
            ret2 = board.mark(row,column + 1,ship_symbol);
          
            return (ret1 && ret2 );
        }
        else if (type == 2){
            ret1 = board.mark(row,column,ship_symbol);
            ret2 = board.mark(row,column + 1,ship_symbol);
            ret3 = board.mark(row,column + 2,ship_symbol);
            
            return (ret1 && ret2 && ret3 );
        }
        else if (type == 3){
            ret1 = board.mark(row,column,ship_symbol);
            ret2 = board.mark(row,column + 1,ship_symbol);
            ret3 = board.mark(row,column + 2,ship_symbol);
            ret4 = board.mark(row,column + 3,ship_symbol);

            return (ret1 && ret2 && ret3 && ret4 );
        }
        else{
            return false;
        }
     }

    public Board get_Board(){
            return this.board;
    }
    
    public static void main(String[] args){

        int cont,row,type,column; 

        Random rand = new Random();
        Board tt = new Board();
        Boats_generator bg = new Boats_generator(tt);
        
        cont = 0;
        while(cont < 4){
            
            type = rand.nextInt(3) + 1;
            row = rand.nextInt(9) + 0;
            column = rand.nextInt(9) + 0;
            
            System.out.println("");
            System.out.println(cont);
            System.out.println(type);
            System.out.println(row);
            System.out.println(column);
            
            if (bg.generate_ship(type,row,column)){
                cont++;
                }
            }
        tt = bg.get_Board();
        System.out.println(tt.toString());
        
        }   
 
}
