package br.senai.sp.FelipeNicolas.guiadefilmes.model;

import org.springframework.http.HttpStatus;

public class Erro {
	private HttpStatus statusCode;
	private String mensagem ;
	private String exception;
	
	public Erro(HttpStatus status, String msg, String exc) {
		this.statusCode = status;
		this.mensagem = msg;
		this.exception = exc;
		
		
	}
}
