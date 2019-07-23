package com.pixel.web.metegol;
import java.util.ArrayList;
import java.util.List;

public class HourRepository {
    private static HourRepository repository = new HourRepository();

    public static HourRepository getInstance() {
        return repository;
    }

    public static List<PartidoModelo> getArrayHour() {

        List<PartidoModelo> lstPartido = new ArrayList<>();

        lstPartido.add(new PartidoModelo("8:00","08:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("8:30","09:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("9:00","09:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("9:30","10:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("10:00","10:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("10:30","11:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("11:00","11:30", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("11:30","12:00", "DISPONIBLE", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("12:00","12:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("12:30","13:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("13:00","13:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("13:30","14:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("14:00","14:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("14:30","15:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("15:00","15:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("15:30","16:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("16:00","16:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("16:30","17:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("17:00","17:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("17:30","18:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("18:00","18:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("18:30","19:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("19:00","19:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("19:30","20:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("20:00","20:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("20:30","21:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("21:00","21:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("21:30","22:00", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("22:00","22:30", "RESERVADO", R.drawable.reservado));
        lstPartido.add(new PartidoModelo("22:30","23:00", "RESERVADO", R.drawable.reservado));

        return lstPartido;
    }

}
