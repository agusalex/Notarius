import com.Notarius.data.dto.Operacion;
import com.Notarius.services.OperacionService;
import com.google.gson.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class ImportExport
{
    @Test

    public void Import(){
        final JsonParser parser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = parser.parse(new FileReader("operaciones.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        OperacionService os=new OperacionService();
        for (JsonElement operacion :jsonArray) {
            JsonObject jsonObject=operacion.getAsJsonObject();
            Gson gson = new Gson();
            Operacion op=gson.fromJson (jsonObject, Operacion.class);
            op.setId(null);
            System.out.println(op.getId());
            os.save(op);



        }




}


}
