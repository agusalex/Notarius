package com.Notarius.services;


import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Persona;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class PersonaService {



    private static PersonaService instance;

    public static PersonaService getService() {
        if (instance == null) {

            final PersonaService personaService = new PersonaService();

            instance = personaService;
        }

        return instance;
    }

    private HashMap<Long, Persona> contacts = new HashMap<>();


    public synchronized List<Persona> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<Persona>(Persona.class);
        List<Persona> personas=dao.readAll();
        for (Persona contact : personas) {

                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || contact.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    if(!contact.isBorrado())
                        arrayList.add(contact);
                    else{
                        System.out.println("persona eliminada no cargada:"+contact);
                    }
                }

        }
        Collections.sort(arrayList, new Comparator<Persona>() {

            @Override
            public int compare(Persona o1, Persona o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }



    public synchronized void delete(Persona value) {
        DAO dao=new DAOImpl<Persona>(Persona.class);
        value.setBorrado(true);
        System.out.println("Deleted");
        save(value);
    }

    public synchronized void save(Persona entry) {

        DAO dao=new DAOImpl<Persona>(Persona.class);
        dao.save(entry);
        System.out.println("persisted changes");
    }

}
