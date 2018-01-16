import com.Notarius.data.dto.Operacion;
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
            jsonElement = parser.parse(new FileReader("test.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        //RESULTS
        for (JsonElement operacion :jsonArray) {
            JsonObject jsonObject=operacion.getAsJsonObject();
            Gson gson = new Gson();
            Operacion op=gson.fromJson (jsonObject, Operacion.class);
            System.out.println(op);

        }




}


}
