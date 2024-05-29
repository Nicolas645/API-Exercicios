package org.serratec.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.serratec.ecommerce.dtos.ClienteDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.services.ClienteServiceImpl;
import org.serratec.ecommerce.services.FotoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private FotoServiceImpl fotoService;

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ClienteDTO> criarClienteComFoto(@RequestPart MultipartFile file, @RequestPart Cliente cliente) throws IOException {
        ClienteDTO novoCliente = clienteService.inserirComFoto(cliente, file);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@Valid @PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteAtualizada = clienteService.atualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
        byte[] foto = fotoService.buscarPorIdCliente(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "image/jpeg"); // Ou qualquer outro tipo que você esteja usando
        headers.add("Content-length", String.valueOf(foto.length));
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    }
}
