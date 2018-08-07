package com.gateway.repositories;


import com.gateway.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//import org.springframework.transaction.annotation.Transactional;

/**
 * Created by daip on 2018/6/26.
 */
@Repository
//@Transactional
public interface UserRepository extends JpaRepository<User,Integer>{
    public List<User> getByName(String name);
}
