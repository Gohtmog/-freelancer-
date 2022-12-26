package org.mycompany.repo;

import org.mycompany.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntrepriseRepository extends JpaRepository<Entreprise, Integer> {

}
