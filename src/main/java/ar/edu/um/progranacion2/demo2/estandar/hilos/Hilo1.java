package ar.edu.um.progranacion2.demo2.estandar.hilos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hilo1 extends Thread{
    protected String nombre;

    @Override
    public void run() {
        System.out.println("Arranca el hilo "+this.nombre + "Como thread");
        for(int i=1; i<10; i++) {
            System.out.println(String.format("Iteracion nro: %d [%s]", i, this.nombre));
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Se termina el hilo "+this.nombre);
    }
}
