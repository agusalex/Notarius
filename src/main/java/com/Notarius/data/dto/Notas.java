package com.Notarius.data.dto;

import javax.persistence.*;

@Entity
@Table(name = "notas")
public class Notas implements Identificable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idNota")
    private Long id;

    @Column(name = "nota")
    private String nota="";

    public Notas() {
    }

    @Override
    public boolean isBorrado() {
        return false;
    }

    @Override
    public void setBorrado(boolean b) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Notas(String nota){
        super();
        this.nota=nota;

    }

}
