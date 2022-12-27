package org.mycompany.repo;

import org.mycompany.model.NotesEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotesEntrepriseRepository extends JpaRepository<NotesEntreprise, Integer>{

}
