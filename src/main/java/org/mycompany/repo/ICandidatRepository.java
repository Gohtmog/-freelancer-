package org.mycompany.repo;

import org.mycompany.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidatRepository extends JpaRepository<Candidat, Integer>{

}
