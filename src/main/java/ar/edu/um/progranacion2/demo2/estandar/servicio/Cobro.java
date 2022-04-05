package ar.edu.um.progranacion2.demo2.estandar.servicio;

import lombok.NoArgsConstructor;

public class Cobro {
    protected MedioPago medioPago;

    public Cobro() {
        this.medioPago = new MedioPago();
    }

    public void agregarTipoPago(TipoPago tipo) {
        this.medioPago.agregarMedioDePago(tipo);
    }

    public void cobrar(Double monto) {
        this.medioPago.cobrar(monto);
    }

    public void cobrar(Double monto, String mPago) {
        this.medioPago.cobrar(monto, mPago);
    }
}
