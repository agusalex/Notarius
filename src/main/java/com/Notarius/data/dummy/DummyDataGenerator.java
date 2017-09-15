package com.Notarius.data.dummy;

import com.Notarius.data.dto.DashboardNotification;


import java.util.Arrays;
import java.util.Collection;

public abstract class DummyDataGenerator {






    static Collection<DashboardNotification> randomNotifications() {
        DashboardNotification n1 = new DashboardNotification();
        n1.setId(1);
        n1.setFirstName("Usuario");
        n1.setLastName("");
        n1.setAction("inicio sesion");
        n1.setPrettyTime("hace instantes");
        n1.setContent("");
        n1.setRead(false);


        return Arrays.asList(n1);
    }

    public static int[] randomSparklineValues(int howMany, int min, int max) {
        int[] values = new int[howMany];

        for (int i = 0; i < howMany; i++) {
            values[i] = (int) (min + (Math.random() * (max - min)));
        }

        return values;
    }


}