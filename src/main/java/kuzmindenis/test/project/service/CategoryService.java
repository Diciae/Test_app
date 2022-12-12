package kuzmindenis.test.project.service;

import kuzmindenis.test.project.model.Category;
import kuzmindenis.test.project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(int categoryId) {
        return categoryRepository.findById((long) categoryId).get();
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
