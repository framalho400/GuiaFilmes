package br.senai.sp.FelipeNicolas.guiadefilmes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Publico;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.Administrador;
import br.senai.sp.FelipeNicolas.guiadefilmes.repository.AdminRepository;
import br.senai.sp.FelipeNicolas.guiadefilmes.util.HashUtil;


@Controller
public class AdmController {

	@Autowired
	private AdminRepository repository;

	@RequestMapping("formAdm")
	public String formAdm(Model model) {
		model.addAttribute("administrador");
		return "administrador/formAdmin";
	}

	// resquest mapping para salvar o adiminstrador do tipo POSt
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {

		// verifica se houveram erros na valodação

		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			//redireciona para o formulario
			return "redirect:formAdm";
		}
		//variavel para descobrir alteraçã ou exclusão
		boolean alteracao = admin.getId() != null ? true: false ;
		//verifica se a senha esta vaiza 
		if(admin.getSenha().equals(HashUtil.hash(""))){
			if(!alteracao) {
		//retira a parte do @
			String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
			//setar a parte na senha do admin
			admin.setSenha(parte);
			}else {
				//buscar senha atual no banco 
				String hash = repository.findById(admin.getId()).get().getSenha();
				//"setar" o hash na senha  
				admin.setSenhaComHash(hash);
			}
		}
			
		try {
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:" + admin.getId());

		} catch (Exception e) {
			// TODO: handle exception
			attr.addFlashAttribute("mensagemErro", "Verifique os campos..." + e.getMessage());
		}
		return "redirect:formAdm";
	}

	
	
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {

		// cria um pageble informando os parametros da pagina
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// cria um page de administrador atraves de um paramentro passado ao repository

		Page<Administrador> pagina = repository.findAll(pageable);
		
		// adiciona a model a lista com
		model.addAttribute("admins", pagina.getContent());
		
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
		return "administrador/listaAdm";

	}
	@RequestMapping("alterarAdm")
	public String alterarAdm(Long id, Model model) {
		Administrador admin = repository.findById(id).get();
		model.addAttribute("admins", admin);
		return "forward:formAdm";
	}
	
	

	@RequestMapping("excluirAdm")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listaAdmin/1";
		
	}
	@Publico
@RequestMapping("login")
	public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		//Busca o adm do banco
		Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		
		//verifica se existe
		if(admin == null) {
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha invalido(s)");
			return "redirect:/";
		}else {
			session.setAttribute("usuarioLogado", admin);
			return "redirect:/listaFilme/1";
		}
	}
public String logout(HttpSession session) {
	//invalida a sessão
	session.invalidate();
	//voltar para a pagina inicial
	return "redirect:/";
	
}
}

