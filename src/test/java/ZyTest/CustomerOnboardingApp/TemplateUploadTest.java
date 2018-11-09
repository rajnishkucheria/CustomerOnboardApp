package ZyTest.CustomerOnboardingApp;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TemplateUploadTest {

	
	  @Test
	  public void testGetTenantConfig()
	  {
		  ExtractableResponse res = RestAssured.given().
				    when().get("http://my-json-server.typicode.com/rajnishkucheria/APITest/tenants/1/config").then().extract();
		  Assert.assertEquals(res.statusCode(), 201);	
				    
	  }
	
 
  @Test
  public void testUploadXlsPositivecase()
  {
	  ExtractableResponse res = RestAssured.given().multiPart(new File(System.getProperty("user.dir") + "/TestData.xls")).
			    when().post("http://my-json-server.typicode.com/rajnishkucheria/APITest/customers").then().extract();
	  Assert.assertEquals(res.statusCode(), 201);	
			    
  }
  
  @Test
  public void testCreateCustomer()
  {
	  Response res = given().multiPart(new File(System.getProperty("user.dir") + "/TestData.xls")).
			    when().post("http://my-json-server.typicode.com/rajnishkucheria/APITest/customers").
			    then().contentType(ContentType.JSON).extract().response();
	  //System.out.println(res.jsonPath().getString("id"));
	  Assert.assertEquals(res.jsonPath().getString("id"), "4");	
			    
  }
  
  
  
  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

}
