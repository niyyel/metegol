package com.pixel.web.metegol;
import com.pixel.web.metegol.model.PartidoClass;

import java.util.ArrayList;
import java.util.List;

public class HourRepository {
    private static HourRepository repository = new HourRepository();

    public static HourRepository getInstance() {
        return repository;
    }

    public static List<PartidoClass> getArrayHour() {

        List<PartidoClass> lstPartido = new ArrayList<>();

        lstPartido.add(new PartidoClass(1,"8:00","08:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(2,"8:30","09:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(3,"9:00","09:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(4,"9:30","10:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(5,"10:00","10:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(6,"10:30","11:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(7,"11:00","11:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(8,"11:30","12:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(9,"12:00","12:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(10,"12:30","13:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(11,"13:00","13:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(12,"13:30","14:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(13,"14:00","14:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(14,"14:30","15:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(15,"15:00","15:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(16,"15:30","16:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(17,"16:00","16:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(18,"16:30","17:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(18,"17:00","17:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(20,"17:30","18:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(21,"18:00","18:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(22,"18:30","19:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(23,"19:00","19:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(24,"19:30","20:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(25,"20:00","20:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(26,"20:30","21:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(27,"21:00","21:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(28,"21:30","22:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(29,"22:00","22:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoClass(30,"22:30","23:00", "DISPONIBLE", R.drawable.reservado));

        return lstPartido;
    }
    public static List<PartidoClass> getArrayHourReserva() {

        List<PartidoClass> lstPartido = new ArrayList<>();

        lstPartido.add(new PartidoClass(1,"8:00","08:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(2,"8:30","09:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(3,"9:00","09:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(4,"9:30","10:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(5,"10:00","10:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(6,"10:30","11:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(7,"11:00","11:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(8,"11:30","12:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(9,"12:00","12:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(10,"12:30","13:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(11,"13:00","13:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(12,"13:30","14:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(13,"14:00","14:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(14,"14:30","15:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(15,"15:00","15:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(16,"15:30","16:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(17,"16:00","16:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(18,"16:30","17:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(18,"17:00","17:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(20,"17:30","18:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(21,"18:00","18:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(22,"18:30","19:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(23,"19:00","19:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(24,"19:30","20:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(25,"20:00","20:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(26,"20:30","21:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(27,"21:00","21:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(28,"21:30","22:00", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(29,"22:00","22:30", "---", R.drawable.reservado));
        lstPartido.add(new PartidoClass(30,"22:30","23:00", "---", R.drawable.reservado));

        return lstPartido;
    }

}
