package ar.edu.um.progranacion2.demo2.estandar.hilos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Callable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejecutor1 implements Callable<Boolean> {
    protected String nombre;

    @Override
    public Boolean call() throws Exception {
        System.out.println("Arranca el hilo "+this.nombre +" Como executor");
        for(int i=1; i<10; i++) {
            System.out.println(String.format("Iteracion nro: %d [%s]", i, this.nombre));
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Se termina el hilo "+this.nombre);
        return true;
    }
}
