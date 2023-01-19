package com.chrisojukwu.tallybookkeeping.userauthentication.repository;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(String email);

    //public User findByUserId(String userId);

//    @Query("SELECT u FROM User u WHERE u.user_id = :user_id")
//    public User findByUserId(@Param("user_id") String userId);


}
