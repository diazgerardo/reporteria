package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import java.io.File;

import ar.com.fdvs.dj.domain.ImageBanner.Alignment;


public class Logo {

	private String logoPath;
	private int logoWidth; 
	private int logoHeight; 
	private String logoAlign;
	private boolean logoExists;
	
	public Logo(String logoPath, int logoWidth, int logoHeight, String logoAlign) {
		this.logoAlign=logoAlign;
		this.logoHeight=logoHeight;
		this.logoWidth=logoWidth;
		this.logoPath=logoPath;
		
		this.logoExists=new File(logoPath).exists();
	}

	public String getLogoPath() {
		return logoPath;
	}

	public int getLogoWidth() {
		return logoWidth;
	}

	public int getLogoHeight() {
		return logoHeight;
	}

	
	/***************************************************************************
	 * 
	 * Hacemos esto para evitar utilizar metadata conteniendo byte encodeados
	 * lo cual podria resultar problematico -mas alla de que provengan de un 
	 * property o un campo en la DB- debido a los diferentes encodings 
	 * entre sistemas y/o seteos de regionalizacion
	 * 
	 * De querer avanzar por esa linea podria hacerse con 
	 * 
	 * String Base64.encodeToString(byte[])
	 *
	 * para encodear el dato y con 
	 * 
	 * byte[] Base64.decode(String)
	 * 
	 * para desencodearlo
	 * 
	 ***************************************************************************
	 */
	public Alignment getLogoAlign() {
		Alignment alignment;
		if("CENTER".equals(logoAlign)) {
			alignment = Alignment.Center;
		} else if ("RIGHT".equals(logoAlign)) {
			alignment = Alignment.Right; 	
		} else {
			alignment = Alignment.Left;
		}
		return alignment;
	}

	public boolean exists() {
		return logoExists;
	}

}
