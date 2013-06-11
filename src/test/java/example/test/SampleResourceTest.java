package example.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import example.model.Sample;
import example.rest.SampleResource;
import example.service.SampleService;

/**
 * Date: 6/11/13
 * Time: 11:08 AM
 */
@Configuration
@ComponentScan(basePackageClasses={SampleResource.class})
public class SampleResourceTest extends SpringContextAwareJerseyTest {
    private SampleService mockSampleService;

    // create mock object for our test
    @Bean
    static public SampleService sampleService() {
        return Mockito.mock(SampleService.class);
    }

    // get the mock objects from the internal servlet context, because
    // the app context may get recreated for each test so we have to set
    // it before each run
    @Before
    public void setupMocks() {
         mockSampleService = getContext().getBean(SampleService.class);
    }

    @Test
    public void testMock() {
        Assert.assertNotNull(mockSampleService);
    }

    @Test
    public void testGetSample() {
        // see how the mock object hijack the sample service, now id 3 is valid
        Sample sample3 = new Sample(3, "sample3");
        Mockito.when(mockSampleService.getSample(3)).thenReturn(sample3);

        expect().statusCode(200).get(SERVLET_PATH + "/sample/3");
        String jsonStr = get(SERVLET_PATH + "/sample/3").asString();
        Assert.assertNotNull(jsonStr);
    }

}
