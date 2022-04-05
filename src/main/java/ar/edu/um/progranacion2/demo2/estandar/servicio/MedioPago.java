package ar.edu.um.progranacion2.demo2.estandar.servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
public class MedioPago {
    List<TipoPago> mediosDePago;

    public MedioPago() {
        this.mediosDePago = new ArrayList<>();
    }

    public void agregarMedioDePago(TipoPago tipo) {
        this.mediosDePago.add(tipo);
    }

    protected TipoPago obtenerTipoPagoRandom() {
        int numPagos = this.mediosDePago.size();
        Random r = new Random();
        int pos = r.nextInt(numPagos);
        TipoPago t = this.mediosDePago.get(pos);
        return t;
    }

    protected TipoPago obtenerTipoPagoPorNombre(String nombre) {
        for (TipoPago tPago : this.mediosDePago) {
            if(tPago.obtenerNombre().equals(nombre)) {
                return tPago;
            }
        }
        return null;
    }

    public void cobrar(Double monto) {
        TipoPago tipoPago = this.obtenerTipoPagoRandom();
        this.cobrarInterno(monto, tipoPago);
    }

    public void cobrar(Double monto, String mPago) {
        TipoPago tipoPago = this.obtenerTipoPagoPorNombre(mPago);
        this.cobrarInterno(monto, tipoPago);
    }

    protected void cobrarInterno(Double monto, TipoPago tipoPago) {
        System.out.println("Vamos a cobrarle al cliente");
        System.out.println(String.format("Vamos a usar como medio de pago: %s", tipoPago.obtenerNombre()));
        tipoPago.cobrar(monto);
        tipoPago.imprimirTicket();
    }
}
