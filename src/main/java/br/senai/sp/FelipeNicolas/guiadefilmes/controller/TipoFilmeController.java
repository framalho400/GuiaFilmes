package br.senai.sp.FelipeNicolas.guiadefilmes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.TipoFilme;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.TipoFilmeRepository;


@Controller
public class TipoFilmeController {

	@Autowired
	private TipoFilmeRepository repository;

	@RequestMapping("formGenero")
	public String formfilmes(Model model) {
		model.addAttribute("genero");
		return "filmes/formTipoFilmes";
	}
	@RequestMapping(value = "salvarGenero", method = RequestMethod.POST)
	public String salvarGenero(@Valid TipoFilme filme, BindingResult result, RedirectAttributes attr) {

		// verifica se houveram erros na validação

		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			//redireciona para o formulario
			return "redirect:formGenero";
	
			

		} else{
			repository.save(filme);
			attr.addFlashAttribute("mensagemSucesso", "Genero cadastrado com sucesso. ID:" + filme.getId());
			return "redirect:formGenero";
		}
		
	
	}

	@RequestMapping("listaGenero/{page}")
	public String listaGenero(Model model, @PathVariable("page") int page) {

		// cria um pageble informando os parametros da pagina
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "genero"));
		// cria um page de administrador atraves de um paramentro passado ao repository

		Page<TipoFilme> pagina = repository.findAll(pageable);
		
		// adiciona a model a lista com
		model.addAttribute("genero", pagina.getContent());
		
		// variavel para o total de paginas
		int totalPages = pagina.getTotalPages();
		
		// cria um list de inteiros para arnazenar os numeros das paginas
		List<Integer> numPaginas = new ArrayList<Integer>();
		
		// peencher o list com as paginas
		for (int i = 1; i <= totalPages; i++) {
			
			numPaginas.add(i);
		}
		// adiciona os valores a pagnia
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		return "filmes/listaTipoFilmes";

	}
	@RequestMapping("alterarGenero")
	public String alterarGenero(Long id, Model model) {
		TipoFilme genero = repository.findById(id).get();
		model.addAttribute("genero", genero);
		return "forward:formGenero";
	}

	@RequestMapping("excluirGenero")
	public String excluirGenero(Long id) {
		repository.deleteById(id);
		return "redirect:listaGenero/1";
	}
	
	

	 

	/*
	 * @RequestMapping("palavraChave") public String palavraChave(String genero,
	 * Model model) {
	 * 
	 * 
	 * model.addAttribute("genero", repository.procurarPalavraChave(genero));
	 * 
	 * if (genero == null) { model.addAttribute("msg", "Genero não encontrado.");
	 * 
	 * }
	 * 
	 * 
	 * return "listaGenero/1";
	 * 
	 * }
	 */
}

