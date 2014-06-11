package bg.uni.sofia.rsa.matrix.determinant;

import java.util.concurrent.Callable;

public class SingleSum implements Callable<Double> {

	private SparseMatrix<Float> matrix;
	private int n;

	public SingleSum(SparseMatrix<Float> matrix, int n) {
		this.matrix = matrix;
		this.n = n;
	}

	/**
	 * Calculate the determinant of given matrix 
	 * @param matrix
	 * @param order
	 * @return Double determinant
	 */
	public static <T> double determinentSingle(SparseMatrix<Float> matrix, int order) {
		SparseMatrix<Float> matrix_buf = new SparseMatrix<Float>(order, order, 0.0F);

		int i, j, k;
		double sum = 0;
		if (order == 1) {
			sum = (Float) matrix.getValueAt(0, 0);
		} else if (order == 2) {
			sum = ((matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix
					.getValueAt(0, 1) * matrix.getValueAt(1, 0)));
		} else {
			for (int p = 0; p < order; p++) {
				int h = 0;
				k = 0;
				for (i = 1; i < order; i++) {
					for (j = 0; j < order; j++) {
						if (j == p)
							continue;
						matrix_buf.setValueAt(h, k, matrix.getValueAt(i, j));// [h][k]=b[i][j];
						k++;
						if (k == order - 1) {
							h++;
							k = 0;
						}
					}
				}
				
				sum = sum + matrix.getValueAt(0, p) * Math.pow((-1), p)
						* determinentSingle(matrix_buf, order - 1);
			}
		}
		return sum;
	}

	@Override
	public Double call() throws Exception {
		// determinentSingle(matrix, n);
		// Result res = determinentSingle(matrix, n);
		return determinentSingle(matrix, n);
	}
}
