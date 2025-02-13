package santa_cruz_alimento_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.User;
import santa_cruz_alimento_backend.repository.IUserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String ci) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findFirstByCi(ci);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found", null);
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getCi(), optionalUser.get().getPassword(), new ArrayList<>());
    }
}
