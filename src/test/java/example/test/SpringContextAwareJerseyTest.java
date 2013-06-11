package example.test;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * A utility class which allows us to access the application context hidden in
 * Jersey's test servlet container. All the JAX-RS resource tests should inherit
 * from this class.
 *
 * Date: 6/6/13
 * Time: 9:44 PM
 */
@Configuration
public class SpringContextAwareJerseyTest extends JerseyTest {
    protected static String SERVLET_PATH = "/api";

    final private static ThreadLocal<ApplicationContext> context =
        new ThreadLocal<>();

    // JerseyTest's way of specifying port number for embedded servlet container
    @Override
    protected int getPort(int defaultPort) {
        return 8080;
    }

    /**
     * Return a string which contains a list of comma separated package names
     * where the JAX-RS resources located. This value is used to configure
     * the init-param "com.sun.jersey.config.property.packages".
     *
     * @return a string of package names
     */
    protected String getResourceLocation() {
        return "example.rest";
    }

    /**
     * Return the locations of the context configuration classes or packages
     * used by the test. The value will be used by the SpringServlet to
     * create the application context. Basically it should match the
     * contextParam "contextConfigLocation" you set in a web.xml file.
     *
     * Note the base implementation returns the test class name. It assumes
     * the test class itself has the @Configuration annotation specified.
     * If there are more than one configuration class used, the test case has
     * to override this method and provides the config location.
     *
     * @return contextConfigLocation
     */
    protected String getContextConfigLocation() {
        return getClass().getName();
    }

    static private String getContextHolderConfigLocation() {
        return SpringContextAwareJerseyTest.class.getName();
    }

    /**
     * Return a WebAppDescriptor which will be used to configure the internal
     * servlet container. Basically the configuration should match the settings
     * you see in web.xml.
     *
     * @return a WebAppDescriptor
     */
    protected WebAppDescriptor configure() {
        String contextConfigLocation = getContextConfigLocation() + " " +
            getContextHolderConfigLocation();

        Map<String, String> initParams = new HashMap<>();
        initParams.put("com.sun.jersey.config.property.packages",
                       getResourceLocation());
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

        return new WebAppDescriptor.Builder(initParams)
            .servletClass(SpringServlet.class)
            .contextParam(
                "contextClass",
                "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
            .contextParam("contextConfigLocation", contextConfigLocation)
            .servletPath(SERVLET_PATH)  // if not specified, it set to root resource
            .contextListenerClass(ContextLoaderListener.class)
            .requestListenerClass(RequestContextListener.class)
            .build();
    }

    protected final ApplicationContext getContext() {
        return context.get();
    }

    @Bean
    public static ContextHolder contextHolder() {
        return new ContextHolder();
    }

    private static class ContextHolder implements ApplicationContextAware {
        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
            context.set(applicationContext);
        }
    }
}
