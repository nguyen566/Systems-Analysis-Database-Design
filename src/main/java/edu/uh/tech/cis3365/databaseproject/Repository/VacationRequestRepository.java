package edu.uh.tech.cis3365.databaseproject.Repository;

import edu.uh.tech.cis3365.databaseproject.Models.VacationRequest;
import org.springframework.data.repository.CrudRepository;

public interface VacationRequestRepository extends CrudRepository<VacationRequest, Integer> {
}
