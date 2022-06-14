package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Piatto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descrizione;
	
	
	@Column(nullable = true)
	private String immagine;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ingrediente> ingredienti;

	public Piatto(String nome, String descrizione, List<Ingrediente> ingredienti) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.ingredienti = ingredienti;
	}
	
	public Piatto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public Long getId() {
		return id;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public void updateValues(Piatto piatto) {
		this.nome = piatto.getNome();
		this.descrizione = piatto.getDescrizione();		
	}
	
	

}
