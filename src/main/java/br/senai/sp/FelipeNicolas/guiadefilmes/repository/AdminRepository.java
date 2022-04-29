package br.senai.sp.FelipeNicolas.guiadefilmes.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	public Administrador findByEmailAndSenha(String email, String senha);

}
