package com.Notarius.data.dto;

import javax.persistence.*;


@Entity
@Table(name = "domicilios")
public class DomicilioDTO implements Identificable {
	@Id @GeneratedValue
	@Column(name = "idDomicilio")
	private Long idDomicilio;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "number")
	private int number;
	
	@Column(name = "floor")
	private int piso;
	
	@Column(name = "apartment")
	private String apartment;

	@Column(name = "city")
	private String city;

	@Column(name = "county")
	private String county;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;






	public DomicilioDTO(Long id) {
		this.idDomicilio=id;
	}

	


	@Override
	public String toString() {
		return "DomicilioDTO [idDomicilio=" + idDomicilio + ", calle=" + street + ", altura=" + number + ", piso=" + piso
				+ ", departamento=" + apartment + "]";
	}

	@Override
	public boolean isBorrado() {
		return false;
	}

	@Override
	public void setBorrado(boolean b) {

	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
