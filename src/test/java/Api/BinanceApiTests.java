package Api;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.util.*;

import static AutocompletesAndCheckers.SomeFunctions.TestingFunctions.readFromTextFile;
import static AutocompletesAndCheckers.SomeFunctions.TestingFunctions.writeToTextFile;
import static AutocompletesAndCheckers.SomeFunctions.apiTestingFunctions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Tag("API")
public class BinanceApiTests {
    private final String TEST_URL = "https://testnet.binance.vision";
    private final String URL = "https://api.binance.com";
    private static String API_KEY = "";
    private static String PRIVATE_KEY_PATH = "";

    @Test
    @Description("Проверка подключения")
    public void TestCase1(){
        Specifications.installSpecification(Specifications.requestSpec(TEST_URL), Specifications.responseSpecOK200());
        given()
                .when()
                .get("/api/v3/ping")
                .then().log().all();
    }
    @Test
    @Description("Получение актуального серверного времени")
    public void TestCase2(){
        Specifications.installSpecification(Specifications.requestSpec(TEST_URL), Specifications.responseSpecOK200());
        Response response = given()
                .when()
                .get("/api/v3/time")
                .then().log().all()
                .body("serverTime", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        long actualServerTime = jsonPath.get("serverTime");
        System.out.println(actualServerTime);
    }

    @Test
    @Description("Получение информации о текущих правилах биржевой торговли и символе 'BTCUSDT'")
    public void TestCase3(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String symbolExpectedResponse = "BTCUSDT";
        String baseAssetExpectedResponse = "BTC";
        String quoteAssetExpectedResponse = "USDT";
        Response response = given()
                .param("symbol", "BTCUSDT")
                .when()
                .get("/api/v3/exchangeInfo")
                .then().log().all()
                .body("symbols.symbol", notNullValue())
                .body("symbols.baseAsset", notNullValue())
                .body("symbols.quoteAsset", notNullValue())
                .extract().response();
        JsonPath jsonPath =response.jsonPath();
        ArrayList <String> symbolActialResponse = jsonPath.get("symbols.symbol");
        ArrayList <String> baseAssetActialResponse = jsonPath.get("symbols.baseAsset");
        ArrayList <String> quoteAssetActialResponse = jsonPath.get("symbols.quoteAsset");

        Assertions.assertEquals(symbolExpectedResponse, symbolActialResponse.get(0));
        Assertions.assertEquals(baseAssetExpectedResponse, baseAssetActialResponse.get(0));
        Assertions.assertEquals(quoteAssetExpectedResponse, quoteAssetActialResponse.get(0));
    }

    @Test
    @Description("Получение статистики изменения цены криптовалютной пары DOGE/USDT за последние 24 часа")
    public void TestCase4() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Response response = given()
                .param("symbol","DOGEUSDT")
                .when()
                .get("/api/v3/ticker/24hr")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Map<String, String> actialResponse = jsonPath.getMap("");
        Assertions.assertEquals("DOGEUSDT", actialResponse.get("symbol"));
    }

    @Test
    @Description("Получение актуальной цены криптовалютной пары ETH/USDT")
    public void TestCase5(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Response response = given()
                .param("symbol","ETHUSDT")
                .when()
                .get("/api/v3/ticker/price")
                .then().log().all()
                .body("symbol",  notNullValue())
                .body("price", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Map<String, String> actialResponse = jsonPath.getMap("");
        System.out.println(actialResponse);
        Assertions.assertTrue(null !=actialResponse.get("price"));
    }

    @Test
    @Description("Получение данных о максимальной и минимальной ценах свечей графика криптовалютной пары BTC/USDT")
    public void TestCase6() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Map <String, String > requestParams = new HashMap<>();
        String symbolParam = "BTCUSDT";
        String intervalParam = "1h";
        String limitParam = "5";
        requestParams.put("symbol", symbolParam);
        requestParams.put("interval", intervalParam);
        requestParams.put("limit", limitParam);
        Response response = given()
                .params(requestParams)
                .when()
                .get("/api/v3/klines")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List <ArrayList> allResponse = jsonPath.getList("");
        List <String> finalResponseall = new ArrayList<>();
        for (int i = 0; i < allResponse.size(); i++) {
            int j = i+1;
            finalResponseall.add("Свеча номер " + j);
            finalResponseall.add("Макс. цена " + (String) allResponse.get(i).get(2));
            finalResponseall.add("Мин. цена " + (String) allResponse.get(i).get(3));
        }
        System.out.println(finalResponseall);
        Assertions.assertTrue(finalResponseall.size() == Integer.valueOf(limitParam)*3);
    }

    @Test
    @Description("Создание нового лимитного ордера(заказа) на покупку в паре BTC/USDT")
    public void TestCase7() throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        API_KEY = readFromTextFile("textFiles/apiKey");
        PRIVATE_KEY_PATH = readFromTextFile("textFiles/privateKeyPath");


        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        parameters.put("side", "BUY");
        parameters.put("type", "LIMIT");
        parameters.put("timeInForce", "GTC");
        parameters.put("quantity", "0.0003");
        parameters.put("price", "40000");

        String timestamp = getTimestamp();
        parameters.put("timestamp", timestamp);

        String queryPath = "symbol=BTCUSDT&side=BUY&type=LIMIT&timeInForce=GTC&quantity=0.0003&price=40000&timestamp=" + timestamp;

        String signature = getSignature(getByteMAC(queryPath, PRIVATE_KEY_PATH));
        parameters.put("signature", signature);

        System.out.println("Параметры запроса " + parameters);

        Response response = given()
                .queryParams(parameters)
                .header("X-MBX-APIKEY", API_KEY)
                .when()
                .post("/api/v3/order")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> respons = jsonPath.getMap("");

        //Преобразование значений ключей из типа Object в тип String
        Map<String, String> newRespons = convertFromStringObjectMapToStringStringMap(respons);

        String orderID = newRespons.get("orderId");
        writeToTextFile("textFiles/orderIdtxt", orderID);

        Assertions.assertEquals("NEW", newRespons.get("status"));
        Assertions.assertEquals("BTCUSDT", newRespons.get("symbol"));
        Assertions.assertEquals("BUY", newRespons.get("side"));
        Assertions.assertEquals("0.00030000", newRespons.get("origQty"));
        Assertions.assertTrue(newRespons.get("orderId").length() == 11);
    }


    @Test
    @Description("Удаление открытого, но ещё не исполнившегося, лимитного ордера")
    public void testCase8() throws NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        API_KEY = readFromTextFile("textFiles/apiKey");
        PRIVATE_KEY_PATH = readFromTextFile("textFiles/privateKeyPath");

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");
        String orderId = readFromTextFile("textFiles/orderIdtxt");

        parameters.put("orderId", orderId);

        String timestamp = getTimestamp();
        parameters.put("timestamp", timestamp);

        String queryPath = "symbol=BTCUSDT&orderId=" + orderId + "&timestamp=" + timestamp;

        String signature = getSignature(getByteMAC(queryPath, PRIVATE_KEY_PATH));

        parameters.put("signature", signature);

        Response response = given()
                .queryParams(parameters)
                .header("X-MBX-APIKEY", API_KEY)
                .when()
                .delete("/api/v3/order")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> respons = jsonPath.getMap("");

        //Выведение на экран
        showStringObjectMap(respons);
    }
}