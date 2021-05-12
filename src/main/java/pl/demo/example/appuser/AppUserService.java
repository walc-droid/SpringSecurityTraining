package pl.demo.example.appuser;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.demo.example.registration.token.ConfirmationToken;
import pl.demo.example.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String singUpUser (AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getUsername())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPw = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPw);
        appUserRepository.save(appUser);


        String token = UUID.randomUUID().toString();
        // TODO: Send Confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: SEND EMAIL

        return token;
    }

    public void enableAppUser(String email) {
        AppUser appUser = this.appUserRepository.findByEmail(email).orElseThrow(null);
        appUser.setEnabled(true);
        this.appUserRepository.save(appUser);
    }


}
