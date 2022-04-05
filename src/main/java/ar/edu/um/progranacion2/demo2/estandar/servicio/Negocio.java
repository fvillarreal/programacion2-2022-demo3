package ar.edu.um.progranacion2.demo2.estandar.servicio;

import ar.edu.um.progranacion2.demo2.estandar.pojo.Cliente;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Comida;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Empleado;
import liquibase.pro.packaged.E;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Negocio {
    protected List<Empleado> empleados;
    protected List<Comida> menu;
    protected Cobro cobro;

    public Negocio() {
        System.out.println("Arrancando negocio");
        this.empleados=new ArrayList<>();
        this.menu=new ArrayList<>();
        this.cobro = new Cobro();
        Tarjeta visa = new Tarjeta("VISA","tarjeta");
        Efectivo efectivo = new Efectivo("efectivo");
        this.cobro.agregarTipoPago(visa);
        this.cobro.agregarTipoPago(efectivo);
    }

    public void agregarEmpleado(Empleado e) {
        this.empleados.add(e);
    }

    public void agregarComida(Comida c) {
        this.menu.add(c);
    }

    public Empleado obtenerEmpleadoRandom() {
        int numEmpleados = this.empleados.size();
        Random r = new Random();
        int pos = r.nextInt(numEmpleados);
        Empleado e = this.empleados.get(pos);
        return e;
    }

    public Comida obtenerComidaRandom() {
        int numComidas = this.menu.size();
        Random r = new Random();
        int pos = r.nextInt(numComidas);
        Comida c = this.menu.get(pos);
        return c;
    }

    public void vender(Cliente c) {
        System.out.println("Vamos a atender al cliente: "+c.toString());
        Empleado em = this.obtenerEmpleadoRandom();
        Comida com = this.obtenerComidaRandom();
        System.out.println("El empleado que atiende es: "+em.toString());
        System.out.println("La opcion seleccionada de comida del menu es: "+com.toString());
        this.cobro.cobrar(com.getPrecio());
        System.out.println("La comida ha sido entregada");
        System.out.println(String.format("El cliente %s se ha retirado del local", c));
        System.out.println("--------------------------------------------------------------------------------");
    }

    public void vender(Cliente c, String mPago) {
        System.out.println("Vamos a atender al cliente: "+c.toString());
        Empleado em = this.obtenerEmpleadoRandom();
        Comida com = this.obtenerComidaRandom();
        System.out.println("El empleado que atiende es: "+em.toString());
        System.out.println("La opcion seleccionada de comida del menu es: "+com.toString());
        this.cobro.cobrar(com.getPrecio(), mPago);
        System.out.println("La comida ha sido entregada");
        System.out.println(String.format("El cliente %s se ha retirado del local", c));
        System.out.println("--------------------------------------------------------------------------------");
    }
}
