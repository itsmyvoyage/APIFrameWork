import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Validating the Add Place API
		
		//given - all input details
		//when - Submit the API (Resource, HTTP method)
		//Then - validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key","qaclcick123").header("Content-Type","application/json")
		.body(payload.getDetails()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().asString();
		
		JsonPath as=new JsonPath(response); //used for parsing Json
		String placeId=as.getString("place_id");
		
		//Updating the Address
		String newAddress="70 winter walk, USA";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		//Getting the place
		String getPlace=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
		.when().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(getPlace);
		JsonPath as1=new JsonPath(getPlace);
		String updatedAddress=as1.getString("address");
		
		Assert.assertEquals(newAddress, updatedAddress);
		
		
		//Dealing with Json data that is externally in the computer as a json file
		//get the file -> convert the json data to Byte -> then Byte to String bcoz body() method accepts only string format
		String jsonFileData=new String(Files.readAllBytes(Paths.get("D:\\RestAssured\\staticPayload.json")));
		String response1=given().log().all().queryParam("key","qaclcick123").header("Content-Type","application/json")
				.body(jsonFileData).when().post("maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)").extract().asString();
				
				JsonPath as2=new JsonPath(response1); //used for parsing Json
				String placeId1=as2.getString("place_id");
		
		
	}

}
