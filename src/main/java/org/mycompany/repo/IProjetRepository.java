package org.mycompany.repo;

import org.mycompany.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjetRepository extends JpaRepository<Projet, Integer> {

}
