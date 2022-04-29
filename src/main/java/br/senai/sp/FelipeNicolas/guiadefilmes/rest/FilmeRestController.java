package br.senai.sp.FelipeNicolas.guiadefilmes.rest;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Publico;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.Filme;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.FilmeRepository;

@RestController
@RequestMapping("/api/filme")
public class FilmeRestController {
	@Autowired
	private FilmeRepository repository;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Filme> getFilme() {
		return repository.findAll();
	}

	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Filme> getFilme(@PathVariable("id") Long id) {
		Optional<Filme> opicional = repository.findById(id);

		if (opicional.isPresent()) {
			return ResponseEntity.ok(opicional.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@Publico
	@RequestMapping(value = "tipo/{id}", method = RequestMethod.GET)
	public Filme getGeneroId(@PathVariable("id") Long idTipo) {

		return repository.findByGeneroId(idTipo);
	}

}
