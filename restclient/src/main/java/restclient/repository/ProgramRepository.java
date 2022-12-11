package restclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restclient.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
