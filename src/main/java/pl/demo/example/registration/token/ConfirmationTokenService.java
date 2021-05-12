package pl.demo.example.registration.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    public void saveConfirmationToken(ConfirmationToken token) {
        this.confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return this.confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt (String token) {
        ConfirmationToken confirmationToken = getToken(token).orElseThrow(null);
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        this.confirmationTokenRepository.save(confirmationToken);
    }


}
