package example.test

import example.rest.SampleResource
import org.springframework.context.annotation.Bean
import org.mockito.Mockito
import example.model.Sample
import example.service.SampleService
import org.junit.{Assert, Test}
import com.jayway.restassured.RestAssured._

/**
 * Sample test in scala. Not the test set up can be pushed into the companion
 * object and make it even cleaner.
 *
 * Date: 6/30/13
 * Time: 10:33 AM
 *
 */
class ScalaSampleResourceTest extends SpringContextAwareJerseyTest {
  @Test
  def testGetSample() {
    expect().statusCode(200).get(SpringContextAwareJerseyTest.SERVLET_PATH + "/sample/3")
    val jsonStr = get(SpringContextAwareJerseyTest.SERVLET_PATH + "/sample/3").asString()
    Assert.assertNotNull(jsonStr);
  }

}

object ScalaSampleResourceTest {
  @Bean
  def sampleService = {
    val mockSampleService =  Mockito.mock(classOf[SampleService])
    // see how the mock object hijack the sample service, now id 3 is valid
    val sample3 = new Sample(3, "sample3")
    Mockito.when(mockSampleService.getSample(3)).thenReturn(sample3)
    mockSampleService
  }

  @Bean
  def sampleResource = new SampleResource()
}