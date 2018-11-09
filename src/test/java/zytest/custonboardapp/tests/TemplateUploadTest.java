package zytest.custonboardapp.tests;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import zytest.custonboardapp.operations.CallRestServices;
import zytest.custonboardapp.operations.ReadTestData;

public class TemplateUploadTest  {

	CallRestServices service = new CallRestServices();
	Properties prop;

	
	/* This test will call /Tenant/<id>/config API.
	 * First we will be checking response is success or not for this request.
	 */
	  @Test
	  public void testGetTenantConfigResponse()
	  {
		  //check success of request
		  Assert.assertEquals(service.getTenantConfig(prop.getProperty("TEST_TENANT_ID")).statusCode(), Integer.parseInt(prop.getProperty("RESPONSE_SUCCESS")));		    
	  }
	  
		/* If response is success for  /Tenant/<id>/config API
		 * Then we will get config values for given tenant
		 * we can use these config values to find desired field values of entities
		 * Same program can be extended for getting the relationship between entities.
		 * This will be used to test dynamic screens
		 */
	  
	  @Test(dependsOnMethods="testGetTenantConfigResponse")
	  public void testGetTenantConfig()
	  {
		  Response res = service.getTenantConfig(prop.getProperty("TEST_TENANT_ID"));
		  String customerConfig = res.jsonPath().getString(prop.getProperty("ENTITY_CUSTOMER"));
		  String addresssConfig = res.jsonPath().getString(prop.getProperty("ENTITY_ADDRESS"));
		  //Comparing the config values
		  Assert.assertEquals(customerConfig, prop.getProperty("CUSTOMER_CONFIG"));
		  Assert.assertEquals(addresssConfig, prop.getProperty("ADDRESS_CONFIG"));
	  }
	  
		/* Upload template via API call  /Customers/upload
		 * Check for dummy positive test scenario and check the success response code
		 */
	  
	  @Test
	  public void testUploadXlsPositiveCase()
	  {
		  Response res = service.getTemplateUploadResponse(prop.getProperty("UPLOAD_FILE_POSITIVECASE"));
		  Assert.assertEquals(res.statusCode(), Integer.parseInt(prop.getProperty("RESPONSE_SUCCESS")));	
				    
	  }
	
		/* Upload template via API call  /Customers/upload
		 * Check for dummy Error test scenario where lets assume one of the config mandatory field is null
		 * as per requirements, errors would be listed per row, if any
		 * here we are comparing against test error scenario and its code
		 */
	  
	  @Test
	  public void testUploadXlsErrorCase()
	  {
		  Response res = service.getTemplateUploadResponseErrorCase(prop.getProperty("UPLOAD_FILE_ERRORCASE"));
		  Assert.assertEquals(res.jsonPath().getString(prop.getProperty("ERROR_CODE_KEY")), prop.getProperty("TEST_ERROR_CODE"));				    
	  }
	
  /*
	  @Test
	  public void testCreateCustomer()
	  {
		  //add logic to send json of customers
		  
		  RequestSpecification request = RestAssured.given();
		  request.header("Content-Type","application/json");
		  
		  JsonObject customer = new JsonObject();
	      JsonObject address = new JsonObject();
		  JsonObject parent = new JsonObject(); 
		  
		  customer.addProperty("name","John");
		  customer.addProperty( "designation","Manager");
		  	 
		  address.addProperty("city","New York");
		  address.addProperty("country","USA");
		  
		  parent.add("customer", customer);
		  parent.add("address", address);
		  	  
		  request.body(parent.getAsString());
		  	  
		  Response res = RestAssured.given().
				    when().post("https://5d6fb7c7-9d21-43dc-8f66-17c54ba4c9e7.mock.pstmn.io/customers").
				    then().extract().response();
		  Assert.assertEquals(res.statusCode(), 201);
		  Assert.assertEquals(res.jsonPath().getString("id"), "4");	
				    
	  }*/
	  
  
  @BeforeTest
  public void beforeTest() throws IOException {
	  
	 // Populate test data object repository	
	  ReadTestData readTestData = new ReadTestData();
	  prop = readTestData.getObjectRepository();
  }

  @AfterTest
  public void afterTest() {
  }

}
