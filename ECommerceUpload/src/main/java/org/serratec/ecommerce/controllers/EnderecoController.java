package org.serratec.ecommerce.controllers;

import org.serratec.ecommerce.entities.Endereco;
import org.serratec.ecommerce.services.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoServiceImpl service;

    @GetMapping("{cep}")
    public ResponseEntity<Endereco> buscarCep(@PathVariable String cep) {
        Endereco endereco = service.buscar(cep);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(endereco);
    }

}
