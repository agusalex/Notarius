package com.Notarius.data.dto;

import java.util.Arrays;
import java.util.List;

public enum TipoMovimiento {
	Ingreso, Egreso; 
	
	@Override
	public String toString() {
		String ret="";
		
		switch (this) {
		case Ingreso:
			ret="Ingreso";
			break;
		case Egreso:
			ret="Egreso";
			break;
		default:
			ret = super.toString();
		}
		
		return ret;
	}
	
	public static List<TipoMovimiento> toList(){
		return Arrays.asList(TipoMovimiento.Ingreso,TipoMovimiento.Egreso);
	}
}
