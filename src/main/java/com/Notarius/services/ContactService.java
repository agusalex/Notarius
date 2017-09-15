package com.Notarius.services;


import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.PersonaDTO;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class ContactService {



    private static ContactService instance;

    public static ContactService getService() {
        if (instance == null) {

            final ContactService contactService = new ContactService();

            instance = contactService;
        }

        return instance;
    }

    private HashMap<Long, PersonaDTO> contacts = new HashMap<>();


    public synchronized List<PersonaDTO> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<PersonaDTO>(PersonaDTO.class);
        List<PersonaDTO> personas=dao.readAll();
        for (PersonaDTO contact : personas) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || contact.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ContactService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<PersonaDTO>() {

            @Override
            public int compare(PersonaDTO o1, PersonaDTO o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }



    public synchronized void delete(PersonaDTO value) {
        DAO dao=new DAOImpl<PersonaDTO>(PersonaDTO.class);
        dao.delete(value);
    }

    public synchronized void save(PersonaDTO entry) {

        try {
            entry = (PersonaDTO) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        DAO dao=new DAOImpl<PersonaDTO>(PersonaDTO.class);
        dao.save(entry);
    }

}
