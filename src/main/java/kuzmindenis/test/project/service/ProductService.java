package kuzmindenis.test.project.service;

import kuzmindenis.test.project.model.Product;
import kuzmindenis.test.project.repository.ProductsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;
    private final EntityManager entityManager;

    public ProductService(ProductsRepository productsRepository, EntityManager entityManager) {
        this.productsRepository = productsRepository;
        this.entityManager = entityManager;
    }

    public void saveProduct(Product product) {
        productsRepository.save(product);
    }

    public void saveProducts(List<Product> products) {
        productsRepository.saveAll(products);
    }

    public List<Product> getProducts(String queryFilter, PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);
        query.select(product).where(criteriaBuilder.or(
                criteriaBuilder.like(product.get("name"), "%" + queryFilter + "%"),
                criteriaBuilder.like(product.get("model"), "%" + queryFilter + "%")
        ));
        return entityManager.createQuery(query)
                .setMaxResults(pageRequest.getPageSize())
                .setFirstResult(Math.toIntExact(pageRequest.getOffset()))
                .getResultList();
    }
}
