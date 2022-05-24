package ar.edu.um.progranacion2.demo2.web.rest;

import ar.edu.um.progranacion2.demo2.domain.DTO.VentaDTO;
import ar.edu.um.progranacion2.demo2.estandar.pojo.Cliente;
import ar.edu.um.progranacion2.demo2.service.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/venta")
public class VentaController {

    @Autowired
    protected NegocioService negocioService;

    @PostMapping("/detallado")
    public ResponseEntity<String> vender_detallado(@Valid @RequestBody VentaDTO ventaDto) {
        boolean resultado = negocioService.vender(ventaDto.getCliente(), ventaDto.getTipoPago());
        if(resultado) {
            return new ResponseEntity<>("Vendido", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Error en la venta", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/simple")
    public ResponseEntity<String> vender_simple(@Valid @RequestBody Cliente cliente) {
        boolean resultado = negocioService.vender(cliente);
        if(resultado) {
            return new ResponseEntity<>("Vendido", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Error en la venta", HttpStatus.BAD_REQUEST);
        }
    }

}
