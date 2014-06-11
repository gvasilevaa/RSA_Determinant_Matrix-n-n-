package bg.uni.sofia.rsa.matrix.determinant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecuteThreads {

	private SparseMatrix<Float> matrix;
	private ExecutorService executor;
	private int order;

	public ExecuteThreads(int nThreads, SparseMatrix<Float> matrix, int order)
			throws InterruptedException, ExecutionException {
		this.matrix = matrix;
		this.order = order;
		this.executor = Executors.newFixedThreadPool(nThreads);

		//Create Collection of task to execute later
		Collection<SingleSum> tasks = new ArrayList<SingleSum>();
		if (nThreads == 1) {
			tasks.add(new SingleSum(matrix, order));
		} else {
			for (int i = 0; i < nThreads; i++) {
				
			}
		}
		
		long start = System.currentTimeMillis();
		//Execute all Callables
		List<Future<Double>> results = executor.invokeAll(tasks, 10,
				TimeUnit.SECONDS);
		
		Double sum = 0.0;
		//Collect all results 
		for (Future<Double> f : results) {
			sum += f.get();
			System.out.println("Sum " + f + ": " + sum);

		}
		long end = System.currentTimeMillis();
		System.out.println("Time:" + (end-start));
	}

}
