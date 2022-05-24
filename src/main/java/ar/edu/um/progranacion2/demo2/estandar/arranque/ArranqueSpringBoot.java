package ar.edu.um.progranacion2.demo2.estandar.arranque;


import ar.edu.um.progranacion2.demo2.estandar.pojo.Cliente;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Comida;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Empleado;
import ar.edu.um.progranacion2.demo2.estandar.servicio.Negocio;
import ar.edu.um.progranacion2.demo2.estandar.servicio.StockInicialException;
import ar.edu.um.progranacion2.demo2.service.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ArranqueSpringBoot {
    @Autowired
    protected NegocioService negocio;

    @PostConstruct
    public void arranque() {
        this.abrirNegocio();
        Cliente cli1 = new Cliente("Juan","Gomez");
        Cliente cli2 = new Cliente("Ana", "Corvalan");
        /*
        this.negocio.vender(cli1);
        this.negocio.vender(cli2);
        this.negocio.vender(cli1);
        this.negocio.vender(cli2);
        this.negocio.vender(cli2,"tarjeta");
        this.negocio.vender(cli2,"tarjeta");
        this.negocio.vender(cli1,"efectivo");
        this.negocio.vender(cli1,"efectivo");
        this.negocio.vender(cli1);
        this.negocio.vender(cli1);
        System.out.println("Vendiendo al cliente cli2");
        boolean resultado = this.negocio.vender(cli2);
        if(resultado) {
            System.out.println("Se le pudo vender con exito a cli2");
        }
        else {
            System.out.println("NO se le pudo vender con exito a cli2");
            System.out.println("Intentamos nuevamente");
            resultado = this.negocio.vender(cli2);
        }
        this.negocio.mostrarStock();

         */
    }

    public void abrirNegocio() {
        Empleado em1 = new Empleado("Fernando", "Villarreal");
        Empleado em2 = new Empleado("Daniel", "Quinteros");
        Comida menu1=null;
        Comida menu2=null;
        Comida menu3=null;
        Comida menu4=null;
        Comida menu5=null;
        try{
            menu1 = new Comida(1L,"Menu1","Pancho", 700D,3);
            menu2 = new Comida(2L,"Menu2","Lomo", 1200D,3);
            menu3 = new Comida(3L,"Menu3","Papas", 400D,3);
            menu4 = new Comida(4L,"Menu4","Pizza", 750D,3);
            menu5 = new Comida(5L,"Menu5","Helado", 350D,3);
        }
        catch (StockInicialException e) {
            System.out.println("LA comida que se esta creando tiene stock inicial negativo");
            System.out.println("Apagamos la aplicación");
            System.exit(1);
        }
        this.negocio.agregarEmpleado(em1);
        this.negocio.agregarEmpleado(em2);
        this.negocio.agregarComida(menu1);
        this.negocio.agregarComida(menu2);
        this.negocio.agregarComida(menu3);
        this.negocio.agregarComida(menu4);
        this.negocio.agregarComida(menu5);
    }
}
