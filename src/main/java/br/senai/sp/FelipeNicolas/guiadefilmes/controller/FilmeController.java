package br.senai.sp.FelipeNicolas.guiadefilmes.controller;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.Filme;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.FilmeRepository;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.TipoFilmeRepository;
import br.senai.sp.FelipeNicolas.guiadefilmes.util.FirebaseUtil;




@Controller
public class FilmeController {

	@Autowired
	private TipoFilmeRepository filmeTipo;
	@Autowired
	private FilmeRepository repository;
	@Autowired
	private FirebaseUtil fireUtil;

	@RequestMapping("formFilme")
	public String form(Model model) {
		model.addAttribute("tipo", filmeTipo.findAll());
		return "filmes/formFilme";
	}

	@RequestMapping(value ="salvarFilme",  method = RequestMethod.POST)
	public String salvarFilme(Filme filme, @RequestParam("filecapa") MultipartFile[] filecapa) {
		System.out.println(filecapa.length);
		// String para armazenar as URLS
		String fotos = filme.getFoto();
		//percorrer cada arquivo que foi submetido no formulario
		for (MultipartFile arquivo : filecapa) {
			if (arquivo.getOriginalFilename().isEmpty()) {
				// vai para o proximo arquivo
				continue;
			}
			//faz o upload para a nuvem e obtém a url gerada
			try {
				fotos += fireUtil.upload(arquivo) + ";";
				
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		filme.setFoto(fotos);

		repository.save(filme);
		return "redirect:formFilme";

	}
	@RequestMapping("listaFilme/{page}")
	public String listaFilmes(Model model, @PathVariable("page") int page ) {
		
				//cria um pageable com 6 elementos por pagina, ordenando os objetos
				PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC,"nome"));
				
				//cria a pagina atual atravez do repository 
				Page<Filme> pagina = repository.findAll(pageable);
				
				//descobrir o total de paginas 
				int totalPages = pagina.getTotalPages();
				
				//cria uma lista de inteiros para representar uma pagina
				List<Integer> pageNumbers = new ArrayList<Integer>();
				
				//preencher a lista com as páginas
				for(int i = 0; i < totalPages; i++ ) {
					pageNumbers.add(i+1);
				}
				
				//adiciona as variaveis na model
				model.addAttribute("filmes", pagina.getContent());
				model.addAttribute("paginaAtual", page);
				model.addAttribute("totalPaginas", totalPages);
				model.addAttribute("numPaginas", pageNumbers);
				
				//retorna para o HTML da lista
				return "filmes/listaFilme";
			}
	@RequestMapping("alterarFilme")
	public String alterarFilme(Model model, Long id) {
		
		Filme filme = repository.findById(id).get();
		
		model.addAttribute("filme", filme);
		
		return "forward:formFilme";
		
	}
	
	@RequestMapping("excluirFilme")
	public String excluirFilme(Long id) {
		Filme filme = repository.findById(id).get();
		if(filme.getFoto().length() > 0) {
			for(String foto : filme.verFotos() ) {
				fireUtil.deletar(foto);
			}
		}
		
		repository.delete(filme);
		return "redirect:listaFilme/1";
	}
	

	@RequestMapping("excluirFotoFilme")
	public String excluirFotoFilme(Long idFilme, int numFoto, Model model) {
		// busca a hospedagem no banco
		Filme filme= repository.findById(idFilme).get();
		// pega a String da foto a ser excluida
		String urlFoto = filme.verFotos()[numFoto];
		// excluir do firebase
		fireUtil.deletar(urlFoto);
		// "arranca" a foto da string fotos
		filme.setFoto(filme.getFoto().replace(urlFoto+";",""));
		// salva no BD o objeto rest
		repository.save(filme);
		//adciona o hotel da model
		model.addAttribute("filme", filme);
		
		return "forward:formFilme";
		
	}
}


