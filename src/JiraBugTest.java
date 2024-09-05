import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraBugTest {

	@Test
	public void JiraIssueTest(){
		
		//Adding Bugs Issue in Jira
		RestAssured.baseURI="https://kjslsajay.atlassian.net";
		String createdIssue=given().log().all().header("Content-Type","application/json").header("Authorization","Basic a2pzbHNhamF5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjB1ZGljbkw0TVJ4dWpvYV9tSWlfYjlHWXNiTlltc3l2T0h5cHpMblNEX0VOWHVEcUczUWQ5akFpblpwTXF6c3BfS2pGbGNPYUJkRVRVVHgzY1FzRnRVS0FPQlo2blFMd3FEZGVxazJfQzRwTXRvUVcweG1ZUF83TVZrcktJdTdILXA3ZnU4U3BxZGV6YTZjYlNaekRpSmhPMlN1VERub1FBam5kNHRjNGk1dVE9MjU2RkUxQjY=")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Please give me some motivation\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "").when().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath as=new JsonPath(createdIssue);
		String issueId=as.getString("id");
		
		//Adding attachment to the bug Issue we created
		given().log().all().header("Authorization","Basic a2pzbHNhamF5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjB1ZGljbkw0TVJ4dWpvYV9tSWlfYjlHWXNiTlltc3l2T0h5cHpMblNEX0VOWHVEcUczUWQ5akFpblpwTXF6c3BfS2pGbGNPYUJkRVRVVHgzY1FzRnRVS0FPQlo2blFMd3FEZGVxazJfQzRwTXRvUVcweG1ZUF83TVZrcktJdTdILXA3ZnU4U3BxZGV6YTZjYlNaekRpSmhPMlN1VERub1FBam5kNHRjNGk1dVE9MjU2RkUxQjY=")
		.header("X-Atlassian-Token","no-check").pathParam("BugId", issueId)
		.multiPart("file",new File("C:/Users/HP/OneDrive/Pictures/Screenshots/Screenshot 2023-07-20 123815.png"))
		.when().post("rest/api/3/issue/{BugId}/attachments")
		.then().log().all().assertThat().statusCode(200);
	}
}
