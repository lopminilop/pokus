package cz.pokus.pokus.services;

import cz.pokus.pokus.Repositories.IPlayerRepo;
import cz.pokus.pokus.Repositories.PlayerRepo;
import cz.pokus.pokus.enitities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class RegisterService {
    @Autowired
    PlayerRepo playerRepo;


    @Autowired
    IPlayerRepo iPlayerRepo;


    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(String name,String password){
        Player alreadyRegistered = playerRepo.findPlayerByName(name);
        if ( alreadyRegistered != null){
            //todo throw
        }
        String encodedPassword = passwordEncoder.encode(password);

        Player player = new Player(null, name, encodedPassword, new ArrayList<>());
        iPlayerRepo.save(player);
        loginService.login(name,password);
        return player.getId();
    }

}

