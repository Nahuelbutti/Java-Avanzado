package PatronesDeDiseño;

public class MainBuilder {

	public static void main(String[] args) {
		// en lugar de hacer esto: Persona p = new Persona("nombre"+"apellido"+"mail"+"celular");
		// con build podemos hacerlo de otra manera	aca abajo se muestra
		Persona p = Persona.Builder.personita()
				// a esta tecnica de concatenar todo se la conoce como patron de diseño pipeline
				// otra de las ventajas del builder es no permitir la mutacion de los objetos
				.withNombre("Nahuel")
				.withApellido("Butti")
				.withCelular("221 477 8619")
				.withMail("nahuelbutti@hotmail.com")
				.build();
	}

}
