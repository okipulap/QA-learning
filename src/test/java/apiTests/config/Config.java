package apiTests.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Config {

    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    private Config() {

    }

    public static String get(String key, String defaultValue) {
        String sys = System.getProperty(key);

        if (sys != null && !sys.isBlank()) {
            return sys;
        }

        String env = DOTENV.get(key);
        if (env != null && !env.isBlank()) {
            return env;
        }

        return defaultValue;
    }
}
