package org.wadajo.jspecifyspring;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;
import java.util.List;

@Controller
public class GeneralController {

    @GetMapping("/obras")
    ResponseEntity<ObraResponse> getRandomArtwork() {

        return ResponseEntity.ok(
            new ObraResponse(
                List.of(
                new Obra("Nasca","Fragment", Year.of(600),null
                )
            )
        ));
    }

}
