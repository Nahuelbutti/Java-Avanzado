package Starvation;

import java.util.concurrent.*;

/*

  Starvation (inanición): es otro problema relacionado con la concurrencia en programación 
concurrente y multihilo. Se produce cuando un hilo o proceso queda privado de los recursos 
necesarios para realizar su trabajo, lo que resulta en una ejecución prolongada o indefinida 
del mismo sin poder avanzar.
	A diferencia de un DeadLock o LiveLock, donde los hilos están interactuando entre sí, 
en el caso de la starvation, un hilo en particular no puede obtener acceso a los recursos que 
necesita debido a la forma en que se gestionan los recursos compartidos.
   
*/

public class Starvation {
    public static void main(String[] args) {
// Creacion de participantes y recursos
        Fox robin = new Fox();
        Fox miki = new Fox();
        Elefante dumbo = new Elefante();
        Comida comida = new Comida();
// Procesamiento de datos
        ExecutorService service = null;
        try {
            service = Executors.newScheduledThreadPool(10);
            service.submit(() -> dumbo.comer(comida));
            service.submit(() -> robin.comer(comida));
            service.submit(() -> miki.comer(comida));
        } finally {
            if(service != null) service.shutdown();
        }
    }
}

class Comida {}
// el elefante esta junto al plato de comida por lo que no tarda en llegar y ademas esta 60 segundos
// comiendo, por ello la funcion thread.sleep, dormir proceso 
class Elefante {
    public void comer(Comida comida) {
        synchronized(comida) {
            System.out.println("El elefante tiene comida!");
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// los zorros tienen que moverse hasta el plato de comida por lo que tardan mas en llegar que
// el elefante que ya esta ahi junto al plato de comida, por lo que como bloquea primero el elefante 
// los zorros solo pueden comer cuando el elefante termine, si el elefante no dejara de comer 
// nunca podrian comer los zorros (a este fenomeno se lo denomina starvation)
class Fox {
    public void comer(Comida comida) {
        mover();
        synchronized(comida) {
            System.out.println("Un fox tiene comida!");
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
