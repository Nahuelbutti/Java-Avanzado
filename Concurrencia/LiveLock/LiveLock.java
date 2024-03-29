package LiveLock;
import java.util.concurrent.*;

/*
 
	Un LiveLock es similar a un DeadLock, pero en lugar de estar bloqueados, los hilos están activos
y continúan realizando acciones repetitivas sin hacer progreso.
	En un LiveLock, los hilos están constantemente cambiando su estado o comportamiento en respuesta
a las acciones de otros hilos, pero no logran avanzar hacia su objetivo.
	A diferencia de un DeadLock, en un LiveLock los hilos no están esperando pasivamente, 
sino que están ocupados realizando acciones inútiles.
	Un LiveLock puede ocurrir cuando los hilos están intentando evitar un DeadLock pero terminan en un estado de interacción no productiva.
 
 */

public class LiveLock {
    public static void main(String[] args) {
// Cracion de participantes y recursos
        Fox robin = new Fox();
        Fox miki = new Fox();
        Comida comida = new Comida();
        Agua agua = new Agua();
// Procesamiento de datos, dos hilos, uno robin y el otro miki
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
        synchronized(comida) {
            System.out.println("Tengo comida!");
            mover();
        }
        aguaYcomida(comida, agua);
    }
    public void aguaYcomida(Comida comida, Agua agua) {
        synchronized(agua) {
            System.out.println("Tengo agua!");
            mover();
        }
        comidaYagua(comida, agua);
    }
    public void mover() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
// Handle exception
        }
    }
}
