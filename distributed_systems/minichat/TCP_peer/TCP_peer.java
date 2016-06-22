// File Name GreetingServer.java

import java.net.*;
import java.io.*;

class GreetingClient{

    public GreetingClient(String serverName, int port){
      try
      {
         System.out.println("[Client] Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("[Client] Just connected to "
                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =
                       new DataOutputStream(outToServer);

         out.writeUTF("[Client] Hey did you do the homewrok? "
                      + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
         System.out.println("[Client] Server says " + in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}

class Server extends Thread
{
   private ServerSocket serverSocket;
   
   public Server(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("[Server] Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("[Server] Just connected to "
                  + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            out.writeUTF("[Server] Which homework ??? :(  "
              + server.getLocalSocketAddress() + "\n [Server] Bye!!!");
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
}
   
public class TCP_peer extends Thread
{
   public static void main(String [] args)
   {
        String serverName = "localhost";
        int port =  5400;
      try
      {
         Thread t = new Server(port);
         t.start();
      }catch(IOException e)
      {
        GreetingClient client = new GreetingClient(serverName,port);
        System.exit(0);
      }

   }
}
