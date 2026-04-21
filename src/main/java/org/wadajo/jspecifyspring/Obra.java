package org.wadajo.jspecifyspring;

import org.jspecify.annotations.Nullable;

import java.time.Year;

public record Obra(
        String artist_title,
        String title,
        Year date_end,
        @Nullable String description
) {
}
