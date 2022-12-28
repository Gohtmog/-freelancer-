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
@JsonIdentityInfo(property = "id", generator = PropertyGenerator.class, scope = NotesEntreprise.class)
public class NotesEntreprise {

	@Id
	private int id;
	private int note;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEntreprise")
	private Entreprise Entreprise;

	
	
	
	public NotesEntreprise() {
		super();
	}

	public NotesEntreprise(int id, int note, Entreprise Entreprise) {
		super();
		this.id = id;
		this.note = note;
		this.Entreprise = Entreprise;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public Entreprise getEntreprise() {
		return Entreprise;
	}

	public void setEntreprise(Entreprise Entreprise) {
		this.Entreprise = Entreprise;
	}

	
	@Override
	public String toString() {
		return "Notes [id=" + id + ", note=" + note + ", Entreprise=" + Entreprise + "]";
	}
}
