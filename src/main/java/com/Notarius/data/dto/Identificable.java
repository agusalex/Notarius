package com.Notarius.data.dto;

/**
 * 
 * Una interface usada por aquellas entidades que usarán el DAO Generico
 *
 */
public interface Identificable {
	public boolean isBorrado();
	public void setBorrado(boolean b);
	public Long getId();

}
