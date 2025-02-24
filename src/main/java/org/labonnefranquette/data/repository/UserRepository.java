package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Byte> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);

}
