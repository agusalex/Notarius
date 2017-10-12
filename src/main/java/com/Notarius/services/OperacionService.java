package com.Notarius.services;


import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Operacion;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class OperacionService {



    private static OperacionService instance;

    public static OperacionService getService() {
        if (instance == null) {

            final OperacionService OperacionService = new OperacionService();

            instance = OperacionService;
        }

        return instance;
    }

    private HashMap<Long, Operacion> Operacions = new HashMap<>();


    public synchronized List<Operacion> findAll(String stringFilter) {
        ArrayList ret = new ArrayList();
        DAO dao=new DAOImpl<Operacion>(Operacion.class);
        List<Operacion> Operacions=dao.readAll();


        for (int i = 0; i <Operacions.size() ; i++) {
            Operacion Operacion=Operacions.get(i);


                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || Operacion.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    if(!Operacion.isBorrado()) {
                        ret.add(Operacion);

                    }
                    Operacions.remove(Operacion);
                }

        }
        if(stringFilter!=null&&!stringFilter.equals(""))
            ret.addAll(Utils.Search(Operacions,stringFilter));


        Collections.sort(ret, new Comparator<Operacion>() {

            @Override
            public int compare(Operacion o1, Operacion o2) {
                return (int) (o2.getCarpeta() - o1.getCarpeta());
            }
        });
        return ret;
    }

    public Integer getUltimaCarpeta(){
        List<Operacion> ops=findAll("");
        if(ops.size()<1){
            return 01;
        }
        return ops.get(0).getCarpeta();

    }

    public synchronized void delete(Operacion value) {
        DAO dao=new DAOImpl<Operacion>(Operacion.class);
        value.setCarpeta(10000+value.getCarpeta());
        value.setBorrado(true);
        save(value);
    }

    public synchronized void save(Operacion entry) {

        DAO dao=new DAOImpl<Operacion>(Operacion.class);
        dao.save(entry);
    }

}
