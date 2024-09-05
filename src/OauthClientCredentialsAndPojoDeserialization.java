import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import PojoClasses.GetCourse;
import PojoClasses.WebAutomation;

public class OauthClientCredentialsAndPojoDeserialization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Generating Access Token
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String AccessTokenJson=given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","trust")
		.when().post("oauthapi/oauth2/resourceOwner/token").asString();
		
		JsonPath as1=new JsonPath(AccessTokenJson);
		String AccessToken=as1.getString("access_token");
		System.out.println(AccessToken);
		
		//Getting List of Courses using AccessToken
		GetCourse as=given().queryParam("access_token",AccessToken)
		.when().get("oauthapi/getCourseDetails").as(GetCourse.class);//POJO class
		
		String[] coursesList= {"Selenium Webdriver Java","Cypress","Protractor"};
		
		//Using POJO classes for PArsing the Json (Deserialization)
		System.out.println(as.getInstructor());
		
		//Getting all Course titles of Web Automation
		List<WebAutomation> webCoursesList=as.getCourses().getWebAutomation();
		ArrayList<String> actualCourses=new ArrayList<>();
		for(int i=0;i<webCoursesList.size();i++) {
			actualCourses.add(webCoursesList.get(i).getCourseTitle());
			System.out.println(webCoursesList.get(i).getCourseTitle());
		}
		
		List<String> expectedCourses=Arrays.asList(coursesList);//Converting Array to List
		Assert.assertTrue(expectedCourses.equals(actualCourses));
	}

}
