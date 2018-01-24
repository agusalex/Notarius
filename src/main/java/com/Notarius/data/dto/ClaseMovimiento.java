package com.Notarius.data.dto;

import java.util.Arrays;
import java.util.List;

public enum ClaseMovimiento {
    Deposito,
	Sellado,
	Venta,
	Otro,
	PagoAPropietario,
	Impuesto,
	PagoAVendedor,
	ComisionAVendedor,ComisionAComprador;

    @Override
    public String toString() {
	String ret = "";
	switch (this) {
	case ComisionAComprador:
	    ret = "Comisión a comprador";
	    break;
	case ComisionAVendedor:
	    ret = "Comisión a vendedor";
	    break;
	case PagoAVendedor:
	    ret = "Pago a vendedor";
	    break;
	case Venta:
	    ret = "Venta";
	    break;
	case Sellado:
	    ret = "Sellado";
	    break;
	case Deposito:
	    ret = "Depósito";
	    break;
		
	case Otro:
	    ret = "Otro";
	    break;

	case PagoAPropietario:
	    ret = "Pago a propietario";
	    break;

	case Impuesto:
	    ret = "Impuesto";
	    break;
	}

	return ret;
    }

	public static List<ClaseMovimiento> toList(){
		return Arrays.asList(ClaseMovimiento.values());

	}

}
