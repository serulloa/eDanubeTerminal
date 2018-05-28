package main;

import java.util.Scanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import facade.FacadeUsuario;
import model.Gender;
import model.Opcion;
import model.Usuario;

public class Controller {
	
	private Scanner in;
	private FacadeUsuario facadeUsuario;
	private Usuario usuario;
	private SessionFactory sessionFactory;
	private boolean salir;
	private ControllerTienda ct;
	
	public Controller() {
		this.salir = false;
		this.in = new Scanner(System.in);
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.err.println(ex.getStackTrace());
		}
		this.facadeUsuario = new FacadeUsuario(this.sessionFactory);
		this.ct = new ControllerTienda(this.in, this.facadeUsuario, this.usuario);
	}
    
    protected void run() {
    	boolean ok = false;
    	Opcion op;
    	
    	while(!ok) {
	    	String opcion = this.bienvenida();
	    	
	    	if(opcion.equals("1"))
	    		ok = this.login();
	    	else if(opcion.equals("2")) {
	    		if(!this.darAltaUsuario())
	    			System.err.println("\nERROR: No ha sido posible realizar el registro de la nueva cuenta. Puede que el correo ya esté en uso.");
	    		salir = false;
	    	}
	    	else
	    		ok = salir = true;
    	}
    	
    	while(!salir) {
    		op = this.menu();
    		
    		switch(op) {
    			case TIENDA:
    				this.ct.run();
    				break;
	    		case CUENTA:
	    			this.cuenta();
	    			break;
	    		case CONTACTO:
	    			this.mostrarContacto();
	    			break;
	    		case EMPLEO:
	    			this.mostrarEmpleo();
	    			break;
	    		case SALIR:
	    			System.out.println("Hasta la próxima!");
	    			salir = true;
	    			break;
    		}
    	}
    	
    	this.sessionFactory.close();
    }
    
    public boolean darAltaUsuario() {
    	String nombre = "";
    	String apellidos = "";
    	int edad = 0;
    	String email = "";
    	String contraseña = "";
    	String repContraseña = "";
    	Gender gender = Gender.OTRO;
    	
    	nombre = getCadena("Nombre");
    	apellidos = getCadena("Apellidos");
    	while(edad < 1 || edad > 99) {
    		System.out.print("\tEdad: ");
        	edad = in.nextInt();
        	if(edad < 1 || edad > 99)
        		System.err.println("\nERROR: La edad debe estar entre 1 y 99 años.");
    	}
    	in.nextLine();
    	gender = getGender();
    	email = getCadena("Correo electrónico");
    	while(!contraseña.equals(repContraseña) || contraseña.isEmpty()) {
    		System.out.print("\tContraseña: ");
    		contraseña = in.nextLine();
    		System.out.print("\tRepite contraseña: ");
    		repContraseña = in.nextLine();
     	   
    		if(!contraseña.equals(repContraseña))
    			System.err.println("\nERROR: Las contraseñas deben coincidir.");
    		if(contraseña.isEmpty())
    			System.err.println("\nERROR: La contraseña no puede estar vacía.");
    	}
    	
    	Usuario usuario = new Usuario(email, nombre, apellidos, edad, gender, contraseña);
    	
//    	Usuario usuario = new Usuario("sergio@admin.com", "Sergio", "Ulloa", 22, Gender.MALE, "cancer");
    	
		return this.facadeUsuario.darAltaUsuario(usuario);
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
    	Gender genero = Gender.OTRO;
    	String cadena = "";
    	
    	while(!cadena.toUpperCase().equals("HOMBRE") && 
    			!cadena.toUpperCase().equals("MUJER") && 
    			!cadena.toUpperCase().equals("OTRO")) {
    		System.out.print("\tSexo (HOMBRE / MUJER / OTRO): ");
    		cadena = in.nextLine();
    		if(!cadena.toUpperCase().equals("HOMBRE") && 
    			!cadena.toUpperCase().equals("MUJER") && 
    			!cadena.toUpperCase().equals("OTRO"))
    			System.err.println("\nERROR: Sexo debe ser alguno de [HOMBRE/MUJER/OTRO]");
    		in.reset();
    	}
    	
    	if(cadena.toUpperCase().equals("HOMBRE"))
    		genero = Gender.HOMBRE;
    	else if(cadena.toUpperCase().equals("MUJER"))
    		genero = Gender.MUJER;
    	else
    		genero = Gender.OTRO;
    	
    	return genero;
    }
    
    private boolean login() {
    	boolean ok = false;
    	Usuario usuario = new Usuario();
    	
    	System.out.println("\nInicie sesión para acceder a la tienda:");
    	String email = this.getCadena("Correo electrónico");
    	String password = this.getCadena("Contraseña");
    	
    	usuario = this.facadeUsuario.login(email, password);
    	
    	if(usuario != null) {
    		ok = true;
    		this.usuario = usuario;
    		this.ct.setUsuario(usuario);
    	}
    	else
    		System.err.println("\nERROR: el email o la contraseña son incorrectos, por favor, revíselos.");
    	
    	return ok;
    }
    
    private String bienvenida() {
    	String opcion = "";
    	boolean ok = false;
    	
    	while(!ok) {
	    	System.out.println("\nHola! Bienvenido a eDanube.");
	    	System.out.println("\t1. Iniciar sesión");
	    	System.out.println("\t2. Registrarse");
	    	System.out.println("\t3. Salir");
	    	System.out.print("Opción > ");
	    	
	    	opcion = in.nextLine();
	    	
	    	if(!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3")) {
	    		System.err.println("\nERROR: debe seleccionar alguna de las opciones disponibles.");
	    		ok = false;
	    	}
	    	else
	    		ok = true;
    	}
    	
    	return opcion;
    }
    
    private Opcion menu() {
    	Opcion op = Opcion.SALIR;
    	String opcion = "";
    	boolean ok = false;
    	
    	while(!ok) {
    		System.out.println("\nBienvenido " + this.usuario.getName() + "! Qué es lo que quieres hacer?");
    		System.out.println("\t1. Tienda");
    		System.out.println("\t2. Mi área personal");
    		System.out.println("\t3. Contacto");
    		System.out.println("\t4. Empleo");
    		System.out.println("\t0. Salir");
    		System.out.print("Opción > ");
    		
    		opcion = this.in.nextLine();
    		
    		if(opcion.equals("1")) {
    			op = Opcion.TIENDA;
    			ok = true;
    		}
    		else if(opcion.equals("2")) {
    			op = Opcion.CUENTA;
    			ok = true;
    		}
    		else if(opcion.equals("3")) {
    			op = Opcion.CONTACTO;
    			ok = true;
    		}
    		else if(opcion.equals("4")) {
    			op = Opcion.EMPLEO;
    			ok = true;
    		}
    		else if(opcion.equals("0")) {
    			op = Opcion.SALIR;
    			ok = true;
    		}
    		else
    			System.err.println("\nERROR: debe seleccionar alguna de las opciones disponibles.");
    	}
    	
    	return op;
    }
    
    private void cuenta() {
    	String opcion = "";
    	boolean atras = false;
    	
    	while(!atras && !salir) {
	    	System.out.println("\nÁREA PERSONAL");
	    	System.out.println("Estos son tus datos personales:");
	    	System.out.println("\tNombre: " + usuario.getName());
	    	System.out.println("\tApellidos: " + usuario.getLastName());
	    	System.out.println("\tEdad: " + usuario.getAge());
	    	System.out.println("\tSexo: " + usuario.getGender().name().toLowerCase());
	    	System.out.println("\tCorreo electrónico: " + usuario.getEmail());
	    	if(usuario.isPremium()) System.out.println("\n\tEsta es una cuenta premium.");
	    	
	    	System.out.println("\nOpciones:");
	    	System.out.println("\t1. Modificar datos");
	    	System.out.println("\t2. Eliminar cuenta");
	    	System.out.println("\t3. Atrás");
	    	
	    	opcion = this.in.nextLine();
	    	
	    	if(opcion.equals("1"))
	    		this.modificarCuenta();
	    	else if(opcion.equals("2"))
	    		this.eliminarCuenta();
	    	else if(opcion.equals("3"))
	    		atras = true;
	    	else
	    		System.err.println("\nERROR: debes introducir alguna de las opciones disponibles.");
    	}    	
    }
    
    private void modificarCuenta() {
    	String nombre = "";
    	String apellidos = "";
    	int edad = 0;
    	String contraseña = "";
    	String repContraseña = "";
    	Gender gender = Gender.OTRO;
    	boolean ok = false;
    	
    	nombre = getCadena("Nombre");
    	apellidos = getCadena("Apellidos");
    	while(edad < 1 || edad > 99) {
    		System.out.print("\tEdad: ");
        	edad = in.nextInt();
        	if(edad < 1 || edad > 99)
        		System.err.println("\nERROR: La edad debe estar entre 1 y 99 años.");
    	}
    	in.nextLine();
    	gender = getGender();
    	while(!contraseña.equals(repContraseña) || contraseña.isEmpty()) {
    		System.out.print("\tContraseña: ");
    		contraseña = in.nextLine();
    		System.out.print("\tRepite contraseña: ");
    		repContraseña = in.nextLine();
     	   
    		if(!contraseña.equals(repContraseña))
    			System.err.println("\nERROR: Las contraseñas deben coincidir.");
    		if(contraseña.isEmpty())
    			System.err.println("\nERROR: La contraseña no puede estar vacía.");
    	}
    	
    	Usuario modificado = new Usuario(this.usuario.getEmail(), nombre, apellidos, edad, gender, contraseña);
    	ok = this.facadeUsuario.modificarUsuario(this.usuario, modificado);
    	
    	if(!ok) System.err.println("\nERROR: No ha sido posible actualizar tus datos.");
    	else System.out.println("\nDatos personales actualizados con éxito!");
    }

    private void eliminarCuenta() {
    	boolean ok = true;
    	String opcion = "";
    	
    	while(!opcion.equals("si") && !opcion.equals("no")) {
    		System.out.println("Estás TOTALMENTE seguro de que quieres eliminar PERMANENTEMENTE tu cuenta?\n[si/no] > ");
    		opcion = this.in.nextLine();
    	}
    	
    	if(opcion.equals("si")) {
    		ok = this.facadeUsuario.eliminarUsuario(usuario);
    		if(ok) {
    			System.out.println("\nCuenta eliminada correctamente. Esperamos volver a verte por aquí!");
    			salir = true;
    		}
    		else System.err.println("\nERROR: No se ha podido eliminar la cuenta.");
    	}
    }

    private void mostrarContacto() {
    	System.out.println(this.facadeUsuario.mostrarContacto());
    }
    
    private void mostrarEmpleo() {
    	System.out.println(this.facadeUsuario.mostrarEmpleo());
    }
    
}
