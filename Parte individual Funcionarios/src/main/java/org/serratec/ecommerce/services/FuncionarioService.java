package org.serratec.ecommerce.services;

import java.util.Optional;

import org.serratec.ecommerce.entities.Funcionario;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Transactional
	public void deletarFuncionario(Long id) {
		Funcionario funcionario = buscarFuncionarioPorIdEntity(id);
		funcionarioRepository.delete(funcionario);
	}


    private Funcionario buscarFuncionarioPorIdEntity(Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if (funcionario.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        return funcionario.get();
    }
}
