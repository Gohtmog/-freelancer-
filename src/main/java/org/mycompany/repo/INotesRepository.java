package org.mycompany.repo;

import org.mycompany.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotesRepository extends JpaRepository<Notes, Integer> {

}
