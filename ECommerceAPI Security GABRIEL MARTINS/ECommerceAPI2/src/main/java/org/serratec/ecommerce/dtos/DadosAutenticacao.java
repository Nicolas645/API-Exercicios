package org.serratec.ecommerce.dtos;

import jakarta.validation.Valid;

public record DadosAutenticacao(@Valid String login, String senha) {

}
