package org.serratec.ecommerce.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.serratec.ecommerce.entities.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Cliente cliente) {
		try {
		    var algorithm = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("bancoapi")
		        .withSubject(cliente.getLogin())
		        .withExpiresAt(DataExpiracao())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Erro na geração de token", exception);
		}
	}

	public String getSubject(String TokenJWT) {
		try {
		    var algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		        .withIssuer("bancoapi")
		        .build()
		    	.verify(TokenJWT)
		    	.getSubject();
		  
		} catch (JWTVerificationException exception){
		  throw new RuntimeException("Token invalido ou expirado!");  
		}
		
	}
	
	private Instant DataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
