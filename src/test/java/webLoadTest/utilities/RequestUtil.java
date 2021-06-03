package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.output.WriterOutputStream;
import webLoadTest.test.TestBaseBrowser;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil extends TestBaseBrowser {

    public static StringWriter requestWriter;
    public static PrintStream requestCapture;

    public static void requestInfo() {

        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);
    }

    public static void requestInfoPrint() {
        //System.out.println("<pre>" + requestWriter.toString().replace("\t", "&nbsp;&nbsp;").replace(" ", "&nbsp;&nbsp;") + "</pre>");
        Reporter.test.log(Status.INFO, "<pre>" + requestWriter.toString().replace("\t", "&nbsp;&nbsp;").replace(" ", "&nbsp;&nbsp;") + "</pre>");
    }

    public static void printHTMLResponse(Response response) {

        Reporter.test.pass(MarkupHelper.createCodeBlock(response.andReturn().asString(), CodeLanguage.XML));

    }

    public static void printJSONResponse(Response response) {
        Reporter.test.pass(MarkupHelper.createCodeBlock(response.andReturn().asString(), CodeLanguage.JSON));
    }

    private static String queryParam(List<HashMap<String, String>> params) {

        StringBuilder builder = new StringBuilder();

        params.forEach(pairs -> {
            if (!builder.toString().isEmpty()) {
                builder.append('&');
            }
            pairs.forEach((key, value) -> {
                builder.append(key)
                        .append("=")
                        .append(value);
            });
        });

        return builder.toString();
    }

    private static RequestSpecification getRequestSpecs(List<HashMap<String, String>> params) {
        RequestSpecification reqs = RestAssured
                .given()
                .urlEncodingEnabled(true)
                .contentType(GlobalVars.getInstance().getCONTENT_TYPE())
                .when();

//        for (HashMap<String, String> item : params) {
//            reqs.parameter(item, item.getValue());
//        }

		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});


        return reqs;
    }

    private static RequestSpecification getRequestSpecs_urlEncoded(List<HashMap<String, String>> params) {
        RequestSpecification reqs = RestAssured.given()
                .urlEncodingEnabled(true)
                .contentType("application/x-www-form-urlencoded")
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    public static RequestSpecification getRequestSpecsAuth(List<HashMap<String, String>> params, String authToken) {
        RequestSpecification reqs = RestAssured.given()
                .headers("authorization", authToken)
                .urlEncodingEnabled(true)
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    public static RequestSpecification getRequestSpecsAuthUsingRetrievalId(List<HashMap<String, String>> params, String authToken) {
        RequestSpecification reqs = RestAssured.given()
                .headers("authorization", authToken)
                .headers("retrieval-id", TestBaseBrowser.retrievalID)
                .urlEncodingEnabled(true)
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    public static Response createGetRequest(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecs(params).get(path);

        return res;
    }

    public static Response createHeadRequestUsingCookies(List<HashMap<String, String>> params, String headerETag, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.headRequestSpecsUsingCookies(params, headerETag).head(path);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);

        //test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        return res;
    }

    private static RequestSpecification headRequestSpecsUsingCookies(List<HashMap<String, String>> params, String headerETag) {
        RequestUtil.requestInfo();

        RequestSpecification reqs = RestAssured
                .given()

                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))

                .urlEncodingEnabled(true)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("If-Match", headerETag)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    public static Response createPostATSTriggerRequest(Map body, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .auth()
                .preemptive()
                .basic("ttn_qa","123456@tothenew")
                .urlEncodingEnabled(true)
                .contentType("application/json")
                .header("Client-key","tothenew")
                .header("employer-identifier","SOUTHERN_CROSS")
                /*.header("Content-Type", "application/json")*/
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .when()
                .body(body)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPostRequest(Map body, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .auth()
                .preemptive()
                .basic("ttn_qa","123456@tothenew")
                .urlEncodingEnabled(true)
                .contentType("application/json")
                .header("Client-key","tothenew")
                .header("employer-identifier","QA_Fedex_Auto")
                /*.header("Content-Type", "application/json")*/
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .when()
                .body(body)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPostListRequest(List<HashMap<String, String>> params, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .auth()
                .preemptive()
                .basic("ttn_qa","123456@tothenew")
                .urlEncodingEnabled(true)
                .contentType("application/json")
                .header("Client-key","tothenew")
                .header("employer-identifier","QA_FedEx")
                /*.header("Content-Type", "application/json")*/
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .when()
                .body(params)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPostJSONRequest(String params, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .auth()
                .preemptive()
                .basic("ttn_qa","123456@tothenew")
                .urlEncodingEnabled(true)
                .contentType("application/json")
                .header("Client-key","tothenew")
                .header("employer-identifier","QA_FedEx")
                /*.header("Content-Type", "application/json")*/
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .when()
                .body(params)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }


    public static Response createPostRequestUsingCookies(Map body, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .contentType("application/json;charset=UTF-8")
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "application/json, text/plain, */*")
                .when()
                .body(body)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPostListRequestUsingCookies(List<HashMap<String, String>> params, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .contentType("application/json;charset=UTF-8")
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "application/json, text/plain, */*")
                .when()
                .body(params)
                .log().all(true)
                .post(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPutListRequestUsingCookies(List<HashMap<String, String>> params, String BaseURI, String BasePath, String BaseURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .contentType("application/json;charset=UTF-8")
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "application/json, text/plain, */*")
                .when()
                .body(params)
                .log().all(true)
                .put(BaseURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPutRequestUsingCookies(Map body, String BaseURI, String BasePath, String SubURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .contentType("application/json;charset=UTF-8")
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .when()
                .body(body)
                .log()
                .all(true)
                .put(SubURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createPutRequestUsingBasicAuth(Map body, String BaseURI, String BasePath, String SubURL) {


        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        RequestUtil.requestInfo();

        Response res = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))
                .urlEncodingEnabled(true)
                .contentType("application/json")
                .header("Authorization", "basic c2VyaGlpOmdoZmRmITEyMw==")
                .header("Client-key", "tothenew")
                .when()
                .body(body)
                .log()
                .all(true)
                .put(SubURL);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);


        return res;
    }

    public static Response createGetRequestUsingCookies(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsUsingCookies(params).get(path);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);

        return res;
    }

    public static Response createGetRequestUsingClientID(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsUsingClientID(params).get(path);
        //test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);

        return res;
    }

    public static Response createGetRequestUsingBasicAuthClientId(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsUsingBasicAuthClientID(params).get(path);

        RequestUtil.requestInfoPrint();
        RequestUtil.printJSONResponse(res);

        return res;
    }

    private static RequestSpecification getRequestSpecsUsingCookies(List<HashMap<String, String>> params) {
        RequestUtil.requestInfo();
        RequestSpecification reqs = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))

                .urlEncodingEnabled(true)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Origin", GlobalVars.ORIGIN)
                .header("Upgrade-Insecure-Requests", "1")
                .header("referer", GlobalVars.BASE_URL + "/account/login/?next=/dashboard/list")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    private static RequestSpecification getRequestSpecsUsingClientID(List<HashMap<String, String>> params) {
        RequestUtil.requestInfo();
        RequestSpecification reqs = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))

                .contentType("application/json")
                .cookies(TestBaseBrowser.postLoginCookies)
                .header("Authorization", "basic c2VyaGlpOmdoZmRmITEyMw==")
                .header("Client-key", "tothenew")
                .header("Accept", "*/*")
                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    private static RequestSpecification getRequestSpecsUsingBasicAuthClientID(List<HashMap<String, String>> params) {
        RequestUtil.requestInfo();
        RequestSpecification reqs = RestAssured
                .given()
                .config(RestAssured.config().logConfig(new LogConfig(RequestUtil.requestCapture, true)))

                .header("Client-key", "tothenew")
                .header("Authorization", "basic Y2hhcnVfdHRuOjM2MGxvZ2ljYQ==")

                .log()
                .everything()
                .when();
		params.forEach(pairs -> {

			pairs.forEach(reqs::parameter);
		});
        return reqs;
    }

    public static Response createGetRequestUrlEncoded(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecs_urlEncoded(params).get(path);
        Reporter.test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        return res;
    }

    public static Response createGetRequestAuthToken(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path, String authorization) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsAuth(params, authorization).get(path);
        Reporter.test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        return res;
    }

    public static Response createGetRequestAuthTokenUsingRetrievalId(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path, String authorization) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsAuthUsingRetrievalId(params, authorization).get(path);
        Reporter.test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + res.andReturn().asString());

        return res;
    }

    private static String getURLBuilder(String path, List<HashMap<String, String>> params) {

        return RestAssured.baseURI + RestAssured.basePath + path + "?" + RequestUtil.queryParam(params);
    }

    public static void appendResponseCode200(Response res) {
        try {
            if (res.getStatusCode() == 200) {

                Reporter.test.log(Status.INFO, "Response Time :: " + res.time());
                Reporter.test.log(Status.INFO, "Response Code ::" + res.getStatusCode());

                printJSONResponse(res);
            } else {

                Reporter.test.log(Status.FAIL, "Response Code ::" + res.getStatusCode());
            }
        } catch (Exception e) {

            Reporter.test.log(Status.ERROR, e.getMessage());
        }
    }

    public static void appendResponseCode202(Response res) {
        try {
            if (res.getStatusCode() == 202) {

                Reporter.test.log(Status.INFO, "Response Time :: " + res.time());
                Reporter.test.log(Status.INFO, "Response Code ::" + res.getStatusCode());

                printJSONResponse(res);
            } else {

                Reporter.test.log(Status.FAIL, "Response Code ::" + res.getStatusCode());
            }
        } catch (Exception e) {

            Reporter.test.log(Status.ERROR, e.getMessage());
        }
    }

    public static void appendResponseCode204(Response res) {
        try {
            if (res.getStatusCode() == 204) {

                Reporter.test.log(Status.INFO, "Response Time :: " + res.time());
                Reporter.test.log(Status.INFO, "Response Code ::" + res.getStatusCode());

                printJSONResponse(res);
            } else {

                Reporter.test.log(Status.FAIL, "Response Code ::" + res.getStatusCode());
            }
        } catch (Exception e) {

            Reporter.test.log(Status.ERROR, e.getMessage());
        }
    }

    public static void appendResponseCode200_ForCSRFToken(Response res) {
        try {
            if (res.getStatusCode() == 200) {

            } else {

            }
        } catch (Exception e) {

        }
    }

    public static void appendResponseCode201(Response res) {
        try {
            if (res.getStatusCode() == 201) {
                Reporter.test.log(Status.INFO, "Response Time :: " + res.time());
                Reporter.test.log(Status.INFO, "Response Code ::" + res.getStatusCode());

            } else {

                Reporter.test.log(Status.FAIL, "Response Code ::" + res.getStatusCode());
            }
        } catch (Exception e) {
            Reporter.test.log(Status.ERROR, e.getMessage());
        }
    }

    public static Response getEmailRequestSpecsUsingBasicAuthClientID(List<HashMap<String, String>> params, String BaseURI, String BasePath, String path, String authorization) {
        RestAssured.baseURI = BaseURI;
        RestAssured.basePath = BasePath;

        Response res = RequestUtil.getRequestSpecsAuthUsingRetrievalId(params, authorization).get(path);
        Reporter.test.log(Status.INFO, "Request URL is ::" + RequestUtil.getURLBuilder(path, params));

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + res.andReturn().asString());

        return res;
    }

}