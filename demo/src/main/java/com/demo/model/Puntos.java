package com.demo.model;



public class Puntos {
	

	private int idPartida_;
	private Usuario idUsuario_;
	private int pGracioso_;
	private int pListo_;
	private int pDibujo_;
	private boolean votadoGracioso;
	private boolean votadoListo;
	private boolean votadoDibujo;
	private boolean consultado;
	
	
	
	public Puntos (int idPartida, Usuario idUsuario){
		this.idPartida_=idPartida;
		this.idUsuario_=idUsuario;
		this.pGracioso_=0;
		this.pDibujo_=0;
		this.pListo_=0;
		this.votadoDibujo = false;
		this.votadoListo = false;
		this.votadoGracioso = false;
		this.consultado = false;
		 
	}
	public Puntos() {
		
	}
	
	public boolean isConsultado() {
		return consultado;
	}
	public void setConsultado(boolean consultado) {
		this.consultado = consultado;
	}
	
	public void sumarPGracioso(int puntos) {
		this.pGracioso_ += puntos;
	}
	public void sumarPListo(int puntos) {
		this.pListo_ += puntos;
	}
	public void sumarPDibujo(int puntos) {
		this.pDibujo_ += puntos;
	}
	public int getIdPartida_() {
		return idPartida_;
	}
	public void setIdPartida_(int idPartida_) {
		this.idPartida_ = idPartida_;
	}
	public Usuario getIdUsuario_() {
		return idUsuario_;
	}
	public void setIdUsuario_(Usuario idUsuario_) {
		this.idUsuario_ = idUsuario_;
	}
	public int getpGracioso_() {
		return pGracioso_;
	}
	public void setpGracioso_(int pGracioso_) {
		this.pGracioso_ = pGracioso_;
	}
	public int getpListo_() {
		return pListo_;
	}
	public void setpListo_(int pListo_) {
		this.pListo_ = pListo_;
	}
	public int getpDibujo_() {
		return pDibujo_;
	}
	public void setpDibujo_(int pDibujo_) {
		this.pDibujo_ = pDibujo_;
	}
	public boolean isVotadoGracioso() {
		return votadoGracioso;
	}
	public void setVotadoGracioso(boolean votadoGracioso) {
		this.votadoGracioso = votadoGracioso;
	}
	public boolean isVotadoListo() {
		return votadoListo;
	}
	public void setVotadoListo(boolean votadoListo) {
		this.votadoListo = votadoListo;
	}
	public boolean isVotadoDibujo() {
		return votadoDibujo;
	}
	public void setVotadoDibujo(boolean votadoDibujo) {
		this.votadoDibujo = votadoDibujo;
	}
	public boolean votadoTodo() {
		return votadoDibujo && votadoListo && votadoGracioso;
	}
	
	
	public int calcularEstrellas() {
		int result=0;
		if(pDibujo_>=2) {result++;
			if(pDibujo_>=5) {result++;
				if(pDibujo_>=8) {result++;}}}
		if(pGracioso_>=2) {result++;
		if(pGracioso_>=5) {result++;
			if(pGracioso_>=8) {result++;}}}
		if(pListo_>=2) {result++;
		if(pListo_>=5) {result++;
			if(pListo_>=8) {result++;}}}
		return result;
	}
	public int calcularMonedas() {
		return pDibujo_+pGracioso_+pListo_;
	}
	

}
