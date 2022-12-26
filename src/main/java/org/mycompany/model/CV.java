package org.mycompany.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class CV {
	@Id
	int id;
	String description;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Patient_Medecin_Associations", joinColumns = @JoinColumn(name = "idMedecin"), inverseJoinColumns = @JoinColumn(name = "idPatient"))
	private List<Medecin> listeMedecins;
}
