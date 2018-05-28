package model;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListaProductosBusqueda implements IListaProductos {
	
	private SessionFactory sf;
	private String busqueda;
	
	public ListaProductosBusqueda(SessionFactory sf, String busqueda) {
		this.sf = sf;
		this.busqueda = busqueda;
	}

	public ArrayList<Producto> getListaProductos() {
		ArrayList<Producto> lista;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			String hql = "select p from Producto p where p.nombre like '%" +  this.busqueda + "%'";
			Query<?> query = session.createQuery(hql).setMaxResults(5);
			lista = (ArrayList<Producto>) query.getResultList();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			lista = null;
		}
		
		return lista;
	}

}
