package br.senai.sp.FelipeNicolas.guiadefilmes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.FelipeNicolas.guiadefilmes.model.TipoFilme;




public interface TipoFilmeRepository extends PagingAndSortingRepository<TipoFilme, Long>{

	
	/*
	 * @Query("SELECT g FROM TipoFilme g WHERE g.palavraChave LIKE %:p%") public
	 * List<TipoFilme> procurarPalavraChave(@Param("p") String palavraChave);
	 */
} 