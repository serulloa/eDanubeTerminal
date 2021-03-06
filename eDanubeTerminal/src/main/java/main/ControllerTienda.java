package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import facade.FacadeUsuario;
import model.OpcionTienda;
import model.Producto;
import model.Usuario;

public class ControllerTienda {
	
	private Scanner in;
	private FacadeUsuario facadeUsuario;
	private Usuario usuario;
	private ArrayList<Producto> listaProductos;
	
	public ControllerTienda(Scanner in, FacadeUsuario facade, Usuario usuario) {
		this.in = in;
		this.facadeUsuario = facade;
		this.usuario = usuario;
	}
	
	public void run() {
		this.getProductosHome();
		boolean salir = false;
		OpcionTienda ot = OpcionTienda.SALIR;		
		
		while(!salir) {
			ot = this.menu();
			
			switch(ot) {
				case PRODUCTO1:
					this.mostrarProducto(this.listaProductos.get(0));
					break;
				case PRODUCTO2:
					this.mostrarProducto(this.listaProductos.get(1));
					break;
				case PRODUCTO3:
					this.mostrarProducto(this.listaProductos.get(2));
					break;
				case PRODUCTO4:
					this.mostrarProducto(this.listaProductos.get(3));
					break;
				case PRODUCTO5:
					this.mostrarProducto(this.listaProductos.get(4));
					break;
				case BUSCAR:
					this.buscarProductos();
					break;
				case CARRITO:
					this.mostrarCarrito();
					break;
				case LISTADESEOS:
					this.mostrarListaDeseos();
					break;
				case SALIR:
					salir = true;
					break;
				default:
					salir = true;
			}
		}
	}
	
	private void muestraProductos() {
		int cont = 1;
		
		if(listaProductos.size() > 0) {
			for(Producto p: listaProductos) {
				System.out.println("\t" + cont + ". " + p.getNombre() + " [" + p.getCategoria().name() + "] ");
				cont++;
			}
		}
	}
	
	private void getProductosHome() {
		this.listaProductos = this.facadeUsuario.verTiendaHome();
	}
	
	private OpcionTienda menu() {
		String opcion = "";
		OpcionTienda ot = OpcionTienda.SALIR;
		boolean ok = false;
		
		while(!ok) {
			System.out.println("\nBienvenido a nuestra tienda!");
			this.muestraProductos();
			
			System.out.println("\n\tTambién puedes elegir estas opciones:");
			System.out.println("\t6. Buscar");
			System.out.println("\t7. Carrito");
			System.out.println("\t8. Lista de deseos");
			System.out.println("\t0. Atrás");
			System.out.print("Opción > ");
			
			opcion = this.in.nextLine();
			
			if(opcion.equals("1")) {
				ot = OpcionTienda.PRODUCTO1;
				ok = true;
			}
			else if(opcion.equals("2")) {
				ot = OpcionTienda.PRODUCTO2;
				ok = true;
			}
			else if(opcion.equals("3")) {
				ot = OpcionTienda.PRODUCTO3;
				ok = true;
			}
			else if(opcion.equals("4")) {
				ot = OpcionTienda.PRODUCTO4;
				ok = true;
			}
			else if(opcion.equals("5")) {
				ot = OpcionTienda.PRODUCTO5;
				ok = true;
			}
			else if(opcion.equals("6")) {
				ot = OpcionTienda.BUSCAR;
				ok = true;
			}
			else if(opcion.equals("7")) {
				ot = OpcionTienda.CARRITO;
				ok = true;
			}
			else if(opcion.equals("8")) {
				ot = OpcionTienda.LISTADESEOS;
				ok = true;
			}
			else if(opcion.equals("0")) {
				ot = OpcionTienda.SALIR;
				ok = true;
			}
			else
    			System.err.println("\nERROR: debe seleccionar alguna de las opciones disponibles.");
		}
		
		return ot;
	}
	
	private void mostrarProducto(Producto p) {
		boolean atras = false;
		
		while(!atras) {
			String opcion = "";
			DecimalFormat df = new DecimalFormat("#.00");
			
			System.out.println("\n" + p.getNombre());
			System.out.println(df.format(p.getPrecio()) + "€" );
			System.out.println("\nEl id de este producto es " + p.getIdProducto());
			System.out.println("Ahora mismo hay " + p.getStock() + " de estos en stock.");
			System.out.println("\nCategoría: [" + p.getCategoria().name() + "]");
			
			System.out.println("\nOpciones:");
			System.out.println("\t1. Añadir al carrito");
			System.out.println("\t2. Añadir a lista de deseos");
			System.out.println("\t3. Atrás");
			System.out.print("Opcion > ");
			
			opcion = this.in.nextLine();
			
			if(opcion.equals("1")) {
				int cantidad = 0;
				while(cantidad <= 0 || cantidad > p.getStock()) {
					System.out.print("Por favor, introduce una cantidad entre 1 y " + p.getStock() + " > ");
					cantidad = this.in.nextInt();
					in.nextLine();
				}
				this.facadeUsuario.añadirCarrito(p, cantidad);
				System.out.println("Añadido(s) " + cantidad + " producto(s) al carrito.");
				atras = true;
			}
			else if(opcion.equals("2"))
				this.facadeUsuario.añadirListaDeseos(p, this.usuario);
			else if(opcion.equals("3"))
				atras = true;
			else
				System.err.println("\nERROR: debes introducir alguna de las opciones disponibles");
		}
	}
	
	private void mostrarCarrito() {
		boolean atras = false;
		int opcion = 0;
		
		while(!atras) {
			System.out.println(this.facadeUsuario.mostrarCarrito());
			System.out.println("\t0. Atrás");
			System.out.print("Opcion > ");
			
			opcion = this.in.nextInt();
			this.in.nextLine();
			if(opcion > 0 && opcion <= this.facadeUsuario.getListaProductos().size())
				this.carritoOpciones(this.facadeUsuario.getListaProductos().get(opcion-1));
			else if(opcion == 0)
				atras = true;
			else
				System.err.println("\nERROR: debes seleccionar alguna de las opciones disponibles");
		}
	}
	
	private void carritoOpciones(Producto p) {
		boolean ok = false;
		
		while(!ok) {
			String opcion = "";
			System.out.println("\t1. Eliminar");
			System.out.println("\t2. Modificar");
			System.out.println("\t3. Atrás");
			
			System.out.print("Opcion > ");
			opcion = this.in.nextLine();
			
			if(opcion.equals("1")) {
				ok = this.facadeUsuario.eliminarCarrito(p);
				if(!ok) System.err.println("\nERROR: se ha producido un error al intentar borrar el artículo del carrito");
				ok = true;
			}
			else if(opcion.equals("2")) {
				int cantidad = 0;
				while(cantidad <= 0 || cantidad > p.getStock()) {
					System.out.print("Por favor, introduce una cantidad entre 1 y " + p.getStock() + " > ");
					cantidad = this.in.nextInt();
					in.nextLine();
				}
				
				this.facadeUsuario.añadirCarrito(p, cantidad);
				ok = true;
			}
			else if(opcion.equals("3"))
				ok = true;
			else
				System.err.println("\nERROR: debes seleccionar alguna de las opciones disponibles");
		}
	}
	
	private void mostrarListaDeseos() {
		boolean atras = false;
		
		while(!atras) {
			ArrayList<Producto> lista = this.facadeUsuario.getListaDeseos(this.usuario);
			System.out.println("\nLISTA DE DESEOS:");
			System.out.println(this.facadeUsuario.mostrarListaDeseos(this.usuario));
			System.out.println("\n\tPuede seleccionar alguno de los productos para eliminarlo.");
			System.out.println("\t0. Atrás");
			System.out.print("Opcion > ");
			
			int opcion = this.in.nextInt();
			this.in.nextLine();
			
			if(opcion > 0 && opcion <= lista.size())
				this.facadeUsuario.eliminarListaDeseos(lista.get(opcion-1), this.usuario);
			else if(opcion == 0)
				atras = true;
			else
				System.err.println("\nERROR: debes seleccionar alguna de las opciones disponibles");
				
		}
	}
	
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}
	
	private void buscarProductos() {
		System.out.print("Por favor, introduzca una palabra clave > ");
		String busqueda = this.in.nextLine();
		
		this.listaProductos = this.facadeUsuario.verTiendaBusqueda(busqueda);
	}
	
}
