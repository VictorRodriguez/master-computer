import java.io.*;
import java.net.* ;

public class Client
{
   private final static int PACKETSIZE = 100 ;
   public static void main( String args[] ){

    String user_data = null;

      DatagramSocket socket = null ;

      while(true)
      {
      try
      {
        InetAddress host =  InetAddress.getByName("localhost");
        int port         = 5400;

         socket = new DatagramSocket() ;

        Console console = System.console();
        user_data = console.readLine("Enter input:");


         byte [] data = user_data.getBytes() ;
         DatagramPacket packet = new DatagramPacket( data, data.length, host, port ) ;

         socket.send( packet ) ;
         socket.setSoTimeout( 2000 ) ;

         packet.setData( new byte[PACKETSIZE] ) ;

         socket.receive( packet ) ;

         System.out.println( new String(packet.getData()) ) ;

      }
      catch( Exception e )
      {  System.out.println( e ) ;
      }
      finally
      {
         if( socket != null )
            socket.close() ;
      }
    }
   }
}
