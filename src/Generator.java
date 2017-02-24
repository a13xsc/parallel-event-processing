import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Generator extends Thread {
	
	String path;
	ArrayBlockingQueue<Event> abq;
	List<Integer> primes, squares, facts, fibos;
	
	Generator(String path, ArrayBlockingQueue<Event> abq, List<Integer> primes, List<Integer> squares, List<Integer> facts, List<Integer> fibos) {
		this.path = path;
		this.abq = abq;
		this.primes = primes;
		this.facts = facts;
		this.fibos = fibos;
		this.squares = squares;
	}
	public void run() {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			//citirea din fisier
			for(String line; (line = br.readLine()) != null; ) {
		    	String[] parts = line.split(",");
		    	
		    	try {
					Thread.sleep(Integer.parseInt(parts[0]));
				} catch (NumberFormatException | InterruptedException e) {
					e.printStackTrace();
				}
		    	
		    	try {
		    		//adaugarea in coada de evenimente
					abq.put(new Event(parts[1], Integer.parseInt(parts[2]), primes, squares, facts, fibos));
				} catch (NumberFormatException | InterruptedException e) {
					e.printStackTrace();
				}
		    }
		    br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
