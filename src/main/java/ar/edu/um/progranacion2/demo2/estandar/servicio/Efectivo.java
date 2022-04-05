package ar.edu.um.progranacion2.demo2.estandar.servicio;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Efectivo implements TipoPago{
    protected String nombre;

    @Override
    public void cobrar(Double precio) {
        System.out.println(
            String.format("Se ha realizado el pago mediante efectivo de %8.2f pesos", precio));
    }

    @Override
    public void imprimirTicket() {
        System.out.println("Se imprime el ticket");
    }

    @Override
    public String obtenerNombre() {
        return this.nombre;
    }
}
