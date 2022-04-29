package br.senai.sp.FelipeNicolas.guiadefilmes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Filme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private TipoFilme genero;
	private String nome;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String duracao;
	private String site;
	private String previa;
	private String atores;
	private String dubladores;
	private String foto;
	private String avaliacao;
	private String idioma;
	private String faixaetaria;
	
	public String[]verFotos(){
		return foto.split(";");
	}
	
	

}
