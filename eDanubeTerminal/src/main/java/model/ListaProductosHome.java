package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ListaProductosHome implements IListaProductos {
	
	private SessionFactory sf;
	
	public ListaProductosHome(SessionFactory sf) {
		this.sf = sf;
	}

	public List<Producto> getListaProductos() {
		List<Producto> lista;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			String hql = "select p from Producto p";
			Query<?> query = session.createQuery(hql).setMaxResults(5);
			lista = (List<Producto>) query.getResultList();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			lista = null;
		}
		
		return lista;
	}

}
