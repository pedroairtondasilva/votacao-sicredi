package com.desafio.votacao.domain.exception;

public class NegocioExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioExcpetion(String message) {
		super(message);
	}
	
	public NegocioExcpetion(java.lang.String message, java.lang.Throwable cause) {
  		super(message, cause);
	}

}
