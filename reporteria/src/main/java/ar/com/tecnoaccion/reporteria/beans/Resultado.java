package ar.com.tecnoaccion.reporteria.beans;

import java.util.List;

public class Resultado {

	List rs;
	public Resultado(List rs) {
		this.rs = rs; 
	}
	
	public List getRs( ) {
		return this.rs;
	}
}
