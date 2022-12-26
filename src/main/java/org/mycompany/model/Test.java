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
public class Test {
	@Id
	int id;
	String sujet;
	Boolean valide;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Candidat_Associations", joinColumns = @JoinColumn(name = "idCandidat"), inverseJoinColumns = @JoinColumn(name = "idTest"))
	private List<Candidat> listeCandidats;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "T_Test_Entreprise_Associations", joinColumns = @JoinColumn(name = "idEntreprise"), inverseJoinColumns = @JoinColumn(name = "idTest"))
	private List<Entreprise> listeEntreprises;
	
}
