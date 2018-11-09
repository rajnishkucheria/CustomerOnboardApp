package zytest.custonboardapp.operations;

import com.google.gson.JsonObject;

public class JsonCreationUtil {
	
	
	public JsonObject createTestJsonObject ()
	{
		JsonObject customer = new JsonObject();
		JsonObject address = new JsonObject();
		JsonObject parent = new JsonObject(); 
		  
		  customer.addProperty("name","John");
		  customer.addProperty( "designation","Manager");
		  	 
		  address.addProperty("city","New York");
		  address.addProperty("country","USA");
		  
		  parent.add("customer", customer);
		  parent.add("address", address);
		  
		 return parent;
	}

}
