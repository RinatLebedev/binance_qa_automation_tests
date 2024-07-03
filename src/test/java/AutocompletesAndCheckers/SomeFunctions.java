package AutocompletesAndCheckers;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public interface SomeFunctions {
    class TestingFunctions{
        @Step("Функция заполнения списка(List) элементами из ElementsCollection")
        public static List<String> listFillingFunction(ElementsCollection elementsCollection){
            List<String> list = new ArrayList<>(elementsCollection.size());
            for(int i = 0; i < elementsCollection.size(); i++){
                list.add(elementsCollection.get(i).getText());
            }
            return list;
        }

        @Step("Функция заполнения коллекции Map элементами из двух ElementsCollection")
        public static Map<String, String> mapFillingFunction(ElementsCollection el1, ElementsCollection el2){
            Map<String,String> map = new HashMap<>();
            for(int i = 0; i < el1.size(); i++){
                map.put(el1.get(i).getText(), el2.get(i).getText());
            }
            return map;
            //map.forEach((key, value) -> map.put(el1.get(map..getText(),)));
        }

        @Step("Функция, выводящая на экран элементы коллекции список(List)")
        public static void showList(List <String> list){
            for (int i = 0; i < list.size(); i++){
                System.out.println(list.get(i));
            }
            //list.forEach(() -> list.get(list.));
        }

        @Step("Функция, выводящая на экран элементы коллекции словарь(Map)")
        public static void showMap(Map<String,String> map){
            map.forEach((key, value) -> System.out.println(key + " " + value));
        }

        @Step("Запись данных в текстовый файл из коллекции словарь(Map)")
        public static void writeToTextFile(String path, Map<String,String> map) throws FileNotFoundException {
            File file = new File(path);
            PrintWriter pw = new PrintWriter(file);
            map.forEach((key, value) -> pw.println(key + " " + value));
            pw.close();
        }

        @Step("Перегруженный метод записать в текстовый файл из строки")
        public static void writeToTextFile(String path, String string) throws FileNotFoundException{
            File file = new File(path);
            PrintWriter pw = new PrintWriter(file);
            pw.println(string);
            pw.close();
        }

        @Step("Чтение данных из текстового файла")
        public static String readFromTextFile(String path) throws FileNotFoundException{
            File file = new File(path);
            String output ="";
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                output +=scanner.nextLine();
            }
            scanner.close();
            return output;
        }
    }

    class apiTestingFunctions{
        @Step("Получение значения timestamp в формате String")
        public static String getTimestamp(){
            long rawTimestamp = System.currentTimeMillis();
            String timestamp = String.valueOf(rawTimestamp);
            return timestamp;
        }

        @Step("Получение MAC из пути, составленного из параметров запроса")
        public static byte[] getByteMAC(String queryPath, String privateKeyPath) throws NoSuchAlgorithmException, InvalidKeyException {
            /**
             * Mac - создает код аутентификации сообщений(MAC - Message Authentication Code) из двичных данных,
             * это дайджест сообщений, зашифрованный секретным ключом
             * Подлинность MAC можно проверить только при наличии секретного ключа
             */
            byte[] hmacSha256 = null;
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKeyPath.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(queryPath.getBytes());
            return hmacSha256;
        }

        @Step("Получение значения signature в формате String")
        public static String getSignature(byte[] hmanSha256){
            final char[] hexArray = "0123456789abcdef".toCharArray();
            char[] rawHexChars = new char[hmanSha256.length * 2];
            for (int j = 0, v; j < hmanSha256.length; j++) {
                v = hmanSha256[j] & 0xFF;
                rawHexChars[j * 2] = hexArray[v >>> 4];
                rawHexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            String signature = String.valueOf(rawHexChars);
            return signature;
        }

        @Step("Выведение на экран коллекции Map <String, Object>")
        public static void showStringObjectMap(Map<String, Object> someMap){
            for(Map.Entry<String, Object> pair : someMap.entrySet()){
                String value = String.valueOf(pair.getValue());
                System.out.println(pair.getKey() + " = " + value);
            }
        }

        @Step("Преобразовать Map <String, Object> в Map <String, String>")
        public static Map <String, String> convertFromStringObjectMapToStringStringMap(Map<String, Object> someMap){
            Map<String, String> newMap = new HashMap<>();
            for(Map.Entry<String, Object> pair : someMap.entrySet()){
                newMap.put(pair.getKey(),String.valueOf(pair.getValue()));
            }
            return newMap;
        }

    }
}
