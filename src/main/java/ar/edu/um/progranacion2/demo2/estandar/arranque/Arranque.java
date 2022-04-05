package ar.edu.um.progranacion2.demo2.estandar.arranque;

import ar.edu.um.progranacion2.demo2.estandar.pojo.Cliente;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Comida;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Empleado;
import ar.edu.um.progranacion2.demo2.estandar.servicio.Negocio;
import liquibase.pro.packaged.E;

public class Arranque {
    protected Negocio negocio;

    public static void main(String[] args) {
        Arranque arranque = new Arranque();
        arranque.inicio();
    }

    public void inicio() {
        this.abrirNegocio();
        Cliente cli1 = new Cliente("Juan");
        Cliente cli2 = new Cliente("Ana");
        this.negocio.vender(cli1);
        this.negocio.vender(cli2);
        this.negocio.vender(cli1);
        this.negocio.vender(cli2);
        this.negocio.vender(cli2,"tarjeta");
        this.negocio.vender(cli2,"tarjeta");
        this.negocio.vender(cli1,"efectivo");
        this.negocio.vender(cli1,"efectivo");
    }

    public void abrirNegocio() {
        Empleado em1 = new Empleado("Fernando", "Villarreal");
        Empleado em2 = new Empleado("Daniel", "Quinteros");
        Comida menu1 = new Comida("Menu1","Pancho", 700D);
        Comida menu2 = new Comida("Menu2","Lomo", 1200D);
        Comida menu3 = new Comida("Menu3","Papas", 400D);
        Comida menu4 = new Comida("Menu4","Pizza", 750D);
        Comida menu5 = new Comida("Menu5","Helado", 350D);
        this.negocio = new Negocio();
        this.negocio.agregarEmpleado(em1);
        this.negocio.agregarEmpleado(em2);
        this.negocio.agregarComida(menu1);
        this.negocio.agregarComida(menu2);
        this.negocio.agregarComida(menu3);
        this.negocio.agregarComida(menu4);
        this.negocio.agregarComida(menu5);
    }
}
