package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class GestionUsuario {
	
	private SessionFactory sf;
	
	public GestionUsuario(SessionFactory sf) {
		this.sf = sf;
	}
	
	public Usuario login(String email, String password) {
		Usuario usuario = null;
		
		Session session = this.sf.openSession();
		session.beginTransaction();
		
		String hql = "select u from Usuario u where email='" + email + "' and password='" + password + "'";
		Query<?> query = session.createQuery(hql);
		List<?> results = query.getResultList();
		
		if(!results.isEmpty())
			usuario = (Usuario) results.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return usuario;
	}

	public boolean darAltaUsuario(Usuario usuario) {
		boolean ok = true;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			session.save(usuario);
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			ok = false;
		}
		
		return ok;
	}
	
	public boolean modificarUsuario(Usuario actual, Usuario modificado) {
		boolean ok = true;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			String hql = "update Usuario u set  ";
			if(!actual.getName().equals(modificado.getName()))
				hql += "u.name='" + modificado.getName() + "',";
			if(!actual.getLastName().equals(modificado.getLastName()))
				hql += "u.lastName='" + modificado.getLastName() + "',";
			if(actual.getAge() != modificado.getAge())
				hql += "u.age='" + modificado.getAge() + "',";
			if(actual.getGender() != modificado.getGender())
				hql += "u.gender='" + modificado.getGender() + "',";
			if(!actual.getPassword().equals(modificado.getPassword()))
				hql += "u.password='" + modificado.getPassword() + "',";
			hql = hql.substring(0, hql.length() - 1);
			hql += " where u.email='" + actual.getEmail() + "'";
			Query<?> query = session.createQuery(hql);
			query.executeUpdate();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			ok = false;
		}
		
		return ok;
	}

	public boolean eliminarUsuario(Usuario usuario) {
		boolean ok = true;
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			String hql = "delete from Usuario u where u.email=:email";
			Query<?> query = session.createQuery(hql);
			query.setParameter("email", usuario.getEmail());
			query.executeUpdate();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			ok = false;
		}
		
		return ok;
	}

	public boolean switchPremium(Usuario u) {
		boolean ok = true;
		u.setPremium(!u.isPremium());
		
		try {
			Session session = this.sf.openSession();
			session.beginTransaction();
			
			session.update(u);
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			ok = false;
		}
		
		return ok;
	}
}
