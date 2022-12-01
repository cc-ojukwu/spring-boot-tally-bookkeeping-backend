package com.chrisojukwu.tallybookkeeping.userauthentication;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //User findByUsername(String username);

    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public User findByUserId(String userId);

//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    public User getUserByUsername(@Param("username") String username);
}
