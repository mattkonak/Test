package githubAPItests;

import org.testng.annotations.Test;
import beans.RepoObject;
import beans.UnauthorizedUserMessageBody;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.baseURI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigurationReader;

public class Test1 {
	Response response;
	RestAssured request;
	RequestSpecification httpRequest = RestAssured.given();
	ResponseBody body;
	RepoObject repoObj;
	JSONObject obj;
	JSONArray arr;
	UnauthorizedUserMessageBody unauthMessage;

	
	public static String username = ConfigurationReader.getProperty("userName");
	public static String password = ConfigurationReader.getProperty("password");


	@BeforeMethod
	public void setup() {
		RestAssured.baseURI = ConfigurationReader.getProperty("baseURI");
		repoObj=new RepoObject();
		unauthMessage=new UnauthorizedUserMessageBody();
	}

	
	@Test
	public void authorizedUserStatusCodeVerification() {
		int expectedStatusCode=200;
		int actual=0;
		response = RestAssured.given().relaxedHTTPSValidation().auth().preemptive().basic(username, password)
				.when().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().get("/user/repos");
		actual=response.getStatusCode();
		Assert.assertEquals(actual, expectedStatusCode);
	}
	
	@Test
	public void authorizationUserFullNameReposVerification() {	
		List<String> expectedFullname=new ArrayList<String>();
		expectedFullname.add("mattkonak/example");
		expectedFullname.add("mattkonak/example2");
		expectedFullname.add("mattkonak/example3");
		expectedFullname.add("mattkonak/example4");
		expectedFullname.add("mattkonak/Test");
		List<String> actual=new ArrayList<String>();
		response = RestAssured.given().relaxedHTTPSValidation().auth().preemptive().basic(username, password).
        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		for (int i = 0; i < arr.length(); i++) {
			actual.add(arr.getJSONObject(i).getString("full_name"));
		}
		repoObj.setFull_name(actual);
		actual=repoObj.getFull_name();
		Assert.assertEquals(actual, expectedFullname);
	}
	
	@Test
	public void authorizeduUserGivenfirstPageandOnePerPageItwillreturnOneRepoObjectVerificationByUsingParamaters() {
		int expected=1;
		int actual=0;
		response = RestAssured.given().param("page","1").param("per_page","1").relaxedHTTPSValidation().auth().preemptive().
				basic("mattkonak", "Abk141414").
		        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		actual=arr.length();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void authorizeduUserGivenSecondPageItwillreturnZeroRepoObjectVerificationByUsingParamater() {
		int expected=0;
		int actual=0;
		response = RestAssured.given().param("page","2").relaxedHTTPSValidation().auth().preemptive().
				basic(username, password).
		        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		actual=arr.length();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void authorizeduUserGivenFirstPageAndOnePerPageOwnerLoginVerificationByUsingParamater() {
		String expected="mattkonak";
		String actual="";
		response = RestAssured.given().param("page","1").param("per_page","1").relaxedHTTPSValidation().auth().preemptive().
				basic(username, password).
		        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		repoObj.setOwnerLogin(arr.getJSONObject(0).getJSONObject("owner").getString("login"));
		actual=repoObj.getOwnerLogin();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void authorizeduUserGivenFirstPageAndOnePerPageOwnerIDVerificationByUsingParamater() {
		int expected=58093137;
		int actual=0;
		response = RestAssured.given().param("page","1").param("per_page","1").relaxedHTTPSValidation().auth().preemptive().
				basic(username, password).
		        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		repoObj.setLoginId(arr.getJSONObject(0).getJSONObject("owner").getInt("id"));
		actual=repoObj.getLoginId();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void authorizeduUserGivenfirstPageandFourPerPageItwillreturnFourRepoObjectVerifiationByUsingParamaters() {
		int expected=4;
		int actual=0;
		response = RestAssured.given().param("page","1").param("per_page","4").relaxedHTTPSValidation().auth().preemptive().
				basic(username, password).
		        when().get("/user/repos");
		arr=new JSONArray(response.asString());
		actual=arr.length();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void unauthorizedUserAttamptStatusCodeVerification() {
		int expectedStatusCode=401;
		response = RestAssured.given().relaxedHTTPSValidation().
                when().get("/user/repos");	
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
	}
	
	@Test
	public void unauthorizedUserAttamptMessageVerificationVerification() {
		String expectedMessages="Requires authentication";
		String actual="";
		response = RestAssured.given().relaxedHTTPSValidation().
                when().get("/user/repos");
		obj=new JSONObject(response.asString());
		unauthMessage.setMessage(obj.getString("message"));
		actual=unauthMessage.getMessage();
		Assert.assertEquals(actual, expectedMessages);
	}
	
	@Test
	public void unauthorizedUserAttamptdocumentation_urlVerificationVerification() {
		String expecteddocumentation_url="https://developer.github.com/v3/repos/#list-your-repositories";
		String actual="";
		response = RestAssured.given().relaxedHTTPSValidation().
                when().get("/user/repos");
		obj=new JSONObject(response.asString());
		unauthMessage.setDocumentation_url(obj.getString("documentation_url"));
		actual=unauthMessage.getDocumentation_url();
		Assert.assertEquals(actual, expecteddocumentation_url);
	}



}
