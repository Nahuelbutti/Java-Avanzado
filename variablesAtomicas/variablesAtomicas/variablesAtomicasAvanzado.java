package variablesAtomicas;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
 
 Practicando un poco mas con variables atomicas, Las variables atómicas se utilizan 
 para garantizar la consistencia en el acceso concurrente a variables compartidas, 
 evitando condiciones de carrera y errores de lectura/escritura parcial. 
 Proporcionan operaciones indivisibles y sin interferencias de otros hilos, lo que puede ayudar a 
 mantener la integridad de los datos compartidos en un entorno concurrente.
 
 */

public class variablesAtomicasAvanzado {

	// defino un stream map, que va a tener un string y un real, y lo trabajo con lambdas
    private static Map<String, Double> PreciosMiAerolinea;
    // este es el main, aca invoco los metodos necesarios para resolver
    public static void main(String[] args)  {
    	// metodo que carga en el mapa todos los precios de cada aerolinea
        inicializacion();
        
        String desde = "BCN";
        String hasta = "JFK";

        Double bajoPrecio = obtenerPrecioMasBajo(desde, hasta);
        Double promedioPrecio = obtenerPrecioPromedio(desde, hasta);

        System.out.println("El precio mas bajo es: " + bajoPrecio);
        System.out.println("El precio promedio es: " + promedioPrecio);
    }
    
    // metodo que calcula el precio mas bajo
    private static Double obtenerPrecioMasBajo (String desde, String hasta) {
    	// varible atomica (en todas las variables atomicas para obtener un valor hay que hacer .get())
        AtomicReference<Double> bajoPrecio = new AtomicReference<>(null);
        // PreciosMiAerolinea: obtiene el precio pero para que pueda obtenerlo debemos decirle para quien
        // keySet: obtiene la primera parte de los strings de aerolineas, es decir los nombres
        // stream: secuencia de elementos
        // parallel: procesamiento paralelo, multihilo
        // forEach: recorre todas las aerolineas buscando lo que necesitemos
        PreciosMiAerolinea.keySet().stream().parallel().forEach(airline -> {
            Double precio = obtenerPrecioViaje(airline, desde, hasta);
            if (bajoPrecio.get() == null || precio < bajoPrecio.get()) {
            	bajoPrecio.set(precio);
            }
        });
        return bajoPrecio.get(); // obtenemos el precio mas bajo y lo retornamos
    }
    
    // metodo que calcula el precio promedio
    private static Double obtenerPrecioPromedio (String desde, String hasta) {
    	// variable atomica
        AtomicReference<Double> totalPrecios = new AtomicReference<>(0.0);
        
        PreciosMiAerolinea.keySet().stream().parallel().forEach(airline -> {
            Double precio = obtenerPrecioViaje(airline, desde, hasta);
            Double resultado = totalPrecios.get() + precio;
            totalPrecios.set(resultado);
        });

        int countAirlines = PreciosMiAerolinea.keySet().size();
        return totalPrecios.get() / countAirlines;
    }
    
    // metodo que inicializa todos los datos
    private static void inicializacion() {
    	// un hashmap es como una cadena de valores que va a tener un codigo asignado para cada valor
    	// por lo que es mas eficiente trabajar con los datos
    	PreciosMiAerolinea = new HashMap<>();
    	PreciosMiAerolinea.put("American Airlines", 550.0);
    	PreciosMiAerolinea.put("US Airways", 610.0);
    	PreciosMiAerolinea.put("Delta Airlines", 540.0);
    	PreciosMiAerolinea.put("Singapore Airlines", 612.0);
    	PreciosMiAerolinea.put("Qatar Airways", 590.0);
    	PreciosMiAerolinea.put("Cathay Pacific Airways", 585.0);
    	PreciosMiAerolinea.put("Sky Airline", 540.0);
    	PreciosMiAerolinea.put("Copa Airlines Colombia", 610.0);
    	PreciosMiAerolinea.put("Avianca", 580.0);
    	PreciosMiAerolinea.put("LATAM Airlines Group", 600.0);
    	PreciosMiAerolinea.put("Aeroméxico", 740.0);
    	PreciosMiAerolinea.put("Aerolíneas Argentinas", 940.0);
    }
    
    // metodo que devuelve precios
    private static Double obtenerPrecioViaje (String airline, String desde, String hasta) {
    	// si quiero obtener el precio de una aerolinea, le paso el nombre de esa aerolinea 
    	// y nos devuelve el precio
        return PreciosMiAerolinea.get(airline);
    }
}