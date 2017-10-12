package com.Notarius.data.dummy;

import com.Notarius.data.dao.DAO;
import com.Notarius.data.dao.DAOImpl;
import com.Notarius.data.dto.DashboardNotification;
import com.Notarius.data.dto.DataProvider;
import com.Notarius.data.dto.Operacion;
import com.Notarius.data.dto.User;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.gson.*;


import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;

/**
 * A dummy implementation for the backend API.
 */
public class DummyDataProvider implements DataProvider {

    /* List of countries and cities for them */
    private static Multimap<String, String> countryToCities;
    private static Date lastDataUpdate;
    private static Random rand = new Random();
    private final Collection<DashboardNotification> notifications = DummyDataGenerator
            .randomNotifications();

    /**
     * Initialize the data for this application.




    /* JSON utility method */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /* JSON utility method */
    private static JsonObject readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                    Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonElement jelement = new JsonParser().parse(jsonText);
            JsonObject jobject = jelement.getAsJsonObject();
            return jobject;
        } finally {
            is.close();
        }
    }

    /* JSON utility method */
    private static JsonArray readJsonFromFile(File path) throws IOException {
        BufferedReader rd = new BufferedReader(new FileReader(path));
        String jsonText = readAll(rd);
        JsonElement jelement = new JsonParser().parse(jsonText);
        JsonArray jobject = jelement.getAsJsonArray();
        return jobject;
    }

public void levantarOperaciones(){
    try {
       File f= new File("escribania.json");
        System.out.println(f.getAbsolutePath());

        LocalDate now=LocalDate.now();
        JsonArray jsonarray = readJsonFromFile(f);
        ArrayList arrayList = new ArrayList();
        DAO dao=new DAOImpl<Operacion>(Operacion.class);
        ArrayList<Operacion> array = new ArrayList<Operacion>();


        for (int i = 1; i < jsonarray.getAsJsonArray().size(); i++) {
            JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();
            Integer carpeta = jsonobject.get("carpeta").getAsInt();
            String asunto = jsonobject.get("asunto").getAsString();
            Operacion operacion=new Operacion();
            operacion.setFechaDeIngreso(now);
            operacion.setBorrado(false);
            operacion.setEstado(Operacion.Estado.Finalizada);
            operacion.setTipo(Operacion.Tipo.Otro);
            operacion.setAsunto(asunto);
            operacion.setCarpeta(carpeta);
            System.out.println(operacion);
            dao.save(operacion);
        }
        String json = new Gson().toJson(array);
        writeFile("output.json",json);

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static void writeFile(String canonicalFilename, String text)
            throws IOException
    {
        File file = new File (canonicalFilename);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(text);
        out.close();
    }
  
    @Override
    public User authenticate(String userName, String password) {
        User user = new User();
        user.setFirstName("Usuario");
        user.setLastName("");
        user.setRole("admin");
        return user;
    }

 

    private Date getDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }


    @Override
    public int getUnreadNotificationsCount() {
        Predicate<DashboardNotification> unreadPredicate = new Predicate<DashboardNotification>() {
            @Override
            public boolean apply(DashboardNotification input) {
                return !input.isRead();
            }
        };
        return Collections2.filter(notifications, unreadPredicate).size();
    }

    @Override
    public Collection<DashboardNotification> getNotifications() {
        for (DashboardNotification notification : notifications) {
            notification.setRead(true);
        }
        return Collections.unmodifiableCollection(notifications);
    }

    

}
