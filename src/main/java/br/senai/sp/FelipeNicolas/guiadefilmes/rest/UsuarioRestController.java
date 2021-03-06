package br.senai.sp.FelipeNicolas.guiadefilmes.rest;

import java.net.URI;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Privado;
import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Publico;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.Erro;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.Usuario;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		try {
			
		//insere o usuario no banco de dados
		repository.save(usuario);
		//retorana um codigo HTTP 201, informa  como acessar o recurso inserido
		//e acressenta no corpo da resposta o objeto inserido
		return ResponseEntity.created(URI.create("/api/usuario/"+usuario.getId())).body(usuario);
		
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro Duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}catch (Exception e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizarUsuario
									(@RequestBody Usuario usuario, @PathVariable("id")Long id){
	//valida????o do ID
		if(id != usuario.getId()) {
			throw new RuntimeException("ID Invalido");
			
				
		}
		repository.save(usuario);
		return ResponseEntity.ok().build();
}
	@Privado
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE )
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
		
		
	}
}
