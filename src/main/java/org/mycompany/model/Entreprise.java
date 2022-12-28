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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table
@Component
@JsonIdentityInfo(property = "id", generator = PropertyGenerator.class)
public class Entreprise {

	@Id
	private int id;
	private String nom;
	private int taille;
	private double capital;

	private int moyNotes;

	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "idNotes")
	private List<NotesEntreprise> listeNotesEntreprise;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Entreprise_Associations", joinColumns = @JoinColumn(name = "idTest"), inverseJoinColumns = @JoinColumn(name = "idEntreprise"))
	private List<Test> listeTests;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEntreprise")
	private List<Projet> listeProjets = new ArrayList<>();

	public Entreprise(int id, String nom, int taille, double capital, List<NotesEntreprise> listeNotes2,
			List<Test> listeTests, List<Projet> listeProjets) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
		int count = 1;
		int moyNote = 0;
		for (NotesEntreprise notes : listeNotes2) {
			if (notes.getEntreprise().getId() == id) {
				moyNotes += notes.getNote();
				count++;
			}
		}
		if (count == 1) {
			moyNotes = 0;
		} else {
			moyNotes = moyNotes / count;
		}
		moyNote = moyNotes;
		this.listeTests = listeTests;
		this.listeProjets = listeProjets;
		this.listeNotesEntreprise = listeNotes2;
	}

	public Entreprise(int id, String nom, int taille, double capital) {
		super();
		this.id = id;
		this.nom = nom;
		this.taille = taille;
		this.capital = capital;
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

	public int getMoyNotes() {
		return moyNotes;
	}

	public void setMoyNotes(int moyNotes) {
		this.moyNotes = moyNotes;
	}

	public List<NotesEntreprise> getListeNotesEntreprise() {
		return listeNotesEntreprise;
	}

	public void setListeNotesEntreprise(List<NotesEntreprise> listeNotesEntreprise) {
		this.listeNotesEntreprise = listeNotesEntreprise;
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
		return "Entreprise [id=" + id + ", nom=" + nom + ", taille=" + taille + ", capital=" + capital + ", moyNotes="
				+ moyNotes + ", listeNotesEntreprise=" + listeNotesEntreprise + ", listeTests=" + listeTests
				+ ", listeProjets=" + listeProjets + "]";
	}

}
