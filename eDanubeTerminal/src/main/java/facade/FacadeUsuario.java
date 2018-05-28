package facade;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;

import model.Carrito;
import model.GestionUsuario;
import model.IListaProductos;
import model.Producto;
import model.Usuario;
import web.IVistaPagina;
import web.VistaSoporte;
import model.ListaProductosHome;

public class FacadeUsuario {
	
	private GestionUsuario gestionUsuario;
	private IListaProductos iListaProductos;
	private IVistaPagina iVistaPagina;
	private SessionFactory sf;
	private Carrito carrito;
	
	public FacadeUsuario(SessionFactory sessionFactory) {
		this.sf = sessionFactory;
		this.gestionUsuario = new GestionUsuario(this.sf);
		this.carrito = new Carrito();
	}
	
	public Usuario login(String email, String password) {
		return gestionUsuario.login(email, password);
	}
	
	public boolean darAltaUsuario(Usuario usuario) {
		return gestionUsuario.darAltaUsuario(usuario);
	}
	
	public boolean modificarUsuario(Usuario actual, Usuario modificado) {
		return gestionUsuario.modificarUsuario(actual, modificado);
	}
	
	public boolean eliminarUsuario(Usuario usuario) {
		return gestionUsuario.eliminarUsuario(usuario);
	}
	
	public List<Producto> verTiendaHome() {
		this.iListaProductos = new ListaProductosHome(this.sf);
		return this.iListaProductos.getListaProductos();
	}
	
	public String mostrarContacto() {
		this.iVistaPagina = new VistaSoporte();
		return this.iVistaPagina.buildContenidoVista();
	}
	
	public void añadirCarrito(Producto p, int cantidad) {
		this.carrito.añadir(p, cantidad);
	}

	public String mostrarCarrito() {
		return this.carrito.toString();
	}
	
	public ArrayList<Producto> getListaProductos() {
		return this.carrito.getListaProductos();
	}
	
	public void eliminarCarrito(Producto p) {
		this.carrito.eliminar(p);
	}
	
}
