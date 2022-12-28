package org.mycompany.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table
@Component
@JsonIdentityInfo(property = "id", generator = PropertyGenerator.class)
public class Test {
	@Id
	private int id;
	private String sujet;
	private Boolean valide;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCandidat")
	private Candidat candidat;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEntreprise")
	private Entreprise entreprise;

	public Test() {
		super();
	}

	public Test(int id, String sujet, Boolean valide, Candidat candidat, Entreprise entreprise) {
		super();
		this.id = id;
		this.sujet = sujet;
		this.valide = valide;
		this.candidat = candidat;
		this.entreprise = entreprise;
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

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", sujet=" + sujet + ", valide=" + valide + ", candidat=" + candidat + ", entreprise="
				+ entreprise + "]";
	}

}
