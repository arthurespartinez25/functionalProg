package etl.info;

import java.time.Year;

public record Film(
    String id,
    String name,
    Year release
) {}
