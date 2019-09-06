package edu.uh.tech.cis3365.databaseproject.Repository;

import edu.uh.tech.cis3365.databaseproject.Models.Music;
import org.springframework.data.repository.CrudRepository;

public interface MusicRepository extends CrudRepository<Music,Integer> {
}
