package br.senai.sp.FelipeNicolas.guiadefilmes.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.Filme;


public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long>{
	public Filme findByGeneroId(Long id);
	
}
