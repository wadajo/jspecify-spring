package org.wadajo.jspecifyspring;

import java.time.Year;

public record Obra(
        String artist_title,
        String title,
        Year date_end,
        String description
) {
}
