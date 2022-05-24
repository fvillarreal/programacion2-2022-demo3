package ar.edu.um.progranacion2.demo2.domain.DTO;

import ar.edu.um.progranacion2.demo2.estandar.pojo.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {
    protected Cliente cliente;
    protected Integer menu;
    protected String tipoPago;
}
