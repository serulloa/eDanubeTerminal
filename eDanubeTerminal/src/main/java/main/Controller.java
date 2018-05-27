package main;

import java.util.Scanner;
import facade.FacadeUsuario;
import model.Gender;
import model.GestionUsuario;
import model.Usuario;

public class Controller {
	
	private Scanner in;
	private FacadeUsuario facadeUsuario;
	
	public Controller() {
		this.in = new Scanner(System.in);
	}
    
    protected void run() {
    	this.darAltaUsuario();
    }
    
    public void darAltaUsuario() {
//    	String nombre = "";
//    	String apellidos = "";
//    	int edad = 0;
//    	String email = "";
//    	String contraseña = "";
//    	String repContraseña = "";
//    	Gender gender = Gender.OTHER;
//    	
//    	nombre = getCadena("Nombre");
//    	apellidos = getCadena("Apellidos");
//    	while(edad < 1 || edad > 99) {
//    		System.out.print("\tEdad: ");
//        	edad = in.nextInt();
//        	if(edad < 1 || edad > 99)
//        		System.err.println("\nERROR: La edad debe estar entre 1 y 99 años.");
//    	}
//    	in.nextLine();
//    	gender = getGender();
//    	email = getCadena("Correo electrónico");
//    	while(!contraseña.equals(repContraseña) || contraseña.isEmpty()) {
//    		System.out.print("\tContraseña: ");
//    		contraseña = in.nextLine();
//    		System.out.print("\tRepite contraseña: ");
//    		repContraseña = in.nextLine();
//     	   
//    		if(!contraseña.equals(repContraseña))
//    			System.err.println("\nERROR: Las contraseñas deben coincidir.");
//    		if(contraseña.isEmpty())
//    			System.err.println("\nERROR: La contraseña no puede estar vacía.");
//    	}
//    	
//    	Usuario usuario = new Usuario(email, nombre, apellidos, edad, gender, contraseña);
    	
    	Usuario usuario = new Usuario("sergio@admin.com", "Sergio", "Ulloa", 22, Gender.MALE, "cancer");
    	
		this.facadeUsuario = new GestionUsuario();
		this.facadeUsuario.darAltaUsuario(usuario);
	}
    
    private String getCadena(String atributo) {
    	String cadena = "";
    	while(cadena.isEmpty()) {
			System.out.print("\t" + atributo + ": ");
			cadena = in.nextLine();
			if(cadena.isEmpty())
    			System.err.println("\nERROR: " + atributo.toLowerCase() + " no puede estar vacío.");
			in.reset();
    	}
    	
    	return cadena;
    }
    
    private Gender getGender() {
    	Gender genero = Gender.OTHER;
    	String cadena = "";
    	
    	while(!cadena.toUpperCase().equals("HOMBRE") && 
    			!cadena.toUpperCase().equals("MUJER") && 
    			!cadena.toUpperCase().equals("OTRO")) {
    		System.out.print("\tSexo (HOMBRE / MUJER / OTRO): ");
    		cadena = in.nextLine();
    		if(!cadena.toUpperCase().equals("HOMBRE") && 
    			!cadena.toUpperCase().equals("MUJER") && 
    			!cadena.toUpperCase().equals("OTRO"))
    			System.out.println("\nERROR: Sexo debe ser alguno de [HOMBRE/MUJER/OTRO]");
    		in.reset();
    	}
    	
    	if(cadena.toUpperCase().equals("HOMBRE"))
    		genero = Gender.MALE;
    	else if(cadena.toUpperCase().equals("MUJER"))
    		genero = Gender.FEMALE;
    	else
    		genero = Gender.OTHER;
    	
    	return genero;
    }

}
