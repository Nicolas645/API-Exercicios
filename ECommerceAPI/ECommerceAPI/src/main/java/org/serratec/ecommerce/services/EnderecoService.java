package org.serratec.ecommerce.services;

import org.serratec.ecommerce.entities.Endereco;

public interface EnderecoService {

    Endereco buscar(String cep);

    Endereco inserir(Endereco endereco);

	void deletarEndereco(Long id);

}

