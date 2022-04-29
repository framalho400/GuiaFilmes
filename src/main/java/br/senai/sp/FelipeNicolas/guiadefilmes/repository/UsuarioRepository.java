package br.senai.sp.FelipeNicolas.guiadefilmes.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.Filme;
import br.senai.sp.FelipeNicolas.guiadefilmes.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	/* public Filme findByUsuarioId(Long id); */
}
