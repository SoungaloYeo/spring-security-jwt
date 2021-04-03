package securitypoc.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import securitypoc.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
