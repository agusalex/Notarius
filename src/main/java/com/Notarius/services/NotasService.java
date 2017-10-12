package com.Notarius.services;

import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Notas;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

public class NotasService {
    private static NotasService instance;

    public static NotasService getService() {
        if (instance == null) {

            final NotasService NotasService = new NotasService();

            instance = NotasService;
        }

        return instance;
    }

    private HashMap<Long, Notas> Notass = new HashMap<>();


    public synchronized List<Notas> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<Notas>(Notas.class);
        List<Notas> Notass=dao.readAll();
        for (Notas Notas : Notass) {

            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || Notas.toString().toLowerCase()
                    .contains(stringFilter.toLowerCase());
            if (passesFilter) {

                    arrayList.add(Notas);
            }

        }
        Collections.sort(arrayList, new Comparator<Notas>() {

            @Override
            public int compare(Notas o1, Notas o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }



    public synchronized void save(Notas entry) {
        DAO dao=new DAOImpl<Notas>(Notas.class);
        for (Notas nota:findAll("")
        ) {
        dao.delete(nota);

        }
        dao.save(entry);
        }
        }
