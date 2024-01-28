import me.q1zz.startprotection.util.DurationUtil;
import org.junit.jupiter.api.Test;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DurationUtilTest {

    @Test
    public void nullDurationTest() {
        assertEquals("", DurationUtil.format(null));
    }

    @Test
    public void zeroDurationTest() {
        assertEquals("0s", DurationUtil.format(Duration.ZERO));
    }

    @Test
    public void singleSecondsTest() {
        assertEquals("1s", DurationUtil.format(Duration.ofSeconds(1)));
    }

    @Test
    public void singleMinutesTest() {
        assertEquals("1min 0s", DurationUtil.format(Duration.ofMinutes(1)));
    }

    @Test
    public void hoursMinutesSecondsTest() {
        assertEquals("2d 3h 45min 30s", DurationUtil.format(Duration.ofDays(2).plusHours(3).plusMinutes(45).plusSeconds(30)));
    }

    @Test
    public void daysHoursTest() {
        assertEquals("5d 6h 0s", DurationUtil.format(Duration.ofDays(5).plusHours(6)));
    }

    @Test
    public void largeDurationTest() {
        assertEquals("123d 5h 30min 15s", DurationUtil.format(Duration.ofDays(123).plusHours(5).plusMinutes(30).plusSeconds(15)));
    }

}
