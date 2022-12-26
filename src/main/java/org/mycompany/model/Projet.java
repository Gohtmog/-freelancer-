package org.mycompany.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class Projet {

	@Id
	private int id;
	private String intitule;
	private double salaire;
	private double duree;
	private int tailleEquipe;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEntreprise")
	private Entreprise entreprise;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Projet_Candidat_Associations", joinColumns = @JoinColumn(name = "idCandidat"), inverseJoinColumns = @JoinColumn(name = "idProjet"))
	private List<Candidat> listeCandidats;

	public Projet() {
		super();
	}

	public Projet(int id, String intitule, double salaire, double duree, int tailleEquipe) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.salaire = salaire;
		this.duree = duree;
		this.tailleEquipe = tailleEquipe;
	}

	public Projet(int id, String intitule, double salaire, double duree, int tailleEquipe, Entreprise entreprise,
			List<Candidat> listeCandidats) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.salaire = salaire;
		this.duree = duree;
		this.tailleEquipe = tailleEquipe;
		this.entreprise = entreprise;
		this.listeCandidats = listeCandidats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public double getSalaire() {
		return salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public int getTailleEquipe() {
		return tailleEquipe;
	}

	public void setTailleEquipe(int tailleEquipe) {
		this.tailleEquipe = tailleEquipe;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public List<Candidat> getListeCandidats() {
		return listeCandidats;
	}

	public void setListeCandidats(List<Candidat> listeCandidats) {
		this.listeCandidats = listeCandidats;
	}

	@Override
	public String toString() {
		return "Projet [id=" + id + ", intitule=" + intitule + ", salaire=" + salaire + ", duree=" + duree
				+ ", tailleEquipe=" + tailleEquipe + ", entreprise=" + entreprise + ", listeCandidats=" + listeCandidats
				+ "]";
	}

}
