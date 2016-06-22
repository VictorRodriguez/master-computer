import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.util.concurrent.Callable;


public class Client {

    BufferedReader in;
    PrintWriter out;

    private static int port = 9001;
    private String name; 
    private String server_address;

    public Client(String client_name ) {
    
        this.name = client_name;
        this.server_address = "localhost";

    }

    private void run() throws IOException{
        boolean com_flag = false;

        Socket socket = new Socket(this.server_address,port);

        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("CLIENT_NAME")) {
                out.println(this.name);
            } else if (line.startsWith("CLIENT_ACCEPTED")) {
                System.out.println("We got accepted");
                com_flag = true;
            } else if (line.startsWith("MESSAGE")) {
                System.out.println(line + "\n");
            }
        
        if (com_flag){
        
        Console console = System.console();
        String user_data = console.readLine("Enter input:");
        out.println(user_data);
        }

        }
    }

    public static void main(String[] args) throws Exception{
        String client_name = args[0];
        Client client = new Client(client_name);
        client.run();
    }
}
