package ac.cy.frederick.st010500.api.api.repositories;

import ac.cy.frederick.st010500.api.api.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface IUserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}