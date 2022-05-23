package ar.edu.um.progranacion2.demo2.arranque.servicio;


import ar.edu.um.progranacion2.demo2.arranque.config.FechaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PruebaServicio {
    @Autowired
    FechaConfiguration fechaConfiguration;

    public String obtenerHora() {
        Long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        Date date = new Date(currentTime);
        String ahora = simpleDateFormat.format(date);
        return ahora;
    }

    public String obtenerHoraFormateado() {
        Long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.fechaConfiguration.getFechaFormat());
        Date date = new Date(currentTime);
        String ahora = simpleDateFormat.format(date);
        return ahora;
    }
}
