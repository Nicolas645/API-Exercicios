package br.com.serratec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.serratec.Entity.LancamentoVendas;
import br.com.serratec.Service.LancamentoService;
import br.com.serratec.dto.LancamentoVendasMostrarDTO;
import br.com.serratec.exception.EmailException;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    @Autowired
    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }    

    @GetMapping
    public List<LancamentoVendasMostrarDTO> listar() {
        return lancamentoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoVendas> buscarPorId(@PathVariable Long id) {
        LancamentoVendas lancamento = lancamentoService.listarPorId(id);
        return ResponseEntity.ok(lancamento);
    }

    @PostMapping
    public ResponseEntity<LancamentoVendas> inserir(@RequestBody LancamentoVendas lancamento) throws EmailException {
        LancamentoVendas novoLancamento = lancamentoService.inserir(lancamento);
        return ResponseEntity.created(null).body(novoLancamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoVendas> atualizar(@PathVariable Long id, @RequestBody LancamentoVendas lancamentoAtualizado) {
        LancamentoVendas lancamento = lancamentoService.atualizar(id, lancamentoAtualizado);
        return ResponseEntity.ok(lancamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lancamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
