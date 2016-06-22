import java.util.Random;
//import javax.swing.*;
import java.io.*;


public class Juego{

public static int play(Board board_missiles, Board enemy_board_ships, int row,int column){

    if (row > 9 || column > 9 ){
        System.out.println("Out of boundaries");
        return -1;
    }

    if (board_missiles.Tablero[row][column] == "*" || board_missiles.Tablero[row][column] == "$" ){
        System.out.println("Hit before");
        return -1;
    }

    if (enemy_board_ships.Tablero[row][column] == "S"){
        System.out.println(" You Hit !!!");
        enemy_board_ships.Tablero[row][column] = "h";
        board_missiles.Tablero[row][column] = "$";
        return 0;
    }
    else{
        System.out.println(" You Miss !!!");
        enemy_board_ships.Tablero[row][column] = "!";
        board_missiles.Tablero[row][column] = "*";
        return 1;
    }
}

public static void play_cpu(Board board_missiles, Board enemy_board_ships){
    int row = Random(9,0);
    int column = Random(9,0);    
    play(board_missiles,enemy_board_ships,row,column);
}

public static void play_player(Board board_missiles, Board enemy_board_ships,int row,int column){
    play(board_missiles,enemy_board_ships,row,column);
}

public static void print_boards(String owner,Board board_ships, Board board_missiles){

    System.out.println("");
    System.out.println(owner);
    System.out.println("Ships Board");
    System.out.println(board_ships.toString());
    
    System.out.println("");
    System.out.println(owner);
    System.out.println("Missiles Board");
    System.out.println(board_missiles.toString());
}
public static Board Generate_board_random(Board board){

        int cont,row,type,column;

        Boats_generator boats_gen = new Boats_generator(board);
        
        cont = 0;
        while(cont < 5){
            
            type = Random(3,1);
            row = Random(9,0);
            column = Random(9,0);
        
            /*
            System.out.println("");
            System.out.println(cont);
            System.out.println(type);
            System.out.println(row);
            System.out.println(column);
            */
            
            if (boats_gen.generate_ship(type,row,column)){
                cont++;
            }
        }
        board = boats_gen.get_Board();
        return board;

}


public static int Random(int MAX,int MIN){

    Random rand = new Random();
    int  n = rand.nextInt(MAX) + MIN;
    return n;
}

public static String Menu(BufferedReader br) throws Exception{ 
        System.out.println("Chose one option");
        System.out.println("1) Play game");
        System.out.println("2) Show Scores");
        System.out.println("3) End game");
        String option = br.readLine();
        return option;
   }
   
public static void main(String[] args) throws Exception{

    //Initialize variables
    boolean first_player_asigned = false;
    String[] Players = {"Player","CPU"};
    String row,column = "0";
    
    //Init readers
    InputStreamReader isr = new  InputStreamReader (System.in);
    BufferedReader br = new BufferedReader(isr);
   
   //Welcome message
    System.out.println("HI, Please put your name");
    String name_player = br.readLine();
    String menu_option = Menu(br);
    

    //Create player    
    Jugador p1 = new Jugador(name_player);
    
    //Create boards of CPU and player ( Boards for ships and missiles)
    Board player_board_ships = new Board();
    Board cpu_board_ships = new Board();
    Board player_board_missiles = new Board();
    Board cpu_board_missiles = new Board();
    
    
    player_board_ships = Generate_board_random(player_board_ships);
    cpu_board_ships = Generate_board_random(cpu_board_ships);
    
    

    while(menu_option.equals("3") == false){

       if(menu_option.equals("1")){

           System.out.println("LET'S PLAY "+name_player+" VS CPU");
           if (!first_player_asigned){
                if (Random(1000,1)%2 == 0 ){
                    //Player starts
                    System.out.println( name_player + " starts first ");
                    print_boards(name_player,player_board_ships,player_board_missiles);
                }
                else{
                    // CPU starts
                    System.out.println("CPU starts first");
                    play_cpu(cpu_board_missiles,player_board_ships);
                    print_boards("CPU",cpu_board_ships,cpu_board_missiles);
                }
                first_player_asigned = true;
           }
           

            // Get Row and Column
            System.out.println(name_player + " choose Row ");
            row = br.readLine();
                
            System.out.println(name_player + " choose Column");
            column = br.readLine();

            // Play with that row and column
            play_player(player_board_missiles,cpu_board_ships,Integer.parseInt(row),Integer.parseInt(column));
            print_boards(name_player,player_board_ships,player_board_missiles);
            
            // Play the CPU
            play_cpu(cpu_board_missiles,player_board_ships);
            print_boards("CPU",cpu_board_ships,cpu_board_missiles);
                    
		    menu_option = Menu(br);
		}
				
		else if(menu_option.equals("2")){
			System.out.println(p1.get_name()+" your score is: "+p1.get_record());
			menu_option = Menu(br);				
		}
		else{
		menu_option = Menu(br);
		}
		
	}
	
    }


}
