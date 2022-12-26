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
public class Notes {

	@Id
	private int id;
	private int note;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCandidat")
	private Candidat candidat;

	
	
	
	public Notes() {
		super();
	}

	public Notes(int id, int note, Candidat candidat) {
		super();
		this.id = id;
		this.note = note;
		this.candidat = candidat;
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
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	
	@Override
	public String toString() {
		return "Notes [id=" + id + ", note=" + note + ", candidat=" + candidat + "]";
	}
}
