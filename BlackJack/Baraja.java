import java.util.Random;
import java.io.BufferedReader;
import javax.swing.ImageIcon;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Baraja {

	public static final String[] valores={"As","2","3","4","5","6","7","8","9","10","Joker","Reina","Rey"};
	public static final String[] figuras={"Corazones","Diamantes","Picas","Treboles"};
	public static final ImageIcon[] naipesImg=new ImageIcon[52];

	private Random rnd = new Random();
    public String BARAJA_FILE = "baraja.txt";

    private int figura;
	private int valor;

	private int pos_naipe;
	private String path;

    public Baraja(){

        set_valor();
        set_figura();

        pos_naipe = valor + (figura * 13);

        //  If file does not exist create it
        File f = new File(BARAJA_FILE);
        if(f.exists()== false){
            gen_naipe_file();
        }

        // Load the file into the naipeImg array
        read_baraja();

    }

    public Naipe give_card(){

        Naipe carta = new Naipe();

        pos_naipe += 1;

        this.figura = pos_naipe/13;
        this.valor = pos_naipe%13;

        carta.valor = this.valor;
        carta.figura = this.figura;
        carta.imagen = getImage();

		// Delete the card you chose
        delete_card();

        return carta;
    }

    public void mix(){
       set_figura();
       set_valor();
    }
    public void set_figura(){
        this.figura=(int)(rnd.nextDouble()*4+0);
    }

    public void set_valor(){
	    this.valor=(int)(rnd.nextDouble()*13+0);
    }

    public int get_pos(){
        return pos_naipe;
    }
    private void delete_card(){

		try {

			String path="Blackjack/Cartas/" + figuras[figura] + "/" + (valor + 1) + ".png";

        	File file = new File(BARAJA_FILE);

			File tempFile = new File("myTempFile.txt");

			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				if(currentLine.contains(path)){
				 	continue;
				}
				else{
					writer.write(currentLine + "\n");
				}
			}
			writer.close();
			reader.close();
			boolean success = tempFile.renameTo(file);

		} catch (IOException e) {
            e.printStackTrace();
   		}
    }
    private void read_baraja(){

		BufferedReader br;
        String curline;
        String[] line_split;
        String path;
        int pos;

        try {
            br = new BufferedReader(new FileReader(BARAJA_FILE));
            while ((curline = br.readLine()) != null) {
               line_split = curline.split(",");
               pos = Integer.parseInt(line_split[0]);
               path = line_split[1];
               naipesImg[pos]= new ImageIcon(path);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void gen_naipe_file(){

        String path = null;
        int pos = 0;
		try {
            PrintWriter writer = new PrintWriter(BARAJA_FILE, "UTF-8");
		    for (int valor = 0; valor< valores.length; valor++){
		        for (int figura = 0; figura < figuras.length;figura++){
		            path="Blackjack/Cartas/" + figuras[figura] + "/" + (valor + 1) + ".png";
		            pos = valor + (figura * 13);
 		            writer.println(pos + "," + path);
		        }
		    }

		    writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

    }
	public ImageIcon getImage(){
		if (naipesImg[pos_naipe] != null){
			return naipesImg[pos_naipe];
		}
		else{
			System.out.println("Esta carta ya habia pasado");
			return null;
		}
	}

	public static void main(String[] args) {

        Baraja baraja = new Baraja();
        
        Naipe naipe = baraja.give_card();
		System.out.println(naipe.toString());
        System.out.println(naipe.getImage());

	}
}
