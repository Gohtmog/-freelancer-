package org.mycompany.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

public class Notes {

	@Id
	int id;
	int note;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Patient_Medecin_Associations", joinColumns = @JoinColumn(name = "idMedecin"), inverseJoinColumns = @JoinColumn(name = "idPatient"))
	private List<Candidat> listeMedecins;
}
