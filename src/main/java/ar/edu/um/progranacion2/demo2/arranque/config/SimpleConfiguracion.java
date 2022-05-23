package ar.edu.um.progranacion2.demo2.arranque.config;

import ar.edu.um.progranacion2.demo2.arranque.pojo.OtraPersona;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
public class SimpleConfiguracion {

    @Value("#{'${params.multiple}'.split(',')}")
    protected List<String> multiples;

    @Bean
    @ConfigurationProperties(prefix = "params.objeto")
    public OtraPersona persona() {
        return new OtraPersona();
    }


}
