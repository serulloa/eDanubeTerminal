package model;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListaProductosHome implements IListaProductos {
	
	private SessionFactory sf;
	
	public ListaProductosHome(SessionFactory sf) {
		this.sf = sf;
	}

	public ArrayList<Producto> getListaProductos() {
		ArrayList<Producto> lista;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			String hql = "select p from Producto p";
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
