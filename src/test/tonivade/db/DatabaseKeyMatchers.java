package tonivade.db;

import static tonivade.db.redis.SafeString.safeString;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import tonivade.db.data.DatabaseKey;

public class DatabaseKeyMatchers {

    public static DatabaseKey safeKey(String str) {
        return DatabaseKey.safeKey(safeString(str));
    }

    public static DatabaseKey ttlKey(String str, long milis) {
        return DatabaseKey.ttlKey(safeString(str), milis);
    }

    public static DatabaseKey ttlKey(String str, int seconds) {
        return DatabaseKey.ttlKey(safeString(str), seconds);
    }

    public static Matcher<DatabaseKey> isExpired() {
        return new KeyExpiredMatcher();
    }

    public static Matcher<DatabaseKey> isNotExpired() {
        return new KeyNotExpiredMatcher();
    }

    private static class KeyExpiredMatcher extends TypeSafeMatcher<DatabaseKey> {

        @Override
        public void describeTo(org.hamcrest.Description description) {
            description.appendText("key is expired");
        }

        @Override
        protected boolean matchesSafely(DatabaseKey item) {
            return item.isExpired();
        }

    }

    private static class KeyNotExpiredMatcher extends TypeSafeMatcher<DatabaseKey> {

        @Override
        public void describeTo(org.hamcrest.Description description) {
            description.appendText("Key is not expired");
        }

        @Override
        protected boolean matchesSafely(DatabaseKey item) {
            return !item.isExpired();
        }

    }

}
