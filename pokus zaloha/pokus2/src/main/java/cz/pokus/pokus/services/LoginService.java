package cz.pokus.pokus.services;


import cz.pokus.pokus.Repositories.PlayerRepo;
import cz.pokus.pokus.enitities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    PlayerRepo playerRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Long login(String name, String password){
        Player player = playerRepo.findPlayerByName(name);
        if ( player == null || ! passwordEncoder.matches(password, player.getPassword())){
            //todo throw NotExistingOrWrongPasswordE
            return null;
        }
        return player.getId();
    }

}
