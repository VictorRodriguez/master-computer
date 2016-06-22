import java.rmi.Naming;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class MyThread implements Runnable {
   static int[] array= {0}; 
   
   public MyThread(int[] array) {
        this.array = array;
   }
   
   public void run_clients_on_shell( ) throws java.io.IOException, java.lang.InterruptedException {
                // Get runtime
                java.lang.Runtime rt = java.lang.Runtime.getRuntime();
                
                
                String array_string = "";
                 for (int i=0; i<this.array.length; i++){
                    array_string += this.array[i] + ",";
                }
                // Start a new process:  java Client '4 3 4 5 '
                System.out.println("[Server] ");
                String command = "java Client " + '"' + array_string + '"' ;
                System.out.println(command);
                
                
                java.lang.Process p = rt.exec(command);
                
                // You can or maybe should wait for the process to complete
                p.waitFor();
                System.out.println("[Server] ");
                System.out.println("Process exited with code = " + p.exitValue());
                // Get process' output: its InputStream
                java.io.InputStream is = p.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
                
                // And print each line
                String s = null;
                System.out.println("[Server] Final Array : ");
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                }
                is.close();
            }  
    
   public void run() {
   
   try {
        run_clients_on_shell();
   } catch (Exception e) {
        System.out.println("[Server] ");
	System.err.println("Error");
	System.exit(1);
   }
     
   }
}


// BEGIN main
public class Server{

	public static void main(String[] args) {


                int array_size = 1; 
                int number_clients = 1;
                
                if (args.length > 0) {
                    try {
                        array_size = Integer.parseInt(args[0]);
                        number_clients = Integer.parseInt(args[1]);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("Arguments must be an integer.");
                        System.exit(1);
                    }
                }
                
                else{
                        System.out.println("You are missing the number of clients and array size");
                        System.out.println("<number of clients> <array size>");
                }


                //Create random array
                System.out.println("[Server] ");
                System.out.println("[Server]: Random Array: ");
                int[] array = new int[array_size];
                for (int i=0; i<array_size; i++){
                    int n = (int)(Math.random()*9 + 1);
                            array[i] = n;
                            System.out.print(array[i] + " ");
                }
                
                 System.out.println(" ");



		try {
			// Create an instance of the server object
			RemoteSortImpl im = new RemoteSortImpl();

			System.out.println("SortServer starting...");

			// Publish it in the RMI registry.
			// Of course you have to have rmiregistry or equivalent running!
			Naming.rebind(RemoteSortImpl.LOOKUPNAME, im);

			System.out.println("SortServer ready.");
			
                	for (int count = 0; count < number_clients; count ++){
                	        Runnable r = new MyThread(array);
                                new Thread(r).start();
			}
			
		} catch (Exception e) {
			System.err.println("Error");
			System.exit(1);
		}
	}
}
// END main
