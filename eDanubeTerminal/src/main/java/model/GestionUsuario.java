package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class GestionUsuario implements IGestionUsuario {
	
	private SessionFactory sessionFactory;

	public Usuario login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean darAltaUsuario(Usuario usuario) {
		boolean ok = true;
		
		try {
			this.setup();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.save(usuario);
			
			session.getTransaction().commit();
			session.close();
			
			this.sessionFactory.close();
		} catch (Exception e) {
			ok = false;
			System.out.println("Problem creating session factory");
		    e.printStackTrace();
		}
		
		return ok;
	}
	
	private void setup() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
		    throw ex;
		}
	}

}
