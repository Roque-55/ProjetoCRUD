import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import io.javalin.rendering.template.JavalinMustache;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {

            config.staticFiles.add("/static");
            config.fileRenderer(new JavalinMustache());
        }).start(7000);

        app.get("/", ctx -> {
            ctx.render("/static/html/index.html");  // Isso vai carregar o index.html
        });
    }
}
