package pl.opalka.Registration.Login.Service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.opalka.Registration.Login.Service.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
