package com.example.airbnb.auth;

import com.example.airbnb.dto.EmailDTO;
import com.example.airbnb.dto.TokenDTO;
import com.example.airbnb.dto.UserInfoDTO;
import com.example.airbnb.exception.ErrorMessage;
import com.example.airbnb.exception.OAuthException;
import com.example.airbnb.exception.RestTemplateResponseErrorHandler;
import com.example.airbnb.utils.GitHubType;
import com.example.airbnb.utils.GitHubUrl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@PropertySource("classpath:/oauth.properties")
@Component
public class GitHubOAuth implements OAuth{

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CODE = "code";
    public static final String TOKEN = "token";

    private final RestTemplate restTemplate;
    private final Environment environment;

    public GitHubOAuth(Environment environment, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        this.environment = environment;
    }

    @Override
    public TokenDTO getTokenAPI(String code, GitHubType gitHubType) {
        String id = environment.getProperty(gitHubType.getClientId());
        String secret = environment.getProperty(gitHubType.getClientSecret());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GitHubUrl.ACCESS_TOKEN.getUrl())
                .queryParam(CLIENT_ID, id)
                .queryParam(CLIENT_SECRET, secret)
                .queryParam(CODE, code);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, TokenDTO.class).getBody();
    }

    @Override
    public UserInfoDTO getUserInfoAPI(String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GitHubUrl.USER_INFO.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, TOKEN + " " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, UserInfoDTO.class).getBody();
    }

    @Override
    public EmailDTO getEmailAPI(String token) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GitHubUrl.USER_EMAIL.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, TOKEN + " " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        List<EmailDTO> emailDTOList = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<EmailDTO>>() {}).getBody();
        return emailDTOList.stream().findFirst().orElseThrow(
                () -> new OAuthException(ErrorMessage.OAUTH_FAILED)
        );
    }
}
