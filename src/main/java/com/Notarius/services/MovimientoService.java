package com.Notarius.services;


import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Movimiento;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.*;
import java.util.*;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class MovimientoService {



    private static MovimientoService instance;

    public static MovimientoService getService() {
        if (instance == null) {

            final MovimientoService MovimientoService = new MovimientoService();

            instance = MovimientoService;
        }

        return instance;
    }

    private HashMap<Long, Movimiento> Movimientos = new HashMap<>();


    public void ImportJson() {
        final JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = parser.parse(new FileReader("Movimientoes.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DAO<Movimiento> dao=new DAOImpl<Movimiento>(Movimiento.class);
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        for (JsonElement Movimiento : jsonArray) {
            JsonObject jsonObject = Movimiento.getAsJsonObject();
            Gson gson = new Gson();
            Movimiento op = gson.fromJson(jsonObject, Movimiento.class);
            dao.save(op);
            System.out.println(op);


        }
    }

        public void exportJson(){
        DAO<Movimiento> dao=new DAOImpl<Movimiento>(Movimiento.class);


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dao.readAll());
            try{
                FileWriter writer = new FileWriter("Movimientoes.json");
                writer.write(json);
                writer.close();
            }
            catch(Exception e) {
                e.printStackTrace ( );
            }
        }




    public synchronized List<Movimiento> findAll(String stringFilter) {
        ArrayList<Movimiento> ret = new ArrayList<>();
        DAO<Movimiento> dao=new DAOImpl<Movimiento>(Movimiento.class);
        ArrayList<Movimiento> Movimientos=( ArrayList<Movimiento>)dao.readAll();


        for (int i = 0; i <Movimientos.size() ; i++) {
            Movimiento Movimiento=Movimientos.get(i);

                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || Movimiento.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter)
                    if(!Movimiento.isBorrado())
                        ret.add(Movimiento);





        }

        

        Collections.sort(ret, new Comparator<Movimiento>() {

            @Override
            public int compare(Movimiento o1, Movimiento o2) {
                return (int) (o2.getFecha().compareTo(o1.getFecha()));
            }
        });
        return ret;
    }


    public synchronized void delete(Movimiento value) {
        DAO<Movimiento> dao=new DAOImpl<Movimiento>(Movimiento.class);
        value.setBorrado(true);
        save(value);
    }

    public synchronized void save(Movimiento entry) {

        DAO<Movimiento> dao=new DAOImpl<Movimiento>(Movimiento.class);
        dao.save(entry);
    }

}
