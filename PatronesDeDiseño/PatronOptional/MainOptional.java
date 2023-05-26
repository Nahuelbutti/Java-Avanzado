package PatronOptional;

import java.util.*;

// java propone y dispone la clase optional para que no estemos asignando null, y asi evitamos errores

public class MainOptional {

	public static void main(String[] args) {
		
		List<String> paises = new ArrayList<>();
		
		paises.add("Argentina");
		paises.add("EEUU");
		paises.add("Colombia");
		paises.add("Brasil");
		paises.add("Peru");
		paises.add("China");
		paises.add("Alemania");
		paises.add("Francia");
		//	devuelve el primer valor que encontro cuyo nombre contenga Arg y debe ser un optionals
		Optional<String> pais = paises.stream()
				.filter(f -> f.startsWith("Arg"))
				.findFirst();
		
		// para utilizarlo
		if (pais.isPresent()) {
			System.out.print("Lo encontre: "+pais.get());
		}
		
	}

}
