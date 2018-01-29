import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.Operacion;
import com.Notarius.data.dto.Persona;
import com.Notarius.services.OperacionService;
import com.Notarius.services.PersonaService;
import com.google.gson.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public class ImportExport
{
    @Ignore

    public void ImportOperaciones() {
        final JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = parser.parse(new FileReader("operaciones.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        OperacionService os = new OperacionService();
        for (JsonElement operacion : jsonArray) {
            JsonObject jsonObject = operacion.getAsJsonObject();
            Gson gson = new Gson();
            Operacion op = gson.fromJson(jsonObject, Operacion.class);
            op.setId(null);
            System.out.println(op.getId());
            os.save(op);


        }
    }
        @Ignore
        public void exportOperacionesJson(){
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




    @Test

    public void ImportClientes() {
        final JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = parser.parse(new FileReader("clientes.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        PersonaService os = new PersonaService();
        for (JsonElement operacion : jsonArray) {
            JsonObject jsonObject = operacion.getAsJsonObject();
            Gson gson = new Gson();
            Persona op = gson.fromJson(jsonObject, Persona.class);
            op.setId(null);
            System.out.println(op.getId());
            os.save(op);
        }
    }
    @Ignore
    public void exportClientes(){
        DAO<Persona> dao=new DAOImpl<Persona>(Persona.class);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dao.readAll());
        try{
            FileWriter writer = new FileWriter("clientes.json");
            writer.write(json);
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace ( );
        }
    }



    }



