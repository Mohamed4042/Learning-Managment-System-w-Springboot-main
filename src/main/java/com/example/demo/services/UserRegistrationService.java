package com.example.demo.services;

import com.example.demo.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRegistrationService implements UserDetailsService {

    private final Map<String, User> users = new HashMap<>();       // username -> User
    private final Map<Integer, User> usersById = new HashMap<>();  // id -> User

    public void addPerson(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        if (usersById.containsKey(user.getId())) {
            throw new RuntimeException("User with this ID already exists");
        }
        users.put(user.getUsername(), user);
        usersById.put(user.getId(), user);
    }

    public boolean deleteUser(String username) {
        User user = users.remove(username);
        if (user != null) {
            usersById.remove(user.getId());
            return true;
        }
        return false;
    }


    public User getUser(String username) {
        return users.get(username);
    }

    public User getUserById(int id) {
        return usersById.get(id);
    }

    public List<User> getAllUsers() {
        return List.copyOf(usersById.values());
    }

    public void updateUser(int id, User updatedUser) {
        User existingUser = usersById.get(id);
        if (existingUser == null) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        // Update only the fields that are allowed to change
        if (updatedUser.getUsername() != null) {
            users.remove(existingUser.getUsername()); // Remove old username mapping
            existingUser.setUsername(updatedUser.getUsername());
            users.put(updatedUser.getUsername(), existingUser); // Add new username mapping
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        usersById.put(id, existingUser); // Update the id-based map
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
        );
    }
}