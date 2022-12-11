package restclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restclient.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
}
