package hr.fitbit.demo.fitbitconnect.dao;

import hr.fitbit.demo.fitbitconnect.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleId(UUID roleId);

    Optional<RoleEntity> findByName(String name);
}

