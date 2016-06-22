/*
 @name:         MatrixOps.java
 @authors:      Victor Rodriguez, Obed N Munoz 
 @description:  Matrix Operations class
*/

/** Class MatrixOps **/
public class MatrixOps
{
    public static int[][] ikjMultiply(int[][] A, int[][] B) {
	int n = A.length;
	int[][] C = new int[n][n];
	for (int i = 0; i < n; i++) {
	    for (int k = 0; k < n; k++) {
		for (int j = 0; j < n; j++) {
		    C[i][j] += A[i][k] * B[k][j];
		}
	    }
	}
	return C;
    }

    /** Main function **/
    public static void main (String[] args)
    {
	MatrixOps m = new MatrixOps();
	
	int N = 5;
	
	/** Generate two 2d matrices **/
	int[][] A = new int[N][N];
	for (int i = 0; i < N; i++)
	    for (int j = 0; j < N; j++)
		A[i][j] = i + j + 3;

	int[][] B = new int[N][N];
	for (int i = 0; i < N; i++)
	    for (int j = 0; j < N; j++)
		B[i][j] = i + j + 5;

	int[][] C = m.ikjMultiply(A, B);

	System.out.println("\nProduct of matrices A and  B : ");
	for (int i = 0; i < N; i++)
	    {
		for (int j = 0; j < N; j++)
		    System.out.format("%6d",C[i][j]);
		System.out.println();
	    }
    }
}
