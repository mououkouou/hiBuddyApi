package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.support.KoreaTour;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KoreaTourService {
    private final WebClient webClient;

    @Value("${spring.korea-tour-api.auth-key}")
    private String authKey;
    @Value("${spring.korea-tour-api.url}")
    private String tourApiUrl;

    public Mono<List<KoreaTour>> getMountains(String keyword) throws UnsupportedEncodingException, URISyntaxException {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(tourApiUrl+"searchKeyword")
                .queryParam("ServiceKey", authKey)
                .queryParam("cat1", "A01")
                .queryParam("cat2", "A0101")
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "HIBUDDY")
                .queryParam("arrange", "B")
                .queryParam("numOfRows", "100")
                .queryParam("pageNo", "1")
                .queryParam("keyword", URLEncoder.encode(keyword, "UTF-8"))
                .queryParam("_type", "json")
                .build(false)
                .toUriString();

        URI uri = new URI(apiUrl);

        ObjectMapper mapper = new ObjectMapper();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.path("response").path("body").path("items").path("item"))
                .map(itemJsonNode -> itemJsonNode.isArray() ? mapper.convertValue(itemJsonNode, new TypeReference<List<KoreaTour>>() {}) :
                        Arrays.asList(mapper.convertValue(itemJsonNode, KoreaTour.class)));

    }

    public Mono<List<KoreaTour>> getFamousMountainIntro(String contentTypeId, String contentId) throws URISyntaxException {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(tourApiUrl+"detailIntro")
                .queryParam("ServiceKey", authKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "HIBUDDY")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("contentId", contentId)
                .queryParam("_type", "json")
                .build(false)
                .toUriString();

        URI uri = new URI(apiUrl);

        ObjectMapper mapper = new ObjectMapper();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.path("response").path("body").path("items").path("item"))
                .map(itemJsonNode -> mapper.convertValue(itemJsonNode, new TypeReference<List<KoreaTour>>() {}));
    }

    public Mono<List<KoreaTour>> getFamousMountainInfo(String contentTypeId, String contentId) throws URISyntaxException {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(tourApiUrl+"detailCommon")
                .queryParam("ServiceKey", authKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "HIBUDDY")
                .queryParam("defaultYN", "Y")
                .queryParam("firstImageYN", "Y")
                .queryParam("addrinfoYN", "Y")
                .queryParam("overviewYN", "Y")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("contentId", contentId)
                .queryParam("_type", "json")
                .build(false)
                .toUriString();

        URI uri = new URI(apiUrl);

        ObjectMapper mapper = new ObjectMapper();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.path("response").path("body").path("items").path("item"))
                .map(itemJsonNode -> mapper.convertValue(itemJsonNode, new TypeReference<List<KoreaTour>>() {}));
    }
}
