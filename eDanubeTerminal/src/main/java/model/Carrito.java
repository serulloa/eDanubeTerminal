package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrito implements IListaProductosUsuario {
	
	private LinkedHashMap<Producto, Integer> productos;
	private double precioTotal;
	private ArrayList<Producto> arrayProductos;
	
	public Carrito() {
		this.productos = new LinkedHashMap<Producto, Integer>();
		precioTotal = 0;
		this.arrayProductos = new ArrayList<Producto>();
	}
	
	public void añadir(Producto p, int cantidad) {
		productos.put(p, cantidad);
		precioTotal += p.getPrecio() * cantidad;
	}
	
	public boolean eliminar(Producto p) {
		boolean ok = true;
		precioTotal -= p.getPrecio() * productos.get(p);
		if(productos.remove(p) == null)
			ok = false;
		return ok;
	}
	
	public String toString() {
		this.arrayProductos.clear();
		DecimalFormat df = new DecimalFormat("#.00");
		String cadena = "\nCARRITO:\n";
		int count = 1;
		
		Iterator it = this.productos.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Producto p = (Producto) pair.getKey();
			this.arrayProductos.add(p);
			int cantidad = (Integer) pair.getValue();
			
			cadena += "\t" + count + ". " + p.getNombre();
			cadena += "\n\t\tCantidad: " + cantidad;
			cadena += "\n\t\tPrecio: " + df.format(p.getPrecio())  + "€ x " + cantidad + " = " + df.format(p.getPrecio()*cantidad) + "€\n";
			count++;
		}
		
		cadena += "\tPrecio total: " + df.format(this.precioTotal) + "€";
		
		return cadena;
	}
	
	public ArrayList<Producto> getListaProductos() {
		return this.arrayProductos;
	}

}
