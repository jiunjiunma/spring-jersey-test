package example.test;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import example.model.Sample;
import example.rest.SampleResource;
import example.service.SampleService;

/**
 * Same test as SampleResourceTest, further simplified. In this case, when the
 * mock sample service is created, all the test responses are also set.
 *
 * Date: 6/30/13
 * Time: 10:16 AM
 */
public class SimplifiedSampleResourceTest extends SpringContextAwareJerseyTest {
    @Bean
    static public SampleService sampleService() {
        SampleService mockSampleService =  Mockito.mock(SampleService.class);
        // see how the mock object hijack the sample service, now id 3 is valid
        Sample sample3 = new Sample(3, "sample3");
        Mockito.when(mockSampleService.getSample(3)).thenReturn(sample3);
        return mockSampleService;
    }

    /**
     * Create our own resource here so only the test resource is loaded. If
     * we use @ComponentScan, the whole package will be scanned and more
     * resources may be loaded (which is usually NOT what we want in a test).
     */
    @Bean
    static public SampleResource sampleResource() {
        return new SampleResource();
    }


    @Test
    public void testGetSample() {
        expect().statusCode(200).get(SERVLET_PATH + "/sample/3");
        String jsonStr = get(SERVLET_PATH + "/sample/3").asString();
        Assert.assertNotNull(jsonStr);
    }
}
