package com.Notarius.data.dto;

import java.util.ArrayList;
import java.util.List;

public enum TipoMoneda {Pesos,Dolares;

    public static List<TipoMoneda> toList() {
        TipoMoneda[] tipos = TipoMoneda.values();
        List<TipoMoneda> ret = new ArrayList<>();
        for (TipoMoneda t : tipos) {
            ret.add(t);
        }
        return ret;
    }
    
    public static String getSimbolo(TipoMoneda tipo) {
	return tipo.equals(TipoMoneda.Dolares)? "U$S" : "$";
    }
}
