package edu.uh.tech.cis3365.databaseproject.Repository;

import edu.uh.tech.cis3365.databaseproject.Models.Timesheet;
import org.springframework.data.repository.CrudRepository;

public interface TimesheetRepository extends CrudRepository<Timesheet, Integer> {
}
