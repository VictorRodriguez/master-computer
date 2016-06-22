import java.net.* ;
import java.io.*;

class Client extends Thread
{
   private final static int PACKETSIZE = 100 ;
   private DatagramSocket socket;
   private int port;
   private InetAddress host;

    public Client(InetAddress user_host , int user_port){
      this.host = user_host;
      this.port = user_port;
    }

   public void run(){
      try
      {

         socket = new DatagramSocket() ;
         
         byte [] data = "[Client] Hello Server, did you do the homework".getBytes() ;
         DatagramPacket packet = new DatagramPacket( data, data.length, this.host, this.port ) ;

         socket.send( packet ) ;

         System.out.println("[Client] Waiting for Server");
         socket.setSoTimeout( 2000 ) ;

         packet.setData( new byte[PACKETSIZE] ) ;

         socket.receive( packet ) ;

         System.out.println( new String(packet.getData()) ) ;
         
      }
      catch( Exception e )
      {
         System.out.println( e ) ;
      }
      finally
      {
         if( socket != null )
            socket.close() ;
      }
   }
}

class Server extends Thread
{
   private final static int PACKETSIZE = 100 ;
   private int port;
   private DatagramSocket socket; 

   public Server(int user_port ) throws IOException{
       
       this.port = user_port;
       socket = new DatagramSocket(port);
       socket.setSoTimeout(10000);
    }

    public void run(){

         System.out.println( "[Server] The server is ready..." ) ;

         while(true)
         {
            try{

            DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;

            socket.receive( packet ) ;

            System.out.println( packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()) ) ;

            socket.send( packet ) ;
            }  
             catch (SocketTimeoutException e) {
            
            System.out.println("Socket timed out!");
            break;

            }
            catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
}


public class UDP_peer extends Thread{

  public static void main(String[] args) throws Exception {
    InetAddress hostname = InetAddress.getByName("localhost");
    int port  = 5400;

    try
    {
        Server server = new Server(port);
        server.start();
        }
    catch (IOException e){
        Client client = new Client(hostname,port);
        client.start();
    }
    }

}
