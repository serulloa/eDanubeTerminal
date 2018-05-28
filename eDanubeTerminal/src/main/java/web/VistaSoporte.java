package web;

public class VistaSoporte implements IVistaPagina {

	public String buildContenidoVista() {
		String contenido = "";
		
		contenido = "\nHola! Si tienes preguntas, propuestas o si has tenido algún problema con un producto..."
				+ "\nEstás en el sitio adecuado! Contamos con algunos de los mejores profesionales en"
				+ "\natención al cliente. Puedes contactarnos de muchas maneras:"
				+ "\n\t- Mandándonos un email a nuestro correo electrónico: support@edanube.com"
				+ "\n\t- Llamándonos por teléfono al _________"
				+ "\n\t- Incluso mandándonos una carta! (Nos gusta ser un poco hipsters) Al siguiente apartado "
				+ "\n\t  de correo: __________"
				+ "\nSi tienes algo que decirnos no dudes en contactarnos! :D\n";
		
		return contenido;
	}

}
