package br.senai.sp.FelipeNicolas.guiadefilmes.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Privado;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.Avaliacao;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.AvaliacaoRepository;

@RequestMapping("/api/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoRepository repository;
	@Privado
	@RequestMapping(value ="", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao (@RequestBody Avaliacao avaliacao){
		
		
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/api/avaliacao/"+avaliacao.getId())).body(avaliacao);
	}
}
