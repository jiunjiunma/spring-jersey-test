spring-jersey-test
==================
Sample code (see SpringContextAwareJerseyTest.java) on how you can enhance
JerseyTest to access the internal Spring application context without writing a
custom ServletContainerFactory.

I also include a test case based on SpringContextAwareJerseyTest to show how you
can easily use Mockito and RestAssured to test your REST API end-to-end.
