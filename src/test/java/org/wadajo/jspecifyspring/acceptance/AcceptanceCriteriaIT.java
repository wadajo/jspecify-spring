package org.wadajo.jspecifyspring.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.wadajo.jspecifyspring.Obra;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    @ConfigureWireMock(port = 8888, baseUrlProperties = "${artworks.base-url}")
)
class AcceptanceCriteriaIT {

    @LocalServerPort
    protected int port;

    private RestTestClient client;

    public static final String OBRA_RAW_STUBBING_FILE = "src/test/resources/stubs/obra-raw.json";

    @BeforeEach
    void setup() {
        client = RestTestClient
            .bindToServer()
            .baseUrl(String.format("http://localhost:%s", port))
            .build();
    }

    @Test
    void debeDevolverUnaObraConFormatoCorrecto() {
        var obraRaw = new JsonMapper().readTree(new File(OBRA_RAW_STUBBING_FILE));

        stubFor(get("/")
            .willReturn(aResponse()
                .withStatus(200)
                // no se puede usar withJsonBody porque el JsonNode al que se referencia sigue siendo el de Jackson 2
                .withBody(obraRaw.toString())
            ));

        client.get()
            .uri("/obraRandom")
            .exchangeSuccessfully()
            .expectBody(Obra.class)
            .consumeWith(
                response -> {
                    var obra = response.getResponseBody();
                    assertThat(obra.title())
                        .isEqualTo("Villa Pamphili outside Porta S. Pancrazio, from Views of Rome");
                }
            );
    }
}
