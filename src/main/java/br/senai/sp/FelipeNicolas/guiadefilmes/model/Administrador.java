package br.senai.sp.FelipeNicolas.guiadefilmes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.FelipeNicolas.guiadefilmes.util.HashUtil;
import lombok.Data;

//cria os gaters & seters 
@Data
//Mapeia a entidade para o JPA
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	// define a coluna email com um indice unico
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	//método set que aplica o hash na senha
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	//método que "seta" o hash na senha 
	public void setSenhaComHash(String hash) {
		this.senha = hash;
		
	}
}

