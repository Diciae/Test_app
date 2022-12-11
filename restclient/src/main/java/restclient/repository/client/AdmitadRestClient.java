package restclient.repository.client;

//import.test.project.dto.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import restclient.dto.AccessTokenResponse;

@Component
public class AdmitadRestClient {

    private static final String API_KEY = "TXQ0NEJCQ01Ld1lENUJFUExpWTlGRmRhdWwyMXhuOkFESFlJaUJvb2RvUUhIWDVsZVZudTAzbklDa0RDUg==";

    private final RestTemplate restTemplate;

    public AdmitadRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccessTokenResponse getAccessToken() {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "Mt44BBCMKwYD5BEPLiY9FFdaul21xn");
        formData.add("scope", "advcampaigns websites public_data advcampaigns_for_website");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + API_KEY);

        var httpEntity = new HttpEntity<>(formData, httpHeaders);

        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(
                "https://api.admitad.com/token/",
                HttpMethod.POST,
                httpEntity,
                AccessTokenResponse.class
        );

        if (!response.hasBody() || response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Couldn't get access token");
        }

        return response.getBody();
    }

    public AccessTokenResponse getAccessTokenByRefreshToken(String refreshToken) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", "Mt44BBCMKwYD5BEPLiY9FFdaul21xn");
        formData.add("refresh_token", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + API_KEY);

        var httpEntity = new HttpEntity<>(formData, httpHeaders);

        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(
                "https://api.admitad.com/token/",
                HttpMethod.POST,
                httpEntity,
                AccessTokenResponse.class
        );

        if (!response.hasBody() || response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Couldn't get access token");
        }

        return response.getBody();
    }
}
