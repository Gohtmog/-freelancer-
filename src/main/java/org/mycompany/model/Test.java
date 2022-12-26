package org.mycompany.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class Test {
	@Id
	private int id;
	private String sujet;
	private Boolean valide;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Candidat_Associations", joinColumns = @JoinColumn(name = "idCandidat"), inverseJoinColumns = @JoinColumn(name = "idTest"))
	private List<Candidat> listeCandidats;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Entreprise_Associations", joinColumns = @JoinColumn(name = "idEntreprise"), inverseJoinColumns = @JoinColumn(name = "idTest"))
	private List<Entreprise> listeEntreprises;

	

	public Test() {
		super();
	}
	public Test(int id, String sujet, Boolean valide, List<Candidat> listeCandidats,
			List<Entreprise> listeEntreprises) {
		super();
		this.id = id;
		this.sujet = sujet;
		this.valide = valide;
		this.listeCandidats = listeCandidats;
		this.listeEntreprises = listeEntreprises;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public List<Candidat> getListeCandidats() {
		return listeCandidats;
	}

	public void setListeCandidats(List<Candidat> listeCandidats) {
		this.listeCandidats = listeCandidats;
	}

	public List<Entreprise> getListeEntreprises() {
		return listeEntreprises;
	}

	public void setListeEntreprises(List<Entreprise> listeEntreprises) {
		this.listeEntreprises = listeEntreprises;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", sujet=" + sujet + ", valide=" + valide + ", listeCandidats=" + listeCandidats
				+ ", listeEntreprises=" + listeEntreprises + "]";
	}

	
	
}
