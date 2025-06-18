package com.tutrit.sce.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Game {
    UUID id;
    String homeTeam;
    String awayTeam;
    Integer homeScore;
    Integer awayScore;
    Instant startTime;
    Instant endTime;
    Instant dateAdded;
}
