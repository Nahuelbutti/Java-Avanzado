package PracticandoWebScraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*

webScraper sirve para poder analizar el contenido de paginas web, 
basicamente es como un robot que analiza y puede descargar o guardar
cierto contenido de las paginas web

*/

public class MainWebScraper {

	public static void main(String[] args) throws IOException {
		
		List<String> links = new ArrayList<>(); // creamos stream de links
		
		// añadimos las webs a los "links"
		
		links.add("https://www.bbc.com/");
		links.add("https://www.google.com.ar/");
		links.add("https://www.mercadolibre.com.ar/");
		links.add("https://www.resinformatica.com.ar/");
		links.add("https://www.youtube.com/");
		links.add("https://twitter.com/home");
		
		// probando visitar las webs sin concurrencia (no multihilo)
		long timeStart = System.nanoTime(); // momento exacto en el que comienza el proceso
		links.stream().forEach(link -> getContenidoWeb(link));
		long timeEnd = System.nanoTime(); // momento exacto en el que termina el proceso
		System.out.println("Sin concurrencia tiempo de ejecucion de la linea 39: " + (timeEnd - timeStart));
		
		// probando visitar webs con concurrencia 
		timeStart = System.nanoTime();
		links.stream().parallel().forEach(link -> getContenidoWeb(link));
		timeEnd = System.nanoTime();
		System.out.println("Con concurrencia tiempo de ejecucion de la linea 39: " + (timeEnd - timeStart));
		
        
	}
	/* 
	 
	 	Lo que hace el synchronized es que el metodo se ejecute nuevamente una vez haya terminado, porque gracias
	 a la concurrencia se puede ejecutar de manera desordenada y con el synchronized lo ordenamos.
	 	Claramente en este caso no tiene sentido pq al hacerlo sincronico se arruina el bajo tiempo de ejecion 
	 pero sirve en otros casos como guardar en una base de datos. Por ejemplo, en el metodo 
	 getContenidoWeb (String link) no usamos el synchronized y recorremos de manera concurrente, pero
	 a la hora de guardar los datos en el servidor lo hacemos de manera synchronized agregandole dicho
	 termino al metodo de guardar en el servidor.
	
	*/
	
	private synchronized static String getContenidoWeb (String link){
		// inicializo resultado antes pq sino en el catch tiene basura
        String resultado = "vacio";
		try {
			// nos guardamos la url
	        URL url = new URL (link);
			// ahora establecemos la conexion
	        //parseamos el objeto pq son de tipos distintos
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        // traemos la cabecera de la web para saber en que formato esta encodeado
	        String encoding = conn.getContentEncoding(); // se usa cuando tenemos varias paginas
	        // descargamos la info
	        InputStream input = conn.getInputStream(); // conn es conexion
	        // ahora pasamos todo el contenido a una variable string
	        resultado = new BufferedReader(new InputStreamReader(input))
	        		.lines().filter(links -> links.contains("http"))
	        		.collect(Collectors.joining()); // el collectors colecta todo y permite guararlo en un string
	        System.out.println(resultado);
	        return resultado;
        } catch (IOException e) { // si hay un error lo muestra
        	System.out.print(e.getMessage());
        	return resultado;
        }
    }

		
}
// aca me quede 1:02:39 / 5:38:14
