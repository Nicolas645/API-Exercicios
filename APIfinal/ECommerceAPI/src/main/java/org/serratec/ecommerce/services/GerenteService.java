package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.Gerente;
import org.serratec.ecommerce.repositories.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenteService {
    @Autowired
    private GerenteRepository gerenteRepository;

    public Gerente salvarGerente(Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public List<Gerente> obterTodosGerentes() {
        return gerenteRepository.findAll();
    }

    public Optional<Gerente> obterGerentePorId(Long id) {
        return gerenteRepository.findById(id);
    }

    public void deletarGerente(Long id) {
        gerenteRepository.deleteById(id);
    }
}
