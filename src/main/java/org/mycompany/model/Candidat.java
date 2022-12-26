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
public class Candidat {
	
	@Id
	int id;
	String nom;
	String prenom;
	
	int moyNotes;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Projet_Candidat_Associations", joinColumns = @JoinColumn(name = "idProjet"), inverseJoinColumns = @JoinColumn(name = "idcandidat"))
	private List<Projet> ListeNotes;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Candidat_Associations", joinColumns = @JoinColumn(name = "idNote"), inverseJoinColumns = @JoinColumn(name = "idcandidat"))
	private List<Test> listeCV;
	
	
}
