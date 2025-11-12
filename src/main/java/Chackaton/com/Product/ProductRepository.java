package Chackaton.com.Product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {

    Optional<Product> findByArticleNumber(String article);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images")
    List<Product> findAllWithImages();

    @EntityGraph(attributePaths = {"images"})
    List<Product> findByName(String name);

    @EntityGraph(attributePaths = {"images"})
    @Override
    List<Product> findAll();
}
