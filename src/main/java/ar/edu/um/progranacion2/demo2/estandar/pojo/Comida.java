package ar.edu.um.progranacion2.demo2.estandar.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comida {
    protected String comida;
    protected String descripcion;
}
