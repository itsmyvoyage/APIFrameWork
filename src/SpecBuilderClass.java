import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

import PojoClasses.AddPlace;
import PojoClasses.Location;

public class SpecBuilderClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Constructing Payload using Pojo classes
		AddPlace as=new AddPlace();
		as.setAccuracy(50);
		as.setName("Sai Ajay House");
		as.setPhone_number("(+91) 983 893 3937");
		as.setAddress("29, side layout, cohen 09");
		
		List<String> myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		as.setTypes(myList);
		
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		as.setLocation(l);
		
		as.setWebsite("saiajay@kjsls.com");
		as.setLanguage("Telugu");
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Declaring Request Spec Builder
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON).build();
		
		//Declaring Response Spec Builder
		ResponseSpecification res= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification reqSpec=given().log().all().spec(req).body(as); //Internally convert Java Object AS to Json format using Serialization
				
		String jsonResponse=reqSpec.when().post("maps/api/place/add/json")
		.then().log().all().spec(res).extract().response().asString();
		
		JsonPath as1=new JsonPath(jsonResponse);
		System.out.println(as1.getString("place_id"));
		

	}

}
