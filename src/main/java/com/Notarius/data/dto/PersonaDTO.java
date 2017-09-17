package com.Notarius.data.dto;



import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

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

    @Column(name = "dni")
    private String DNI ="";

    @Column(name = "cuitl")
    private String cuitl ="";

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "maritalStatus")
    private MaritalStatus maritalStatus;

    @Column(name = "phone")
    private String phone="";

    @Column(name = "mobilePhone")
    private String mobilePhone="";

    @Column(name = "email")
    private String email="";

    public String getCuitl() {

        return cuitl;
    }

    public void setCuitl(String cuitl) {
        this.cuitl = cuitl;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCountryofOrigin() {
        return countryofOrigin;
    }

    public void setCountryofOrigin(String countryofOrigin) {
        this.countryofOrigin = countryofOrigin;
    }

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "fathersName")
    private String fathersName="";

    @Column(name = "mothersName")
    private String mothersName="";

    @Column(name = "info")
    private String info="";

    @Column(name = "countryofOrigin")
    private String countryofOrigin="";


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDomicilio")
    private DomicilioDTO domicilio;




    public enum MaritalStatus {
        CASADO,DIVORCIADO,SOLTERO,VIUDO;
        @Override
        public String toString(){


            switch (this) {
                case CASADO:  return "Casado";
                case SOLTERO: return "Soltero";
                case DIVORCIADO: return "Divorciado";
                case VIUDO:  return "Viudo";

            }

            return "";

        }

        public static MaritalStatus getEnum(String g){
            switch (g) {
                case "Casado":  return CASADO;
                case "Casada":  return CASADO;
                case "Soltero":  return SOLTERO;
                case "Soltera":  return SOLTERO;
                case "Divorciado":  return DIVORCIADO;
                case "Divorciada":  return DIVORCIADO;
                case "Viudo":  return VIUDO;
                case "Viuda":  return VIUDO;
            }
            throw new IllegalArgumentException("Cannot cast String "+g+" to enum MaritalStatus");
        }
        public String toString(Sex g){


            switch (this) {
                case CASADO:  return "Casad"+g.genreEndWord();
                case SOLTERO: return "Solter"+g.genreEndWord();
                case DIVORCIADO: return "Divorciad"+g.genreEndWord();
                case VIUDO:  return "Viud"+g.genreEndWord();

            }

            return "";

        }

    }


    public enum Sex {
        Masculino, Femenino;

        @Override
        public String toString(){

            if(this== Sex.Masculino){
                return "Masculino";}
            else{return "Femenino";}

        }
        public String genreEndWord(){

            if(this== Sex.Masculino){
                return "o";}
            else{return "a";}

        }
        public static Sex toGenero(String gen){
            switch (gen) {
                case "Masculino": return Masculino;
                case "Femenino": return Femenino;
                case "m": return Masculino;
                case "f": return Femenino;
            }
            return null;
        }

    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
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
