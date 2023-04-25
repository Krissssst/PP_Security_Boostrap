package spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.boot_security.models.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
