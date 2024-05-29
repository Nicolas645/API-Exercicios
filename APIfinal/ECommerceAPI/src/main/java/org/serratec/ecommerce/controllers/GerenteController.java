package org.serratec.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.Gerente;
import org.serratec.ecommerce.services.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @PostMapping
    public Gerente criarGerente(@RequestBody Gerente gerente) {
        return gerenteService.salvarGerente(gerente);
    }

    @GetMapping
    public List<Gerente> obterTodosGerentes() {
        return gerenteService.obterTodosGerentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gerente> obterGerentePorId(@PathVariable Long id) {
        Optional<Gerente> gerente = gerenteService.obterGerentePorId(id);
        if (gerente.isPresent()) {
            return ResponseEntity.ok(gerente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gerente> atualizarGerente(@PathVariable Long id, @RequestBody Gerente detalhesGerente) {
        Optional<Gerente> gerente = gerenteService.obterGerentePorId(id);
        if (gerente.isPresent()) {
            Gerente gerenteAtualizado = gerente.get();
            gerenteAtualizado.setNome(detalhesGerente.getNome());
            gerenteAtualizado.setEmail(detalhesGerente.getEmail());
            gerenteService.salvarGerente(gerenteAtualizado);
            return ResponseEntity.ok(gerenteAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGerente(@PathVariable Long id) {
        if (gerenteService.obterGerentePorId(id).isPresent()) {
            gerenteService.deletarGerente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
