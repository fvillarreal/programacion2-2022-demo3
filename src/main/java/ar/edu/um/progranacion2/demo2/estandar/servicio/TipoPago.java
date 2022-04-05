package ar.edu.um.progranacion2.demo2.estandar.servicio;

public interface TipoPago {
    public void cobrar(Double precio);
    public void imprimirTicket();
    public String obtenerNombre();
}
