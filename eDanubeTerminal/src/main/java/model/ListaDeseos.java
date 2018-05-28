package model;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListaDeseos implements IListaProductosUsuario {
	
	private ArrayList<Producto> productos;
	private Usuario owner;
	private SessionFactory sf;
	
	public ListaDeseos(SessionFactory sf, Usuario usuario) {
		this.sf = sf;
		this.productos = new ArrayList<Producto>();
		this.owner = usuario;

		Session session = this.sf.openSession();
		session.beginTransaction();
		
		String hql = "select ld.producto from ListaDeseosItem ld where ld.usuario=:user";
		Query<?> query = session.createQuery(hql);
		query.setParameter("user", this.owner);
		ArrayList<Producto> ldi = (ArrayList<Producto>) query.getResultList();
		
		for(Producto i : ldi) {
			this.productos.add(i);
		}
		
		session.getTransaction().commit();
		session.close();
	}

	public ArrayList<Producto> getListaProductos() {
		return this.productos;
	}

	public void añadir(Producto p, int cantidad) {
		this.productos.add(p);
		
		Session session = this.sf.openSession();
		session.beginTransaction();
		
		session.save(new ListaDeseosItem(this.owner, p));

		session.getTransaction().commit();
		session.close();
	}

	public boolean eliminar(Producto p) {
		Session session = this.sf.openSession();
		session.beginTransaction();
		
		String hql = "delete from ListaDeseosItem ldi where ldi.usuario=:user and ldi.producto=:producto";
		Query<?> query = session.createQuery(hql);
		query.setParameter("user", this.owner);
		query.setParameter("producto", p);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
		
		return this.productos.remove(p);
	}
	
	public String toString() {
		String cadena = "";
		
		int cont = 1;
		for(Producto p: this.productos) {
			cadena += "\t" + cont + ". " + p.getNombre() + " [" + p.getCategoria().name() + "]\n";
			cont++;
		}
		
		return cadena;
	}

}
