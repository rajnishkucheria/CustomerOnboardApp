package zytest.custonboardapp.operations;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CallRestServices {
	
	ReadTestData readData = new ReadTestData();
	Properties prop; 
	
	/* Mock Web service for getTenant config /Tenant/<id>/config
	 * 
	 */
	
	public Response getTenantConfig(String tenantId) throws IOException
	  {
		prop = readData.getAPIRepository();
		String  url = prop.getProperty("TENANT_MOCKAPI")+tenantId+prop.getProperty("TENANT_CONFIG_VALUE_MOCKAPI");
		Response res = RestAssured.given().
				    when().get(url).then().extract().response();
		  return res;	    
	  }
	
	/* Mock Web service for template upload /Customer/upload
	 *  positive test scenario
	 */
	
	public Response getTemplateUploadResponse(String filePath) throws IOException
	  {
		prop = readData.getAPIRepository(); 
		Response res = RestAssured.given().multiPart(new File(System.getProperty("user.dir") + "/" + filePath)).
			    when().post(prop.getProperty("UPLOAD_MOCKAPI")).then().extract().response();
		  return res;	    
	  }
	
	/* Mock Web service for template upload /Customer/upload
	 *  Error test scenario
	 */
	
	public Response getTemplateUploadResponseErrorCase(String filePath) throws IOException
	  {
		prop = readData.getAPIRepository(); 
		Response res = RestAssured.given().multiPart(new File(System.getProperty("user.dir") + "/" + filePath)).
			    when().post(prop.getProperty("UPLOAD_ERROR_MOCKAPI")).then().extract().response();
		  return res;	    
	  }
	
	public Response createNewCustomer() throws IOException
	  {
		prop = readData.getAPIRepository(); 
		Response res = RestAssured.given().
				    when().post(prop.getProperty("CUSTOMER_MOCKAPI")).
				    then().extract().response();
		  return res;	    
	  }

}

