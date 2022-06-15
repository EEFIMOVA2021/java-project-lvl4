/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;

class AppTest {
    private static Javalin app;
    private static String baseUrl;

    @BeforeAll
    public static void beforeAll() {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port + "/";
    }

    @AfterAll
    public static void afterAll() {
        app.stop();
    }

    @Test
    void testGetHelloWorld() {
        HttpResponse<String> response = Unirest
                .get(baseUrl)
                .asString();
        String content = response.getBody();
        assertThat(content).isEqualTo("Hello World");
    }
}
