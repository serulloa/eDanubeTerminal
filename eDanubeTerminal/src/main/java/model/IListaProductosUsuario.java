package model;

public interface IListaProductosUsuario extends IListaProductos {
	
	public void añadir(Producto p, int cantidad);
	public boolean eliminar(Producto p);
	public String toString();
	
}
