package co.unicauca.microservice.apirest.model.service.implementation;

import co.unicauca.microservice.apirest.model.entity.Category;
import co.unicauca.microservice.apirest.model.entity.Product;
import co.unicauca.microservice.apirest.model.service.iCategoryService;
import co.unicauca.microservice.apirest.repository.iCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService implements iCategoryService {
    @Autowired
    private iCategoryRepository categoryRepository;
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public String deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return "Category was deleted successfull";
    }

    @Override
    public String postCategory(Category category) {
        categoryRepository.save(category);
        return "Category was created successfull";
    }

    @Override
    public String putCategory(Category category, Long id) {
        Category c = categoryRepository.findById(id).orElse(null);

        if (c == null)
        {
            return "Category was not updated, an error has occurred";
        }
        category.setCategoryId(c.getCategoryId());
        categoryRepository.save(category);
        return "Category was updated successfull";
    }

    @Override
    public List<Category> categoriesExisting(List<Category> categories) {
        // Recorrer las categorías enviadas con el producto
        List<Category> resCategories = new ArrayList<Category>();

        for (Category category : categories) {
            // Verificar si la categoría existe en la base de datos por su nombre
            Category existingCategory = categoryRepository.findByName(category.getName());

            // Si la categoría existe, asignarla al producto
            if (existingCategory != null) {
                resCategories.add(existingCategory);
            } else {
                // Si la categoría no existe, crearla y asignarla al producto
                Category newCategory = new Category(0L, category.getName());
                resCategories.add(newCategory);
            }
        }

        return resCategories;
    }
}
