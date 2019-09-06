package edu.uh.tech.cis3365.databaseproject.Repository;

import edu.uh.tech.cis3365.databaseproject.Models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
}
