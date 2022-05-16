package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//Chiave posta sull'id e non sul nome, in quanto potrebbero esserci varianti dello 
	//stesso Ingrediente basate sulla descrizione
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String origine;
	@Column(nullable = false)
	private String descrizione;
	
	
	public Ingrediente(String nome, String origine, String descrizione) {
		this.nome = nome;
		this.origine = origine;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getOrigine() {
		return origine;
	}


	public void setOrigine(String origine) {
		this.origine = origine;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
