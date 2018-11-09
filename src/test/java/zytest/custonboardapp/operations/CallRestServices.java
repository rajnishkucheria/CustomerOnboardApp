package zytest.custonboardapp.operations;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CallRestServices {
	
	/* Mock Web service for getTenant config /Tenant/<id>/config
	 * 
	 */
	
	public Response getTenantConfig(String tenantId)
	  {
		String  url = "https://5d6fb7c7-9d21-43dc-8f66-17c54ba4c9e7.mock.pstmn.io/tenants/"+tenantId+"/config";
		Response res = RestAssured.given().
				    when().get(url).then().extract().response();
		  return res;	    
	  }
	
	/* Mock Web service for template upload /Customer/upload
	 *  positive test scenario
	 */
	
	public Response getTemplateUploadResponse(String filePath)
	  {
		Response res = RestAssured.given().multiPart(new File(System.getProperty("user.dir") + "/" + filePath)).
			    when().post("https://4f9aa1f6-be6c-4a2a-b03d-becdc7ffe7a8.mock.pstmn.io/customers/upload").then().extract().response();
		  return res;	    
	  }
	
	/* Mock Web service for template upload /Customer/upload
	 *  Error test scenario
	 */
	
	public Response getTemplateUploadResponseErrorCase(String filePath)
	  {
		Response res = RestAssured.given().multiPart(new File(System.getProperty("user.dir") + "/" + filePath)).
			    when().post("https://5d6fb7c7-9d21-43dc-8f66-17c54ba4c9e7.mock.pstmn.io/customers/upload").then().extract().response();
		  return res;	    
	  }
	
	public Response createNewCustomer()
	  {
		  Response res = RestAssured.given().
				    when().post("https://5d6fb7c7-9d21-43dc-8f66-17c54ba4c9e7.mock.pstmn.io/customers").
				    then().extract().response();
		  return res;	    
	  }

}

