package org.serratec.ecommerce.services;

import org.serratec.ecommerce.dtos.ViaCepEnderecoDTO;
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
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://viacep.com.br/ws/" + cep + "/json";
        ViaCepEnderecoDTO viaCEPEnderecoDTO = restTemplate.getForObject(url, ViaCepEnderecoDTO.class);
        if (viaCEPEnderecoDTO != null && viaCEPEnderecoDTO.getCep() != null) {
            // Mapear os dados do DTO para um objeto Endereco
            Endereco endereco = new Endereco();
            endereco.setCep(viaCEPEnderecoDTO.getCep());
            endereco.setLogradouro(viaCEPEnderecoDTO.getLogradouro());
            endereco.setBairro(viaCEPEnderecoDTO.getBairro());
            endereco.setLocalidade(viaCEPEnderecoDTO.getLocalidade());
            endereco.setUf(viaCEPEnderecoDTO.getUf());
           
            return endereco;
        } else {
            throw new ResourceNotFoundException("Endereço não encontrado para o CEP fornecido: " + cep);
        }
    }

    @Override
    public Endereco inserir(Endereco endereco) {
        return repository.save(endereco);
    }
    
    @Override
    public void deletarEndereco(Long id) {
        repository.deleteById(id);
    }
}