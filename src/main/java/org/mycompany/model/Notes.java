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
@JsonIdentityInfo(property = "id", generator = PropertyGenerator.class, scope = Notes.class)
public class Notes {

	@Id
	private int id;
	private int note;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCandidat")
	private Candidat Candidat;

	
	
	
	public Notes() {
		super();
	}

	public Notes(int id, int note, Candidat Candidat) {
		super();
		this.id = id;
		this.note = note;
		this.Candidat = Candidat;
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

	public Candidat getCandidat() {
		return Candidat;
	}

	public void setCandidat(Candidat Candidat) {
		this.Candidat = Candidat;
	}

	
	@Override
	public String toString() {
		return "Notes [id=" + id + ", note=" + note + ", Candidat=" + Candidat + "]";
	}
}
