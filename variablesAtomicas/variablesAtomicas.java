package variablesAtomicas;

import java.util.concurrent.atomic.AtomicInteger;

/*
 
 Las variables atomicas sirven para evitar el problema que tenemos en la concurrencia y es que dos procesos
 ejecutados al mismo tiempo modifiquen el valor de una variable, con la variable atomica evitamos esa
 situacion porque bloquea los recursos cuando los esta usando
 
 por otro lado tambien tenemos .join() que sirve para que un proceso no salga de ejecucion hasta que termine
 
 */

public class variablesAtomicas {

	public static void main(String[] args) throws InterruptedException {
		
		// clase  variable      clase
		Contador contador = new Contador();
		// definimos dos hilos de procesamiento
		Thread principal = new Thread (contador, "principal");
		Thread secundario = new Thread (contador, "secundario");
		// comenzamos cada hilo de procesamiento
		principal.start();
		secundario.start();
		// con join prohibimos que "secundario" se ejecute si no termino su proceso actual "principal"
		principal.join();
		secundario.join();
		
		System.out.println(contador.cont);
		
	}
	
	static class Contador extends Thread{
		public AtomicInteger cont = new AtomicInteger(0); // esta inicializado en 0
	
		public void run() {
			for (int i=0; i<100_000_000; i++) {
				cont.addAndGet(1);
				// podriamos hacer cont++? no no podemos porque la variable tiene que ser atomica cuando
				// trabajas con concurrrencia
			}
		}
	}

}

//aca me quede 1:02:39 / 5:38:14