package com.example.musicstore.service;

import com.example.musicstore.entity.Role;
import com.example.musicstore.entity.ShoppingCart;
import com.example.musicstore.entity.User;
import com.example.musicstore.entity.UserRole;
import com.example.musicstore.repository.RoleRepository;
import com.example.musicstore.repository.ShoppingCartRepository;
import com.example.musicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameContaining(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return new UserDetailsPrincipal(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers(){return userRepository.findAll();}

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username){return userRepository.findByUsernameContaining(username);}

    @Transactional(readOnly = true)
    public Boolean checkUsername(String username){return userRepository.existsByUsername(username);}

    @Transactional(readOnly = true)
    public Boolean checkEmail(String email){return userRepository.existsByEmail(email);}

    @Transactional(readOnly = true)
    public boolean checkRole(UserRole role){return roleRepository.existsByRole(role);}

    @Transactional(readOnly = false)
    public void registerUser(SignupRequest signupRequest){
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));
        System.out.println(roleRepository.findByRole(UserRole.CUSTOMER).get());
        //for (String role:signupRequest.getRole())
        Set<Role> userRoles = user.getRoles();
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        System.out.println(cart);
        shoppingCartRepository.save(cart);
        user.setShoppingCart(cart);
        System.out.println(user);
        System.out.println(cart);
        System.out.println(userRoles);
        userRoles.add(roleRepository.findByRole(UserRole.CUSTOMER).get());
        System.out.println(userRoles);
        userRepository.save(user);
    }

    public Optional<User> authenticateUser(SignupRequest signupRequest) {
        Optional<User> user = userRepository.findByUsernameContaining(signupRequest.getUsername());
        if(user.isEmpty())
            throw new UsernameNotFoundException("Username Not Found");
        String password = signupRequest.getPassword();
        if(encoder.matches(password, String.valueOf(user.get().getPassword())))
            return user;
        return Optional.empty();
    }
}
