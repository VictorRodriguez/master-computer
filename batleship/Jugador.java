public class Jugador{

    private String name;
    private int record;

    public Jugador(String name){
        this.name = name; 
        this.record = 0;
    }

    public String get_name(){
        return this.name;
    }

    public int get_record(){
        return this.record;
    }

    public void set_name(String name){
        this.name = name;
    }

    public void set_record(int record){
        this.record = record;
    }

    public void declarewinner(){
        this.record ++;
    }

    public static void main(String[] args) {
        
        Jugador p = new Jugador("vladimir");
        String a = p.get_name();
        p.set_record(5);
        int c = p.get_record();
        p.declarewinner();
        
        c = p.get_record();
        System.out.println(a);
        System.out.println(c);
    }
    
}
