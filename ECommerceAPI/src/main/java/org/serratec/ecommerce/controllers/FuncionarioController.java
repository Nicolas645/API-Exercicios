package org.serratec.ecommerce.controllers;


import java.util.List;

import org.serratec.ecommerce.entities.Funcionario;
import org.serratec.ecommerce.repositories.FuncionarioRepository;
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
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Endpoint para listar todos os funcionários
    @GetMapping
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    // Endpoint para adicionar um novo funcionário
    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Endpoint para obter um funcionário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> ResponseEntity.ok().body(funcionario))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um funcionário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionarioRepository.delete(funcionario);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar um funcionário pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(funcionarioDetails.getNome());
                    funcionario.setCargo(funcionarioDetails.getCargo());
                    funcionario.setSalario(funcionarioDetails.getSalario());
                    Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);
                    return ResponseEntity.ok(updatedFuncionario);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

