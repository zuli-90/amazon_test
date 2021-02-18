package Source.Step_Definition;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class RequestApi_Step_Definition_INT {
    String url;
    String transactionid= "paymentTransactionLogs[0].transactionId";
    JsonPath jsonPathEvaluator;
    String origin = "int.sdk.smartpay.vwfs.io";
    String checkout_api_base_url_int = "https://api.int.smartpay.vwfs.io";


    @Then("^Send request in 'INT' Environment for status is: \"([^\"]*)\"$")
    public void resultStatusIs(String sBody) {
        getTransactionIDINT();

        this.url= checkout_api_base_url_int+"/payment/status/"+jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", origin );

        boolean status = false ;
        while (true) {
            try {
                Response response= httpRequest.get(this.url);
                response.then().statusCode(500).extract().response().prettyPrint();
                jsonPathEvaluator = response.jsonPath();
                Assert.assertEquals(jsonPathEvaluator.get("paymentStatus"), sBody);
                //  status = sBody;


            } catch (NoSuchElementException ex) {
                break;
            }
            if (status == false) {
                break;
            }
        }

    }

    @Given("^Send a request for \"([^\"]*)\" in 'INT' Merchant \"([^\"]*)\"$")
    public void sendARequestForMerchant(String request, String merchantkey) throws InterruptedException {
        Thread.sleep(500);
        getTransactionIDINT();

        // Sample2 string
        String str1 = Context.Sample2.replace("{{transaction_id}}", jsonPathEvaluator.get(transactionid));
        String str2= str1.replace("{{g_merchant_key}}",merchantkey);
        String Request= str2;

        // post  request
        this.url= checkout_api_base_url_int+"/payment/"+request;

        System.out.println(Request);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type", "application/json")
                        .header("Origin", origin)
                        .body(Request);

        Response response =  httpRequest.post(this.url);
        response.prettyPrint();
        ResponseBody body = response.getBody();
        body.prettyPrint();
        String bodyAsString = body.jsonPath().getString("paymentStatus");
        System.out.println(bodyAsString);

    }


    @Given("^Send a request for \"([^\"]*)\" in 'INT' Merchant \"([^\"]*)\", using currency: \"([^\"]*)\" and amount: \"([^\"]*)\"$")
    public void sendARequestForCaptureDisableAutoCapture(String request, String merchantkey, String currency,String amount) throws InterruptedException {
        Thread.sleep(500);
        getTransactionIDINT();

        // Sample1 string
        String str1 = Context.Sample1.replace("{{transaction_id}}", jsonPathEvaluator.get(transactionid));
        String str2= str1.replace("{{currency}}",currency);
        String str3= str2.replace("{{g_merchant_key}}",merchantkey);
        String str4= str3.replace("{{amount}}",amount);

        String Capture= str4;

        // post capture request
        this.url= checkout_api_base_url_int+"/payment/"+request;
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type", "application/json")
                        .header("Origin", origin)
                        .body(Capture);

        System.out.println(Capture);
        Response response =  httpRequest.post(this.url);
        response.prettyPrint();

    }


    @Then("^The user check the payment status for the transaction:\"([^\"]*)\" in 'INT' Environment$")
    public void TheusercheckthetransactiondetailforPaymentStatus(String paymentstatus) throws InterruptedException {
        getTransactionIDINT();
        this.url = checkout_api_base_url_int + "/payment/status/" + jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type", "application/json").header("Origin", origin);
        boolean status = false;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(20);
                Response response = httpRequest.get(this.url);
                response.then().statusCode(200).extract();
                jsonPathEvaluator = response.jsonPath();
                System.out.println(jsonPathEvaluator.getString("paymentStatus"));
                String status_text = jsonPathEvaluator.get("paymentStatus");
                status = status_text.equalsIgnoreCase(paymentstatus);

            } catch (NoSuchElementException ex) {
                break;
            }
            if (status == true) {
                System.out.println("Succesful");

                break;
            }
            if (status != true) {
                System.out.println("Error fail payment status");
                break;
            }
        }
    }

    @Then("^The user check the transaction detail for refunding Amount:\"([^\"]*)\" in 'INT' Environment$")
    public void TheusercheckthetransactiondetailforrefundingAmount(Integer refundingAmount) throws InterruptedException {
        getTransactionIDINT();
        this.url= checkout_api_base_url_int+"/payment/transaction/"+jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", origin );
        Thread.sleep(500);
        Response response= httpRequest.get(this.url);
        response.then().statusCode(200);
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator.getString("paymentTransaction.refundingAmount"));
        Assert.assertEquals(jsonPathEvaluator.get("paymentTransaction.refundingAmount"), refundingAmount);

    }


    @Then("^In transaciton detail for 'INT' Enviorment is the fail Reason: \"([^\"]*)\"$")
    public void IntransacitondetailforDevEnviormentisthefailReason(String paymentdescription) throws InterruptedException {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/transaction/"+jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );
        System.out.println(this.url);

        Response response= httpRequest.get(this.url);
        response.then().statusCode(200);
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator.getString("paymentTransaction.failReason"));
        Assert.assertEquals(jsonPathEvaluator.get("paymentTransaction.failReason"), paymentdescription);
    }


    @Then("^Delete saved payment option in 'INT' Environment$")
    public void deleteSavedPaymentOption() throws InterruptedException {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/transaction/"+jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );
        System.out.println(this.url);

        Response response= httpRequest.get(this.url);
        response.then().statusCode(500).extract().response().prettyPrint();
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonPathEvaluator.getString("paymentTransaction.fspayResponses[1].operation"));
        Assert.assertEquals(jsonPathEvaluator.get("paymentTransaction.fspayResponses[1].operation"), "DELETE_SAVED_PAYMENT_OPTION");
    }


    @Then("^User check transactionOverview \"([^\"]*)\" : \"([^\"]*)\" as String type in 'INT' Environment$")
    public void userChecktransactionOverview(String jsonmap, String jsondata) throws Throwable {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/status/"+jsonPathEvaluator.get(transactionid);

        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );

        Response response= httpRequest.get(this.url);
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonmap+"   :   "+ jsonPathEvaluator.getString("transactionOverview."+jsonmap));
        Assert.assertEquals(jsonPathEvaluator.get("transactionOverview."+jsonmap), jsondata);

    }

    @Given("^Send a request for \"([^\"]*)\" using description \"([^\"]*)\" in 'INT' Merchant \"([^\"]*)\"$")
    public void sendARequestForMerchantwithDescription(String request, String description,String merchantkey) throws InterruptedException {
        Thread.sleep(500);
        getTransactionIDINT();

        // Sample2 string
        String str1 = Context.Sample3.replace("{{transaction_id}}", jsonPathEvaluator.get(transactionid));
        String str2= str1.replace("{{g_merchant_key}}",merchantkey);
        String str3= str2.replace("{{description}}",description);

        String Request= str3;

        // post  request
        this.url= checkout_api_base_url_int+"/payment/"+request;

        System.out.println(Request);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type", "application/json")
                        .header("Origin", origin)
                        .body(Request);

        Response response =  httpRequest.post(this.url);
        ResponseBody body = response.getBody();
        body.prettyPrint();
        String bodyAsString = body.jsonPath().getString("paymentStatus");
        System.out.println(bodyAsString);

    }


    @Then("^User check transactionOverview \"([^\"]*)\" : \"([^\"]*)\" as Integer type in 'INT' Environment$")
    public void userChecktransactionOverviewInteger(String jsonmap, Integer jsondata) throws Throwable {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/status/"+jsonPathEvaluator.get(transactionid);

        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );

        Response response= httpRequest.get(this.url);
        jsonPathEvaluator = response.jsonPath();
        System.out.println( jsonmap+"   :   "+jsonPathEvaluator.getInt("transactionOverview."+jsonmap));
        Assert.assertEquals(jsonPathEvaluator.get("transactionOverview."+jsonmap), jsondata);

    }

    @Then("^User check modification \"([^\"]*)\" : \"([^\"]*)\" as String type in 'INT' Environment$")
    public void userCheckmodification(String jsonmap, String jsondata) throws Throwable {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/status/"+jsonPathEvaluator.get(transactionid);

        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );

        Response response= httpRequest.get(this.url);
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonmap+"   :   "+jsonPathEvaluator.getString("modifications[0]."+jsonmap));
        Assert.assertEquals(jsonPathEvaluator.get("modifications[0]."+jsonmap), jsondata);

    }

    @Then("^User check modification \"([^\"]*)\" : \"([^\"]*)\" as Integer type in 'INT' Environment$")
    public void userCheckmodificationInteger(String jsonmap, Integer jsondata) throws Throwable {
        getTransactionIDINT();
        TimeUnit.SECONDS.sleep(30);
        this.url= checkout_api_base_url_int+"/payment/status/"+jsonPathEvaluator.get(transactionid);
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", "int.sdk.smartpay.vwfs.io" );

        Response response= httpRequest.get(this.url);
        response.then().statusCode(200);
        jsonPathEvaluator = response.jsonPath();
        System.out.println(jsonmap+"   :   "+jsonPathEvaluator.getInt("modifications[0]."+jsonmap));

        Assert.assertEquals(jsonPathEvaluator.get("modifications[0]."+jsonmap), jsondata);

    }


    public void getTransactionIDINT() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String date1= dateFormat.format(date);

        this.url =  "https://api.int.smartpay.vwfs.io/payment/transaction-log?date="+date1+"&numOfRecords=1";
        RequestSpecification httpRequest =
                RestAssured.given().header("Content-Type","application/json").header("Origin", origin );

        Response response= httpRequest.get(this.url);
        jsonPathEvaluator = response.jsonPath();
    }



}

