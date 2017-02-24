import java.util.List;

public class Event implements Runnable {

	String type;
	int n;
	List<Integer> primes, squares, facts, fibos;
	
	Event(String type, int n, List<Integer> primes, List<Integer> squares, List<Integer> facts, List<Integer> fibos) {
		this.type = type;
		this.n = n;
		this.primes = primes;
		this.fibos = fibos;
		this.facts = facts;
		this.squares = squares;
	}
	
	public void run() {
		//in functie de tipul evenimentului se executa functia corespunzatoare
		switch(type) {
		case "FIB":
			fib();
			break;
		case "SQUARE":
			square();
			break;
		case "PRIME":
			prime();
			break;
		case "FACT":
			fact();
			break;
		default:
			System.out.println("default");
		}
	}

	private void fact() {
		int max = 0;
		for(int i=1;i<n;i++) {
			if(getFact(i)>n) {
				break;
			}
			max = i;
		}
		facts.add(max);	
	}

	private void prime() {
		for(int i=n;i>2;i--) {
			if(isPrime(i)) {
				primes.add(i);
				return;
			}
		}
		primes.add(2);
	}

	private void square() {
		int max = 0;
		for(int i=1;i<n/2;i++) {
			if(Math.pow(i, 2)>n) { 
				break;
			}
			max = i;
		}
		squares.add(max);
	}
	
	private void fib() {
		int a = 0, b = 1, aux = 1, max=0;
		for(int i=2;i<n;i++) {
			if(a+b>n) break;
			aux = a+b;
			a=b;
			b=aux;
			max = i;
		}
		fibos.add(max);
	}
	
	//verifica daca un numar este prim
	private boolean isPrime(int x) {
		for(int i=2;i<=x/2;i++) {
			if(x%i == 0) return false;
		}
		return true;
	}
	
	//calculeaza factorialul unui numar
	private int getFact(int n) {
		int fact = 1;
		for(int i=2;i<=n;i++) {
			fact *= i;
		}
		return fact;
	}
}
