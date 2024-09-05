import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle){
		RestAssured.baseURI="http://216.10.245.166";
		Response addBookJson=given().header("Content-Type","application/json")
		.body(payload.addBookPayload(isbn,aisle)).when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		JsonPath as=new JsonPath(addBookJson.asString());
		String bookId=as.getString("ID");
		System.out.println(bookId);
	}
	
	
	//Write delete book code also for executing without repitition of data
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"as1","2324"},{"as2","2324"},{"as3","2324"}};
	}
}
