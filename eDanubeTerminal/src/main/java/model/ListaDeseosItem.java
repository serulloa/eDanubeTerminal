package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="listadeseos")
public class ListaDeseosItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email")
	private Usuario usuario;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idProducto")
	private Producto producto;
	
	public ListaDeseosItem() {
		super();
	}
	
	public ListaDeseosItem(Usuario u, Producto p) {
		this.usuario = u;
		this.producto = p;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
}
