package ar.edu.um.progranacion2.demo2.estandar.pojo;

import ar.edu.um.progranacion2.demo2.estandar.servicio.NoMasComidaException;
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
    protected Double precio;
    protected Integer stock;

    public void descontarStock() throws NoMasComidaException {
        if(this.stock == 0) {
            throw new NoMasComidaException("No se puede vender esa comida porque no hay mas stock");
        }
        this.stock--;
    }
}
