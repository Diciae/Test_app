package restclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {

    @JsonProperty("access_token")
    public final String accessToken;

    @JsonProperty("expires_in")
    public final int expiresInSec;

    @JsonProperty("refresh_token")
    public final String refreshToken;

    public AccessTokenResponse(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("refresh_token") String refreshToken
    ) {
        this.accessToken = accessToken;
        this.expiresInSec = expiresIn;
        this.refreshToken = refreshToken;
    }
}
