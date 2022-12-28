package org.mycompany.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@Entity
@Table
@Component
@JsonIdentityInfo(property = "id", generator = PropertyGenerator.class)
public class CV {
	@Id
	private int id;
	private String description;

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
