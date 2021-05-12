package pl.demo.example.registration;

import org.springframework.stereotype.Service;
import pl.demo.example.appuser.AppUser;
import pl.demo.example.appuser.AppUserRole;
import pl.demo.example.appuser.AppUserService;
import pl.demo.example.registration.token.ConfirmationToken;
import pl.demo.example.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private AppUserService appUserService;
    private EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;

    public RegistrationService(AppUserService appUserService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService) {
        this.appUserService = appUserService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register (RegistrationRequest request) {

        boolean isEmailValid = emailValidator.test(request.getEmail());

        if(!isEmailValid) {
            throw new IllegalStateException("email not valid");
        }
        return
                appUserService.singUpUser(
                        new AppUser(
                                request.getFirstName(),
                                request.getLastName(),
                                request.getEmail(),
                                request.getPassword(),
                                AppUserRole.USER)
                );
    }

    public String confirmToken (String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getUsername());
        return "confirmed";
    }


}
