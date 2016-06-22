import java.util.Scanner;


public class BlackJack{

	public static void main(String[] args) {

        Baraja baraja = new Baraja();

        // Ask the number of players
        Scanner user_input = new Scanner(System.in);
        
        System.out.println("Number of players");
        int number_players = Integer.parseInt(user_input.next());

        
        
        // Ask the apuesta
        System.out.println("Apuesta");
        int apuesta = Integer.parseInt(user_input.next());
        
        
        if (number_players > 4){
            System.out.println("Too many players");
            number_players = 0;
            System.exit(-1);
        }

        // Create the players
        Jugador[] players = new Jugador[number_players];
        for ( int cont = 0 ; cont < players.length ; cont ++){
            players[cont] = new Jugador();
        }


       // Ask the name of players
        for (int cont = 0 ; cont < players.length; cont ++ ){
            System.out.println("Name of Player : " + cont);
            players[cont].set_nombre(user_input.next());

            System.out.println("Saldo inicial: " + cont);
            players[cont].set_saldo(Integer.parseInt(user_input.next()));
        }

        //Give cards
        for (int cont = 0 ; cont < players.length; cont ++ ){
            System.out.println();
            System.out.println("Cards of player " + players[cont].get_nombre());
            System.out.println();
            for (int p_cont = 0; p_cont < players[cont].juego.length; p_cont++){
                Naipe carta = baraja.give_card();
                System.out.println("Carta:  " +  carta.toString());
                System.out.println("Imagen: " + carta.getImage());
                System.out.println("Valor : " + carta.getValor());

                players[cont].juego[p_cont] = carta;
            }

            System.out.println("Total = " + Integer.toString(players[cont].getTotal()));
        }
        
    }
}
