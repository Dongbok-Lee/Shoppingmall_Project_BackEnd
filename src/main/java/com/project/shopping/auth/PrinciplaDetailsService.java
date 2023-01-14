package com.project.shopping.auth;



import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new CustomException("User Not Found", ErrorCode.NotFoundUserException));

        System.out.println(user); //user  찍히고
        return  new PrincipalDetails(user);
    }
}
