import java.io.*;
import java.net.*;
import java.util.HashSet;

public class Server {

    private static int port = 9001;
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();


    private static class Handler extends Thread {
        private Socket socket;
        private String client_name;
        private PrintWriter out;
        private BufferedReader in;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("CLIENT_NAME");
                    client_name = in.readLine();
                    if (client_name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(client_name)) {
                            names.add(client_name);
                            break;
                        }
                    }
                }

                out.println("CLIENT_ACCEPTED");
                writers.add(out);

                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + client_name + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (client_name != null) {
                    names.remove(client_name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Server error ...");
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println("The Server is ready....");
        ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

}
