package zytest.custonboardapp.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import zytest.custonboardapp.operations.CallRestServices;
import zytest.custonboardapp.operations.JsonCreationUtil;
import zytest.custonboardapp.operations.ReadTestData;

public class TemplateUploadTest  {

	CallRestServices service = new CallRestServices();
	JsonCreationUtil util = new JsonCreationUtil();
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
	
	/* Create customer API call /Customers
	 * here we are passing JSON object and checking for 201 Created response code
	 * This method would be called after Upload template API and when all the validation checks are passed
	 * Here I am keeping it as independent test, can be clubbed later with earlier test
	 */
	  @Test(dependsOnMethods="testUploadXlsPositiveCase")
	  public void testCreateCustomer()
	  {
		  
		  RequestSpecification request = RestAssured.given();
		  request.header("Content-Type","application/json");
		  
		  JsonObject obj = util.createTestJsonObject();
		  request.body(obj.toString());
		  
		  Response res = service.createNewCustomer();
		  Assert.assertEquals(res.statusCode(), Integer.parseInt(prop.getProperty("RESPONSE_CREATED")));				    
	  }
	  
	/* This test is used to validate xls template.
	 * In this example, we are trying to check null fields in the xls and report that
	 * This step would be used as pre-requsite to API call  /Customers/upload so that error cases can be highlighted and fixed
	 */
    @Test
    public void validateXlsTemplate()
    {
	  
	  FileInputStream fs;
	  boolean validationFlag=true;
		try {
			fs = new FileInputStream(System.getProperty("user.dir") + "/" + prop.getProperty("UPLOAD_FILE_ERRORCASE"));
			HSSFWorkbook hw = new HSSFWorkbook(fs);
		    HSSFSheet hs = hw.getSheetAt(0);
		    for (int row = 0; row <= hs.getLastRowNum(); row++) {
		    	int col=0;
		    	HSSFCell cusId, name;
		    	cusId=hs.getRow(row).getCell(col);
		    	name = hs.getRow(row).getCell(col+1);
		    	//Show error in case field validation fails ex. field is empty
		    	if(null==name || null==cusId){
		    		validationFlag = false;
		    		Assert.assertEquals(validationFlag,false);
		    		System.out.println("Name or customer id field is empty");
		    	}  
		    	else{
		    		Assert.assertEquals(validationFlag,true);
		    	}
		    }
		    	
		    hw.close();
		    fs.close();
		} catch (FileNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
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
