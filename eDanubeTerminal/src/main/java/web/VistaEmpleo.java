package web;

public class VistaEmpleo implements IVistaPagina {

	public String buildContenidoVista() {
		String contenido = "";
		
		contenido = "\n¿Estás buscando trabajo? ¿Te parece que nuestra idea es buena y quieres echarnos una mano?"
				+ "\nPues aquí te explicaremos como aplicar para obtener un puesto en nuestro equipo! Lo único"
				+ "\nque tienes que hacer es mandarnos un correo electrónico a 'employment@edanube.com' donde nos"
				+ "\nexpliques por qué quieres trabajar con nosotros; adjuntando tu carta de presentación y tu"
				+ "\nCurrículum Vitae. Suerte!\n"; 
		
		return contenido;
	}

}
