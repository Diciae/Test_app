package restclient.sevice;

import org.springframework.stereotype.Service;
import restclient.dto.AccessTokenResponse;
import restclient.repository.client.AdmitadRestClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenService {

    private final AdmitadRestClient admitadRestClient;
    private final Executor threadPool = Executors.newFixedThreadPool(1);

    private volatile String accessToken;
    private volatile long deadLineMs;
    private volatile String refreshToken;

    public AccessTokenService(AdmitadRestClient admitadRestClient) {
        this.admitadRestClient = admitadRestClient;
    }

    @PostConstruct
    public void start() {
        updateAccessToken();
        threadPool.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                updateAccessToken();
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    //Метод для получения токена и использования его для получения магазинов
    public String getAccessToken() {
        return accessToken;
    }

    private void updateAccessToken() {
        if (deadLineMs <= System.currentTimeMillis()) {
            this.accessToken = null;
            //refreshToken lives forever
        }
        if (accessToken == null || refreshToken == null) {
            long currentTimeMillis = System.currentTimeMillis();
            AccessTokenResponse accessToken;
            if (refreshToken != null) {
                accessToken = admitadRestClient.getAccessTokenByRefreshToken(refreshToken);
            } else {
                accessToken = admitadRestClient.getAccessToken();
            }
            this.accessToken = accessToken.accessToken;
            this.refreshToken = accessToken.refreshToken;
            this.deadLineMs = currentTimeMillis + ((int) (accessToken.expiresInSec * 0.8)) * 1000L; //convert to millis
        }
    }
}
