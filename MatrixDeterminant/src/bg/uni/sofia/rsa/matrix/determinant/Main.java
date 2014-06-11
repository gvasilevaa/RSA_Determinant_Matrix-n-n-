package bg.uni.sofia.rsa.matrix.determinant;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws NumberFormatException,
			IOException, InterruptedException, ExecutionException {
		//Get argument for matrix order [Run Configuration -> Arguments -> 2 primerno]
		int n = Integer.parseInt(args[0]);

		testMatrix(n);
	}

	/**
	 * 
	 * @param n
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void testMatrix(int n) throws NumberFormatException,
			IOException, InterruptedException, ExecutionException {
		//Create SparseMatrix (n,n)
		SparseMatrix<Float> matrix = new SparseMatrix<Float>(n, n, 0.0F);
		//Populate matrix
		populateMatrix(matrix);
		//check determinant
		Double detA = determinent(matrix, n);
		System.out.println("DetA = " + detA);

		//Create ExecutorService
		ExecuteThreads executor = new ExecuteThreads(1, matrix, n);
	}

	/**
	 * Populate matrix with random doubles
	 * @param matrix
	 */
	public static void populateMatrix(SparseMatrix<Float> matrix) {
		Random rnd = new Random();

		for (int i = 0; i < matrix.returnSize(); i++) {
			for (int j = 0; j < matrix.returnSize(); j++) {
				matrix.setValueAt(i, j, rnd.nextFloat());
				System.out.println("matrix : " + matrix.getValueAt(i, j));
			}

		}
	}
	
	/**
	 * Recursive function for checking the real determinant 
	 * @param matrix
	 * @param m :  matrix order
	 * @return Double 
	 */
	public static <T> double determinent(SparseMatrix<Float> matrix, int m) {
		SparseMatrix<Float> matrix_buf = new SparseMatrix<Float>(m, m, 0.0F);

		int i, j, k;
		double sum = 0;
		if (m == 1) {
			sum = (Float) matrix.getValueAt(0, 0);
		} else if (m == 2) {
			sum = ((matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix
					.getValueAt(0, 1) * matrix.getValueAt(1, 0)));
		} else {
			for (int p = 0; p < m; p++) {
				int h = 0;
				k = 0;
				for (i = 1; i < m; i++) {
					for (j = 0; j < m; j++) {
						if (j == p)
							continue;
						matrix_buf.setValueAt(h, k, matrix.getValueAt(i, j));// [h][k]=b[i][j];
						k++;
						if (k == m - 1) {
							h++;
							k = 0;
						}
					}
				}
				sum = sum + matrix.getValueAt(0, p) * Math.pow((-1), p)
						* determinent(matrix_buf, m - 1);
			}
		}
		return sum;
	}
//
//	public static <T> double determinentSingle(SparseMatrix<Float> matrix, int m) {
//		SparseMatrix<Float> matrix_buf = new SparseMatrix<Float>(m, m, 0.0F);
//
//		int i, j, k;
//		double sum = 0;
//		if (m == 1) {
//			sum = (Float) matrix.getValueAt(0, 0);
//		} else if (m == 2) {
//			sum = ((matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix
//					.getValueAt(0, 1) * matrix.getValueAt(1, 0)));
//		} else {
//			for (int p = 0; p < m; p++) {
//				// this in new method to
//				int h = 0;
//				k = 0;
//				for (i = 1; i < m; i++) {
//					for (j = 0; j < m; j++) {
//						if (j == p)
//							continue;
//						matrix_buf.setValueAt(h, k, matrix.getValueAt(i, j));// [h][k]=b[i][j];
//						k++;
//						if (k == m - 1) {
//							h++;
//							k = 0;
//						}
//					}
//				}
//				// sum= matrix.getValueAt(0, p) * Math.pow((-1), p)
//				sum = sum + matrix.getValueAt(0, p) * Math.pow((-1), p)
//						* determinent(matrix_buf, m - 1);
//			}
//		}
//		return sum;
//	}
//
//	public double determinant(double A[][], int N) {
//
//		double det = 0;
//		double res;
//
//		if (N == 1)
//			res = A[0][0];
//
//		else if (N == 2) {
//			res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
//		}
//
//		else {
//			res = 0;
//			for (int j1 = 0; j1 < N; j1++) {
//				
//				double[][] m = new double[N - 1][];
//				//sumSingle s thread t 
//				// 
//				for (int k = 0; k < (N - 1); k++)
//					m[k] = new double[N - 1];
//				for (int i = 1; i < N; i++) {
//					int j2 = 0;
//					for (int j = 0; j < N; j++) {
//						if (j == j1)
//							continue;
//						m[i - 1][j2] = A[i][j];
//						j2++;
//					}
//				}
//				res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1]
//						* determinant(m, N - 1);
//
//			}
//
//		}
//		return res;
//	}

}
