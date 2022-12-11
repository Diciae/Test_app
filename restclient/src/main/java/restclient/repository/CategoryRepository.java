package restclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restclient.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}