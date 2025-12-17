package org.wadajo.jspecifyspring.acceptance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.RestTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceCriteriaIT {

    @LocalServerPort
    protected int port;

    @Test
    void debeDevolverUnaObraConFormatoCorrecto() {

        RestTestClient client = RestTestClient
            .bindToServer()
            .baseUrl(String.format("http://localhost:%s", port))
            .build();

        client.get()
            .uri("/obras")
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("data[0].artist_title")
            .isEqualTo("Nasca");
    }
}
