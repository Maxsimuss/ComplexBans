package maxsimus.complexbans.bungeecord.util;

import java.time.Instant;
import java.util.Date;

public class Punishment {
    String reason;
    long until;

    public Punishment(String r, long u) {
        reason = r;
        until = u;
    }

    public String getReason() {
        return reason;
    }

    public String getExpiration() {
        return Date.from(Instant.ofEpochMilli(until)).toString();
    }

    public boolean isValid() {
        return until > System.currentTimeMillis();
    }
}
