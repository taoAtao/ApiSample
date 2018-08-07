package com.gateway.services;

import com.gateway.models.User;
import com.gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by daip on 2018/6/26.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(int id) {
        return userRepository.getOne(id);
    }

    public boolean insertUser(User user) {
        try{
            userRepository.saveAndFlush(user);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public boolean deleteUser(User user) {
        try{
            userRepository.delete(user);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getUserByName(String name) {
        return userRepository.getByName(name);
    }
}
