package com.Notarius.services;


import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Operacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;

import java.io.*;
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


    public void ImportJson() {
        final JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = parser.parse(new FileReader("operaciones.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DAO<Operacion> dao=new DAOImpl<Operacion>(Operacion.class);
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        for (JsonElement operacion : jsonArray) {
            JsonObject jsonObject = operacion.getAsJsonObject();
            Gson gson = new Gson();
            Operacion op = gson.fromJson(jsonObject, Operacion.class);
            dao.save(op);
            System.out.println(op);


        }
    }

        public void exportJson(){
        DAO<Operacion> dao=new DAOImpl<Operacion>(Operacion.class);


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dao.readAll());
            try{
                FileWriter writer = new FileWriter("operaciones.json");
                writer.write(json);
                writer.close();
            }
            catch(Exception e) {
                e.printStackTrace ( );
            }
        }




    public synchronized List<Operacion> findAll(String stringFilter) {
        ArrayList<Operacion> ret = new ArrayList<>();
        DAO<Operacion> dao=new DAOImpl<Operacion>(Operacion.class);
        ArrayList<Operacion> Operacions=( ArrayList<Operacion>)dao.readAll();


        for (int i = 0; i <Operacions.size() ; i++) {
            Operacion operacion=Operacions.get(i);

                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                                        || operacion.toString().toLowerCase()
                                        .contains(stringFilter.toLowerCase());
                if (passesFilter)
                    if(!operacion.isBorrado())
                        ret.add(operacion);





        }

        if(stringFilter!=null&&!stringFilter.equals(""))
            Utils.Search(Operacions,stringFilter).forEach(
                    operacion -> {
                        if(!operacion.isBorrado()) {
                            if(!ret.contains(operacion)) {
                                ret.add(operacion);
                            }
                        }
            });
        

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
        DAO<Operacion> dao=new DAOImpl<Operacion>(Operacion.class);
        value.setCarpeta(10000+value.getCarpeta());
        value.setBorrado(true);
        save(value);
    }

    public synchronized void save(Operacion entry) {

        DAO<Operacion> dao=new DAOImpl<Operacion>(Operacion.class);
        dao.save(entry);
    }

    public static Resource GenerarStreamResource(String path) {
        if (new File("Files" + File.separator + path).exists()) {
            StreamResource str = new StreamResource(new StreamResource.StreamSource() {
                @Override
                public InputStream getStream() {
                    try {
                        return new FileInputStream("Files" + File.separator + path);
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al Buscar Foto: " + path);
                    }
                    return null;
                }
            }, "Files" + File.separator + path);
            return str;
        }
        return null;
    }
}
