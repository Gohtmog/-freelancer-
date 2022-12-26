package org.mycompany.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class Entreprise {

	@Id
	private int id;
	private String nom;
	private int taille;
	private double capital;
	private double note;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Entreprise_Associations", joinColumns = @JoinColumn(name = "idTest"), inverseJoinColumns = @JoinColumn(name = "idEntreprise"))
	private List<Test> listeTests;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEntreprise")
	private List<Projet> listeProjets = new ArrayList<>();

	public Entreprise(int id, String nom, int taille, double capital, double note, List<Test> listeTests,
			List<Projet> listeProjets) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
		this.note = note;
		this.listeTests = listeTests;
		this.listeProjets = listeProjets;
	}

	public Entreprise(int id, String nom, int taille, double capital, double note) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
		this.note = note;
	}

	public Entreprise() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public List<Test> getListeTests() {
		return listeTests;
	}

	public void setListeTests(List<Test> listeTests) {
		this.listeTests = listeTests;
	}

	public List<Projet> getListeProjets() {
		return listeProjets;
	}

	public void setListeProjets(List<Projet> listeProjets) {
		this.listeProjets = listeProjets;
	}

	@Override
	public String toString() {
		return "Entreprise [id=" + id + ", nom=" + nom + ", taille=" + taille + ", capital=" + capital + ", note="
				+ note + ", listeTests=" + listeTests + ", listeProjets=" + listeProjets + "]";
	}

}
