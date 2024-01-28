package me.q1zz.startprotection.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

@UtilityClass
public class DurationUtil {

    @NotNull
    public String format(@Nullable Duration duration) {

        if(duration == null) {
            return "";
        }

        final long days = duration.toDaysPart();
        final long hours = duration.toHoursPart();
        final long minutes = duration.toMinutesPart();
        final long seconds = duration.toSecondsPart();

        final StringBuilder formattedDuration = new StringBuilder();

        if (days > 0) {
            formattedDuration.append(days).append("d ");
        }
        if (hours > 0) {
            formattedDuration.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append("min ");
        }

        formattedDuration.append(seconds).append("s");

        return formattedDuration.toString();
    }

}
