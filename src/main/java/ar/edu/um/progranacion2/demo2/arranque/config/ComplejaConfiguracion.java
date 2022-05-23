package ar.edu.um.progranacion2.demo2.arranque.config;

import ar.edu.um.progranacion2.demo2.arranque.pojo.OtraPersona;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "params2")
public class ComplejaConfiguracion {
    protected OtraPersona persona;
    protected List<OtraPersona> personas;
    protected String url;
}
