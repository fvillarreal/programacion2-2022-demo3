package ar.edu.um.progranacion2.demo2.estandar.arranque;

import ar.edu.um.progranacion2.demo2.estandar.hilos.Ejecutor1;
import ar.edu.um.progranacion2.demo2.estandar.hilos.Hilo1;
import ar.edu.um.progranacion2.demo2.estandar.hilos.Hilo2;
import ar.edu.um.progranacion2.demo2.estandar.hilos.Hilo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ArrancaHilos {
    public static void main(String[] args) {
        ArrancaHilos a = new ArrancaHilos();
        //a.arranqueHilos();
        //a.arranqueExecutor();
        //a.arranqueExecutor2();
        a.arranqueExecutor3();
    }

    public void arranqueHilos() {
        Thread h1 = new Hilo1("hilo1");
        h1.start();
        Hilo2 h2_run = new Hilo2("hilo2");
        Thread h2 = new Thread(h2_run);
        h2.start();
        System.out.println("HOLA!");
    }

    public void arranqueExecutor() {
        System.out.println("Creamos el ExecutorService");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String nombre;
        for(int i=0; i<20; i++) {
            nombre = String.format("Executor%d",i);
            Ejecutor1 ej = new Ejecutor1(nombre);
            executorService.submit(ej);
        }
        executorService.shutdown();
        System.out.println("Shutdown del executorService");
    }

    public void arranqueExecutor2() {
        System.out.println("Creamos el ExecutorService");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Ejecutor1 ej = new Ejecutor1("Ejecutor");
        executorService.schedule(ej,4000L, TimeUnit.MILLISECONDS);
        System.out.println("Agregado el executor con demora");
        executorService.shutdown();
    }


    public void arranqueExecutor3() {
        System.out.println("Creamos el ExecutorService");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Hilo3 ej2 = new Hilo3("Ejecutor recurrente");
        System.out.println("Agregado el executor con demora");
        executorService.scheduleAtFixedRate(ej2,0, 2000, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
