package ukitinu.elastic_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;

@SpringBootApplication
public class Main implements WebApplicationInitializer
{
    private static String[] arguments;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args)
    {
        arguments = args;
        context = SpringApplication.run(Main.class, arguments);
    }

    public static void restart()
    {
        context.close();
        context = SpringApplication.run(Main.class, arguments);

    }

    @Override
    public void onStartup(ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}
