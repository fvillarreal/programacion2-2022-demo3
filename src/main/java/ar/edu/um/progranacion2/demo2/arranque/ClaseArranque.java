package ar.edu.um.progranacion2.demo2.arranque;

import ar.edu.um.progranacion2.demo2.arranque.config.ComplejaConfiguracion;
import ar.edu.um.progranacion2.demo2.arranque.config.SimpleConfiguracion;
import ar.edu.um.progranacion2.demo2.arranque.pojo.OtraPersona;
import ar.edu.um.progranacion2.demo2.arranque.servicio.PruebaServicio;
import ar.edu.um.progranacion2.demo2.web.rest.PublicUserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ClaseArranque {

    @Autowired
    protected PruebaServicio pruebaServicio;

    @Autowired
    protected SimpleConfiguracion simpleConfiguracion;

    @Autowired
    protected ComplejaConfiguracion complejaConfiguracion;

    private final Logger log = LoggerFactory.getLogger(ClaseArranque.class);

    @PostConstruct
    public void arranque() {
        System.out.println("Arranque");
        log.info("Arranque");
        System.out.println("Ahora: "+this.pruebaServicio.obtenerHora());
        log.info("Ahora: "+this.pruebaServicio.obtenerHora());
        log.info("Ahora formateado: "+this.pruebaServicio.obtenerHoraFormateado());
        log.info("Lista de parametros");
        for(String valor : this.simpleConfiguracion.getMultiples()) {
            log.info("Multiple: "+valor);
        }
        log.info("Persona: "+this.simpleConfiguracion.persona());
        log.info("Persona: "+this.complejaConfiguracion.getPersona());
        this.complejaConfiguracion.getPersona();
        log.info("Lista de Personas");
        for(OtraPersona pers : this.complejaConfiguracion.getPersonas()) {
            log.info("Persona: "+pers);
        }

    }
}
