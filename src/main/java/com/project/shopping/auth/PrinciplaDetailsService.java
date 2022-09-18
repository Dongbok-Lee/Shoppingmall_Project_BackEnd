package com.project.shopping.auth;



import com.project.shopping.model.User;
import com.project.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrinciplaDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService");
        User user = userRepository.findByEmail(email); //이메일을 이용하여 user객체 인증 객체  만들기
        System.out.println(user);
        return  new PrincipalDetails(user);
    }
}
