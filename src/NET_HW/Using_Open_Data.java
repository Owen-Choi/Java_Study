package NET_HW;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class Using_Open_Data {
    static String Service_key = "";
    static Category category;
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + Service_key); /*Service Key*/
        //urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20211114", "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line).append('\n');
        }
        Parsing(sb.toString());
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
    static void Parsing(String sb) {
        JSONObject jsonObject = new JSONObject(sb);
        JSONArray item = getRoot(jsonObject);
        //System.out.println(item);
        System.out.println("////////////////////////////////");
        for(int i=0; i<item.length(); i++) {
            //System.out.println(item.get(i));
            Analyzation((JSONObject)item.get(i));
        }
    }
    static JSONArray getRoot(JSONObject jsonObject) {
        JSONObject items = (JSONObject)jsonObject.get("response");
        items = (JSONObject)items.get("body");
        items = (JSONObject)items.get("items");
        JSONArray item = (JSONArray)items.get("item");
        return item;
    }
    static void Analyzation(JSONObject line) {
        // 예보구분값을 가져옴, 후에 Enum 클래스를 통해 이해하기 쉬운 출력값으로 변경 예정
        String cg = (String)line.get("category");
        // obsrValue를 가져옴
        double value = Double.parseDouble((String)line.get("obsrValue"));
        // 날짜 값을 가져옴
        String date = (String)line.get("baseDate");
        date = date.substring(0,4) + '년' + date.substring(4);
        date = date.substring(0,7) + '월' + date.substring(7);
        date = date + '일';
        // x,y 좌표 값을 가져옴
        int nx = (Integer)line.get("nx");
        int ny = (Integer)line.get("ny");
        // 측정 시간을 가져옴
        String time = (String)line.get("baseTime");
        time = time.substring(0,2) + ':' + time.substring(2);
        time = time + '시';

        System.out.println(date + " " + time + " " + nx + "," + ny + "의 " + category.getCategory(cg).getDesc()
        + "는(은) " + value + "입니다.");
    }
}
