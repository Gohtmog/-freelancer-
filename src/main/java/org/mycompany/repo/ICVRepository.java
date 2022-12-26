package org.mycompany.repo;

import org.mycompany.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICVRepository extends JpaRepository<CV, Integer> {

}
