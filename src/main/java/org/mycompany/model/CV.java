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
public class CV {
	@Id
	int id;
	String description;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCandidat")
	private Candidat candidat;

//	
	public CV() {
		super();
	}

	public CV(int id, String description, Candidat candidat) {
		super();
		this.id = id;
		this.description = description;
		this.candidat = candidat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	@Override
	public String toString() {
		return "CV [id=" + id + ", description=" + description + ", candidat=" + candidat.toString() + "]";
	}

}
