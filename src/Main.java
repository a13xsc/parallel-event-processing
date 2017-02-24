import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		Generator[] generators = new Generator[args.length-2];
		ExecutorService tpe = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		ArrayBlockingQueue<Event> abq = new ArrayBlockingQueue<Event>(Integer.parseInt(args[0]));
		int tasks = Integer.parseInt(args[1]) * (args.length-2);
		Event event;
		
		//4 liste pentru rezultatele finale
		List<Integer> primes = Collections.synchronizedList(new ArrayList<Integer>());
		List<Integer> squares = Collections.synchronizedList(new ArrayList<Integer>());
		List<Integer> facts = Collections.synchronizedList(new ArrayList<Integer>());
		List<Integer> fibos = Collections.synchronizedList(new ArrayList<Integer>());
		
		for(int i=2;i<args.length;i++) {
			generators[i-2] = new Generator(args[i], abq, primes, squares, facts, fibos);
			generators[i-2].start();
		}
		//cat timp nu s-au executat toate task-urile din fisiere, ia un task din coada (daca exista) si da-l spre executie
		while(tasks != 0) {
			try {
				event = abq.take();
				tpe.submit(event);
				tasks--;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		tpe.shutdown();
		
		try {
			tpe.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//sortarea rezultatelor
		Collections.sort(primes);
		Collections.sort(squares);
		Collections.sort(fibos);
		Collections.sort(facts);
		
		//scrierea in fisiere
		try{
		    PrintWriter writer = new PrintWriter("PRIME.out", "UTF-8");
		    for(int i=0;i<primes.size();i++) {
		    	writer.println(primes.get(i));
		    }
		    writer.close();
		    
		    writer = new PrintWriter("SQUARE.out", "UTF-8");
		    for(int i=0;i<squares.size();i++) {
		    	writer.println(squares.get(i));
		    }
		    writer.close();
		    
		    writer = new PrintWriter("FACT.out", "UTF-8");
		    for(int i=0;i<facts.size();i++) {
		    	writer.println(facts.get(i));
		    }
		    writer.close();
		    
		    writer = new PrintWriter("FIB.out", "UTF-8");
		    for(int i=0;i<fibos.size();i++) {
		    	writer.println(fibos.get(i));
		    }
		    writer.close();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
}
