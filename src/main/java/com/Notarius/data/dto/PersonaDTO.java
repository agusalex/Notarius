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
@Table(name = "people")
public class PersonaDTO implements Serializable, Cloneable,Identificable {

    @Id
    @GeneratedValue
    @Column(name = "idPersona")
    private Long id;

    @Column(name = "firstName")
    private String firstName="";

    @Column(name = "lastName")
    private String lastName="";

    @Column(name = "phone")
    private String phone="";

    @Column(name = "email")
    private String email="";

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "fathersName")
    private String fathersName="";

    @Column(name = "countryofOrigin")
    private String countryofOrigin="";

    @Column(name = "info")
    private String info="";

    @Column(name = "mothersName")
    private String mothersName="";

    @Column(name = "dni")
    private String DNI ="";

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDomicilio")
    private DomicilioDTO domicilio;



    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sexo sex;

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getCountryofOrigin() {
        return countryofOrigin;
    }

    public void setCountryofOrigin(String countryofOrigin) {
        this.countryofOrigin = countryofOrigin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public DomicilioDTO getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDTO domicilio) {
        this.domicilio = domicilio;
    }

    public Sexo getSex() {
        return sex;
    }

    public void setSex(Sexo sex) {
        this.sex = sex;
    }



    public enum Sexo{
        Masculino, Femenino
    }


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
