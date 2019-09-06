package edu.uh.tech.cis3365.databaseproject.Repository;

import edu.uh.tech.cis3365.databaseproject.Models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}
