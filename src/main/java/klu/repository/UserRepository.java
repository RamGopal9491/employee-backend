package klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import klu.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long >
{
	//Query for validating the User is already present or not while signup
	@Query("select count(U) from Users U where U.email=:email")
	public int validateEmail(@Param("email") String email);
	@Query("select U from Users U where U.email = :email")
	Users findByEmail(@Param("email") String email);
	//Query for Signin Operation
	@Query("select count(U) from Users U where U.email=:username and U.password=:password	")
	public int validateCredentials(@Param("username") String email, @Param("password") String password);

}
