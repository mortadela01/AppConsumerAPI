package co.unicauca.microservice.apirest.model.service.implementation;

import co.unicauca.microservice.apirest.model.entity.Product;
import co.unicauca.microservice.apirest.model.service.iProductService;
import co.unicauca.microservice.apirest.repository.iProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;

//definir el crud del produt (POST, GET, UPDATE), no se edita nada en la base de datos, eso lo hace el repositorio
@Service
@RequiredArgsConstructor
public class ProductService implements iProductService {
    //para inyectar las propiedades de iProductRepository a productRepository es util para fijar una instancia del repositorio al servicio, pero si la interface es implemementada en mas de un repositorio puede dar error (habria que usar  @qualifier)
    @Autowired
    private iProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        //la interfaz jpa ya da metodo predeterminados para usar aqui
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByCategory(String nameCategory) {
        return productRepository.findByCategory(nameCategory);
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "Product was deleted successfull";
    }

    @Override
    public String postProduct(Product product) {
        productRepository.save(product);
        return "Product was created successfull";
    }

    @Override
    public String putProduct(Product product, Long id) {
        Product p = productRepository.findById(id).get();

        if (p == null)
        {
            return "Product was not updated, an error has occurred";
        }
        product.setProductId(p.getProductId());
        productRepository.save(product);
        return "Product was updated successfull";
    }
}
