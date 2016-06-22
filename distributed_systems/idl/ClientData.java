import java.util.*;

class ClientData {

    private int id = 0;
    private int n = 0; 
    private int[][] matrix = new int[0][0];
    
    public ClientData(){
    }
    
    public ClientData(int[] data){
	this.id = data[0];
	this.n = data[1];
	matrix = buildMatrix(data);
    }

    private int[][] buildMatrix(int[] data){
        int[][] newMatrix = new int[n][n];

	for (int i=0; i<n; i++)
	    for (int j=0; j<n; j++)
		newMatrix[i][j] = data[2 + i*n + j];
		    
	return newMatrix;
    }

    public int getId(){
	return id;
    }

    public int[][] getMatrix(){
	return matrix;
    }

    @Override
    public String toString(){
	String data = "--> Client: "+ id +
	    "\n--> N Size: " + n;
	
	for (int[] row : matrix)
	    data += "\n "+Arrays.toString(row);
	return data;
    }
}
