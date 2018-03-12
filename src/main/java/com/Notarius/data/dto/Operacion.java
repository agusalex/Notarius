package com.Notarius.data.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "operacion")
public class Operacion implements Serializable,Identificable{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idOperacion")
    protected Long id;

    @Enumerated(EnumType.STRING)

    @Column(name = "tipoOperacion")
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoOperacion")
    private Estado estado;
    @Column(name = "fechaDeIngreso")
    private LocalDate fechaDeIngreso;
    @Column(name = "borrado")
    boolean borrado;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "carpeta")
    private Integer carpeta;

    public Set<String> getPathImagenes() {
        return pathImagenes;
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "paths_imagenes", joinColumns = @JoinColumn(name = "idOperacion"))
    private Set<String> pathImagenes = new HashSet<>();


    public void removePathImagen(String path) {

        Object [] archivos = pathImagenes.toArray();

        for (int i = 0; i < archivos.length ; i++) {
            pathImagenes.remove(path);
        }

    }

    public void addPathImagen(String s) {
        pathImagenes.add(s);
    }


    public enum Estado {
        Iniciada,Inscripta,Finalizada,Descartada;
        public static List<Estado> toList() {
            Estado[] clases = Estado.values();
            List<Estado> ret = new ArrayList<>();
            for (Estado c : clases) {
                ret.add(c);
            }
            return ret;
        }

    }
    public enum Tipo {
        Otro,Compraventa,Donación,Can_Hipoteca,Cesión_Boleto,Cesión_Derechos,Cert_Firma,Autorización;
        public static List<Tipo> toList() {
            Tipo[] clases = Tipo.values();
            List<Tipo> ret = new ArrayList<>();
            for (Tipo c : clases) {
                ret.add(c);
            }
            return ret;
        }

    }

    public boolean finalizada(){
        if(this.getEstado().equals(Estado.Finalizada))
            return true;
        else
            return false;
    }
    public Operacion() {
        this.setEstado(Estado.Iniciada);
        this.setBorrado(false);
    }

    public Operacion(Long id, String asunto, Integer carpeta,Tipo operacion,LocalDate fechaDeIngreso) {
	super();
	this.asunto=asunto;
	this.carpeta=carpeta;
	this.tipo=operacion;
	this.fechaDeIngreso = fechaDeIngreso;
	this.id = id;
	this.borrado=false;

    }
    @Override
    public String toString() {
        return  carpeta+" "+tipo +
                " " + asunto ;
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(LocalDate fechadeIngreso) {
        this.fechaDeIngreso = fechadeIngreso;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(Integer carpeta) {
        this.carpeta = carpeta;
    }
    @Override
    public Long getId() {
	return this.id;
    }

    public void setId(Long id) {
	this.id = id;
    }



}
