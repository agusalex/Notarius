package com.Notarius.data.dto;



import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
@Entity
@Table(name = "personas")
public class PersonaDTO implements Serializable, Cloneable,Identificable {

    @Id
    @GeneratedValue
    @Column(name = "idPersona")
    private Long id;

    @Column(name = "nombre")
    private String firstName="";

    @Column(name = "apellido")
    private String lastName="";

    @Column(name = "telefono")
    private String phone="";

    @Column(name = "email")
    private String email="";

    @Column(name = "nacimiento")
    private Date birthDate;

 /*   @Enumerated(EnumType.STRING)
    @Column(name = "mascotaPreferida")
    private TipoMascota mascotaPreferida;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDomicilio")
    private DomicilioDTO domicilio;*/


    public PersonaDTO(Long idd){
        id=idd;
    }
    public PersonaDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public PersonaDTO clone() throws CloneNotSupportedException {
        try {
            return (PersonaDTO) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "PersonaDTO{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phone=" + phone + ", email="
                + email + ", birthDate=" + birthDate + '}';
    }

}
