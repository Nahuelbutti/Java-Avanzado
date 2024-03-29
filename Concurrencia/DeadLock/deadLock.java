package DeadLock;
import java.util.concurrent.*;

/*
 * 
 	Un DeadLock ocurre cuando dos o más hilos están bloqueados indefinidamente, 
esperando mutuamente por recursos que están siendo retenidos por otros hilos.
	En un DeadLock, los hilos quedan atrapados en un estado de espera circular, 
donde cada hilo espera que otro libere el recurso que necesita para continuar.
 	Como resultado, los hilos quedan bloqueados y el programa no puede avanzar, 
 	lo que lleva a una situación de estancamiento.
 	
*/

public class deadLock {
    public static void main(String[] args) {
// Creacion de participantes y recursos
        Fox robin = new Fox();
        Fox miki = new Fox();
        Comida comida = new Comida();
        Agua agua = new Agua();
// Procesamiento de datos con dos hilos de procesamiento
        ExecutorService service = null;
        try {
            service = Executors.newScheduledThreadPool(10);
            service.submit(() -> robin.comidaYagua(comida,agua));
            service.submit(() -> miki.aguaYcomida(comida,agua));
        } finally {
            if(service != null) service.shutdown();
        }
    }
}

class Comida {}

class Agua {}

class Fox {
	
    public void comidaYagua(Comida comida, Agua agua) {
        synchronized(comida) { // bloquea el recurso comida
            System.out.println("Robin: tengo deadlock.comida!");
            mover();
            synchronized(agua) {
                System.out.println("Robin: tengo deadlock.agua!");
            }
        }
    }
    
    public void aguaYcomida(Comida comida, Agua agua) {
        synchronized(agua) { // bloquea el recurso agua
            System.out.println("Miki: tengo deadlock.agua!");
            mover();
            synchronized(comida) {
                System.out.println("Miki: tengo deadlock.comida!");
            }
        }
    }
    
    public void mover() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
// Handle exception
        }
    }
    
}