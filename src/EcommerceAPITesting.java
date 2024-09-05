import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;

import PojoClasses.EcomLoginInfo;
import PojoClasses.EcomLoginResp;
import PojoClasses.OrderEcom;
import PojoClasses.Orders;

public class EcommerceAPITesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Logging in the Website
		RequestSpecification loginReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		EcomLoginInfo userDetails=new EcomLoginInfo();
		userDetails.setUserEmail("saiajay@kjsls.com");
		userDetails.setUserPassword("Saiajay2324$");
		
		RequestSpecification userResp= given().log().all().spec(loginReq).body(userDetails);
	    EcomLoginResp userLoginResp= userResp.when().post("api/ecom/auth/login").then()
	    		.log().all().extract().response().as(EcomLoginResp.class);
	    String token=userLoginResp.getToken();
	    String userId=userLoginResp.getUserId();
	    
	    
	    //Creating Product
	    RequestSpecification createReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
	    		.addHeader("Authorization",token).build();
	    String createdResp= given().spec(createReq).param("productName","Heart Symbol")
	    .param("productAddedBy", userId)
	    .param("productCategory", "Gifts")
	    .param("productSubCategory","Soft Toys")
	    .param("productPrice", "12345")
	    .param("productDescription", "AS originals")
	    .param("productFor", "All")
	    .multiPart("productImage",new File("C:/Users/HP/Downloads/AppIcon.jpg"))
	    .when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
	    
	    JsonPath resp=new JsonPath(createdResp);
	    String productId=resp.getString("productId");
	    System.out.println(productId);
	    
	    
	    //Creating Order
	    RequestSpecification orderReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
	    		.addHeader("Authorization",token).setContentType(ContentType.JSON).build();
	    Orders o=new Orders();
	    o.setCountry("India");
	    o.setProductOrderedId(productId);
	    ArrayList<Orders> orderList=new ArrayList<>();
	    orderList.add(o);
	    
	    OrderEcom orderPayload=new OrderEcom();
	    orderPayload.setOrders(orderList);
	    
	    String orderResp=given().log().all().spec(orderReq).body(orderPayload)
	    .when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
	    JsonPath OrderResp1=new JsonPath(orderResp);
	    
	    
	    //Deleting the added product
	    given().log().all().spec(orderReq).pathParam("productId", productId) //Declaring Path Parameters
	    .when().delete("api/ecom/product/delete-product/{productId}") //Using Path Parameters
	    .then().log().all();

	}

}
