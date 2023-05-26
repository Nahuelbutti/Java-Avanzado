package PatronesDeDiseño;

import java.util.Objects;


public class Persona {
	private String nombre;
	private String apellido;
	private String mail;
	private String celular;
	
	public Persona(String nombre, String apellido, String mail, String celular) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.celular = celular;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public String getMail() {
		return mail;
	}
	public String getCelular() {
		return celular;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, celular, mail, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(celular, other.celular)
				&& Objects.equals(mail, other.mail) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", celular=" + celular + "]";
	}
	
// patron de diseño build
// genere el builder con shift+control+B

	public static final class Builder {
		private String nombre;
		private String apellido;
		private String mail;
		private String celular;

		private Builder() {
		}
		
		public static Builder personita() {
			return new Builder();
		}

		public Builder withNombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public Builder withApellido(String apellido) {
			this.apellido = apellido;
			return this;
		}

		public Builder withMail(String mail) {
			this.mail = mail;
			return this;
		}

		public Builder withCelular(String celular) {
			this.celular = celular;
			return this;
		}

		public Persona build() {
			return new Persona(nombre,apellido,mail,celular);
		}
	}
	
	
}
