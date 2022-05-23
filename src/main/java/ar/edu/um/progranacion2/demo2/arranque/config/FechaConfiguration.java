package ar.edu.um.progranacion2.demo2.arranque.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class FechaConfiguration {
    @Value("${params.fechaFormato}")
    protected String fechaFormat;

}
