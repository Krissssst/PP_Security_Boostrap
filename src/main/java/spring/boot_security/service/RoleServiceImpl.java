package spring.boot_security.service;

import org.springframework.stereotype.Service;
import spring.boot_security.models.Role;
import spring.boot_security.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        return roles;
    }

    @Override
    public Set<Role> getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r where r.role = :name", Role.class);
        query.setParameter("name", name);
        return query.getResultStream().collect(Collectors.toSet());
    }
}