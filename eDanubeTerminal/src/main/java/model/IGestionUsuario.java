package model;

import facade.FacadeUsuario;

public interface IGestionUsuario extends FacadeUsuario {
	
	public Usuario login(String email, String password);
	public boolean darAltaUsuario(Usuario usuario);
	
}
