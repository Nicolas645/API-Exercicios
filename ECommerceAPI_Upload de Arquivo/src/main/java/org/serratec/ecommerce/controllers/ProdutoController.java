package org.serratec.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.serratec.ecommerce.dtos.ProdutoDTO;
import org.serratec.ecommerce.entities.Foto;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.services.FotoServiceImpl;
import org.serratec.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoServiceImpl fotoService;

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProdutoDTO> criarProduto(@RequestPart Produto produto, @RequestPart MultipartFile file)
			throws IOException {
		Produto novoProduto = produtoService.criarProduto(produto);
		fotoService.inserir(novoProduto, file);
		ProdutoDTO produtoDTO = produtoService.adicionarImagemUri(novoProduto);
		return new ResponseEntity<>(produtoDTO, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
		List<ProdutoDTO> produtos = produtoService.listarProdutos();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
		Produto produto = produtoService.buscarProdutoPorId(id);
		return ResponseEntity.ok(produto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		Produto produtoAtualizada = produtoService.atualizarProduto(id, produto);
		return ResponseEntity.ok(produtoAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarPorIdProduto(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", foto.getTipo());
		headers.add("Content-length", String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/foto", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProdutoDTO> atualizarFoto(@PathVariable Long id, @RequestPart MultipartFile file)
			throws IOException {
		Produto produto = produtoService.buscarProdutoPorId(id);
		fotoService.atualizar(produto, file);
		ProdutoDTO produtoDTO = produtoService.adicionarImagemUri(produto);
		return ResponseEntity.ok(produtoDTO);
	}

	@DeleteMapping("/{id}/foto")
	public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
		fotoService.deletarPorIdProduto(id);
		return ResponseEntity.noContent().build();
	}
}
