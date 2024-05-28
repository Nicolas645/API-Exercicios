package org.serratec.ecommerce.services;

import org.serratec.ecommerce.entities.Endereco;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Override
    public Endereco buscar(String cep) {
        Endereco endereco = repository.findByCep(cep);
        if (endereco != null) {
            return endereco;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://viacep.com.br/ws/" + cep + "/json";
            Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
            if (enderecoViaCep != null && enderecoViaCep.getCep() != null) {
                
                Endereco enderecoExistente = repository.findByCep(cep);
                if (enderecoExistente != null) {
                    return enderecoExistente;
                } else {
                    return repository.save(enderecoViaCep);
                }
            } else {
                throw new ResourceNotFoundException("CEP não encontrado!");
            }
        }
    }


    @Override
    public Endereco inserir(Endereco endereco) {
        return repository.save(endereco);
    }
}
