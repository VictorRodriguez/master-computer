import java.net.* ;

public class Server
{
   private final static int PACKETSIZE = 100 ;
   public static void main( String args[] )
   {

      try
      {
         int port = 5400 ;
         DatagramSocket socket = new DatagramSocket( port ) ;
         System.out.println( "The server is ready..." ) ;


         while(true) 
         {
            DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;

            socket.receive( packet ) ;

            System.out.println( packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()) ) ;
            
        }  
     }
     catch( Exception e )
     {
        System.out.println( e ) ;
     }
  }
}
