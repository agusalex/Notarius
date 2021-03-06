package com.Notarius.data.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movimiento")
public class Movimiento implements Identificable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;

	@Column(name = "descripcionMovimiento")
	private String descripcionMovimiento;

	@Column(name = "monto")
	private BigDecimal monto = new BigDecimal("0");

	@Column(name = "fecha")
	private Date fecha;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoMovimiento")
	private TipoMovimiento tipoMovimiento;

	@Enumerated(EnumType.STRING)
	@Column(name =  "claseMovimiento")
	private ClaseMovimiento claseMovimiento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipoMoneda")
	private TipoMoneda tipoMoneda;
	@Column(name = "borrado")
	boolean borrado;
	@Column(name = "usuario")
	private String username;


	public Movimiento() {

	}

	public Movimiento(Builder b) {
		this.descripcionMovimiento = b.descripcionMovimiento;
		this.monto = b.monto;
		this.fecha = b.fecha;
		this.tipoMovimiento = b.tipoMovimiento;
		this.claseMovimiento = b.claseMovimiento;
		this.tipoMoneda=b.tipoMoneda;
	}

	public static class Builder {

		private String descripcionMovimiento;
		private BigDecimal monto;
		private Date fecha;
		private TipoMovimiento tipoMovimiento;
		private ClaseMovimiento claseMovimiento;
		private TipoMoneda tipoMoneda;

		public Builder setdescripcionMovimiento(String descripcionMovimiento) {
			this.descripcionMovimiento = descripcionMovimiento;
			return this;
		}

		public Builder setMonto(BigDecimal monto) {
			this.monto = monto;
			return this;
		}

		public Builder setFecha(Date fecha) {
			this.fecha = fecha;
			return this;
		}

		public Builder setTipoMovimiento(TipoMovimiento tipoMovimiento) {
			this.tipoMovimiento = tipoMovimiento;
			return this;
		}
		public Builder setClaseMovimiento(ClaseMovimiento claseMovimiento) {
			this.claseMovimiento = claseMovimiento;
			return this;
		}
		public Builder setTipoMoneda(TipoMoneda dato) {
			this.tipoMoneda=dato;
			return this;
		}

		public Movimiento build() {
			return new Movimiento(this);
		}

	}

	@Override
	public boolean isBorrado() {
		return this.borrado;
	}

	@Override
	public void setBorrado(boolean b) {
		this.borrado=b;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getDescripcionMovimiento() {
		return descripcionMovimiento;
	}

	public void setDescripcionMovimiento(String descripcionMovimiento) {
		this.descripcionMovimiento = descripcionMovimiento;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

public BigDecimal getMonto() {
		if(monto.equals(new BigDecimal(0)))
			return monto;


		return this.tipoMovimiento.equals(TipoMovimiento.Egreso)?monto.multiply(new BigDecimal(-1)):monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto.abs();
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public ClaseMovimiento getClaseMovimiento() {
		return claseMovimiento;
	}

	public void setClaseMovimiento(ClaseMovimiento claseMovimiento) {
		this.claseMovimiento = claseMovimiento;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	public TipoMoneda getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	@Override
	public int hashCode() {
		return 3;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Movimiento))
			return false;
		Movimiento movimiento = (Movimiento) o;
		return getId() != null && Objects.equals(getId(), movimiento.getId());
	}



}