import javax.swing.ImageIcon;

public class Naipe {
    
    public static final String[] valores={"As","2","3","4","5","6","7","8","9","10","Joker","Reina","Rey"};
    public static final String[] figuras={"Corazones","Diamantes","Picas","Treboles"};
            
    public ImageIcon imagen =new ImageIcon();
    public int valor;
    public int figura;

    public int getValor(){
        if (this.valor == 0){
            return 11;
        }else{
            return this.valor + 1;
        }
    }

    public String toString(){
        return valores[valor]+" de "+figuras[figura];
    }

    public ImageIcon getImage(){
            return imagen;
    }


}
