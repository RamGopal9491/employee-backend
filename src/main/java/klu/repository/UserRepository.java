package klu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import klu.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // Preferred Spring Data style: return Optional
    Optional<Users> findByEmail(String email);

    // Count of users with given email (used for signup validation)
    @Query("select count(u) from Users u where u.email = :email")
    int countByEmail(@Param("email") String email);

    // Credential validation (not recommended for production use)
    @Query("select count(u) from Users u where u.email = :username and u.password = :password")
    int validateCredentials(@Param("username") String email, @Param("password") String password);
}
