package org.wadajo.jspecifyspring;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

@Controller
public class GeneralController {

    @Value("${artworks.base-url}")
    private String endpointUrl;

    private RestClient restClient;

    private JsonMapper jsonMapper;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
            .baseUrl(endpointUrl)
            .build();
        jsonMapper = new JsonMapper();
    }

    @GetMapping("/obraRandom")
    ResponseEntity<Obra> getRandomArtwork() {
        String rawResponse = restClient.get()
            .retrieve()
            .body(String.class);

        var dataRawField = jsonMapper.readTree(rawResponse).get("data").get(0);
        var obraRandom = jsonMapper.readValue(dataRawField.toString(), Obra.class);
        return ResponseEntity.ok(obraRandom);
    }

}
