package kuzmindenis.test.project.repository;

import kuzmindenis.test.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

}
