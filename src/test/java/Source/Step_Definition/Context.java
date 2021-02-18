package Source.Step_Definition;

public class Context {

   public static String origin = "int.sdk.smartpay.vwfs.io";

    public static String Sample1= "{\n" +
            "\"transactionId\": \"{{transaction_id}}\",\n" +
            "  \"modifiedAmount\": {\n" +
            "    \"amount\": {{amount}},\n" +
            "    \"currencyCode\": \"{{currency}}\",\n" +
            "\"description\":\"Payment capturing\""+
            "  },\n" +
            "  \"merchantKey\": \"{{g_merchant_key}}\",\n" +
            "   \"modificationId\": \"{{transaction_id}}\"\n" +
            "\n" +
            "}";


    public static String Sample2= "{\n" +
            "\"transactionId\": \"{{transaction_id}}\",\n" +
            "  \"merchantKey\": \"{{g_merchant_key}}\",\n" +
            "\"modificationId\": \"{{transaction_id}}\"\n" +
            "}";

    public static String Sample3= "{\n" +
            "\"transactionId\": \"{{transaction_id}}\",\n" +
            "  \"merchantKey\": \"{{g_merchant_key}}\",\n" +
            "\"modificationId\": \"{{transaction_id}}\",\n" +
            "\"description\": \"{{description}}\"\n" +
            "}";


    public class Merchant
    {
        public String AutoCaptureDEV;
        public String ManualCaptureDEV;

        public Merchant(String autoCaptureDEV, String manualCaptureDEV){
            AutoCaptureDEV= autoCaptureDEV;
            ManualCaptureDEV= manualCaptureDEV;
        }
    }


}
