package facade;

import java.util.ArrayList;
import org.hibernate.SessionFactory;

import model.Carrito;
import model.GestionUsuario;
import model.IListaProductos;
import model.IListaProductosUsuario;
import model.ListaDeseos;
import model.ListaProductosBusqueda;
import model.Producto;
import model.Usuario;
import web.IVistaPagina;
import web.VistaEmpleo;
import web.VistaSoporte;
import model.ListaProductosHome;

public class FacadeUsuario {
	
	private GestionUsuario gestionUsuario;
	private IListaProductos iListaProductos;
	private IListaProductosUsuario iListaProductosUsuario;
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
	
	public ArrayList<Producto> verTiendaHome() {
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
	
	public boolean eliminarCarrito(Producto p) {
		return this.carrito.eliminar(p);
	}
	
	public ArrayList<Producto> getListaDeseos(Usuario u) {
		this.iListaProductosUsuario = new ListaDeseos(this.sf,u);
		return this.iListaProductosUsuario.getListaProductos();
	}
	
	public String mostrarListaDeseos(Usuario u) {
		this.iListaProductosUsuario = new ListaDeseos(this.sf,u);
		return this.iListaProductosUsuario.toString();
	}
	
	public boolean eliminarListaDeseos(Producto p, Usuario u) {
		this.iListaProductosUsuario = new ListaDeseos(this.sf, u);
		return this.iListaProductosUsuario.eliminar(p);
	}
	
	public void añadirListaDeseos(Producto p, Usuario u) {
		this.iListaProductosUsuario = new ListaDeseos(this.sf, u);
		this.iListaProductosUsuario.añadir(p, 1);
	}
	
	public ArrayList<Producto> verTiendaBusqueda(String busqueda) {
		this.iListaProductos = new ListaProductosBusqueda(this.sf, busqueda);
		return this.iListaProductos.getListaProductos();
	}

	public String mostrarEmpleo() {
		this.iVistaPagina = new VistaEmpleo();
		return this.iVistaPagina.buildContenidoVista();
	}
	
	public boolean switchPremium(Usuario u) {
		return this.gestionUsuario.switchPremium(u);
	}
	
}
