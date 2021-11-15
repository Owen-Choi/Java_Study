package NET_HW;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class Using_Open_Data_2 {
    static final String Service_key = "private";
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + Service_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("year","UTF-8") + "=" + URLEncoder.encode("2021", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("itemCode","UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8"));
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
    }
    // 끌어온 JSON 정보를 파싱하여 정제하기 위한 함수
    static void Parsing(String sb) {
        //가장 상위에 있는 JSONObject를 객체화 시킨다.
        JSONObject jsonObject = new JSONObject(sb);
        //그 후에 우리가 원하는 정보가 담긴 객체를 뽑아낸다.
        //미세먼지/초미세먼지 현황 데이터의 경우 items JSONArray에 우리가 원하는 정보가 저장되어 있다.
        // getRoot 함수에서 items까지 이동 후 이를 반환하면, 바로 이용할 수 있다.
        JSONArray item = getRoot(jsonObject);
        System.out.println("////////////////////////////////");
        for(int i=0; i<item.length(); i++) {
            //배열 각각의 데이터마다 분석을 실행한다.
            Analyzation((JSONObject)item.get(i));
        }
    }
    static JSONArray getRoot(JSONObject jsonObject) {
        JSONObject items = (JSONObject)jsonObject.get("response");
        items = (JSONObject)items.get("body");
        JSONArray item = (JSONArray)items.get("items");
        //items = (JSONObject)items.get("items");
        return item;
    }
    static void Analyzation(JSONObject line) {
        System.out.println('\n');
        // 분석
        ////////////////////////////////////////////////////////////////
        //String StartDate = (String)line.get("dataDate");        //발령일
        String EndDate = (String)line.get("clearDate");         //해제일
        String RegionName = (String)line.get("districtName");   //지역명
        String issueGbn = (String)line.get("issueGbn");         //경보단계
        String issueTime = (String)line.get("issueTime");       //발령시간
        String clearTime = (String)line.get("clearTime");       //해제시간
        String itemCode = (String)line.get("itemCode");         //항목명(미세먼지 종류 : 초미세 or 미세)
        String sn = (String)line.get("sn");                     //관리번호
        String issueDate = (String)line.get("issueDate");       //발령일
        String issueVal = (String)line.get("issueVal");         //발령농도
        String clearVal = (String)line.get("clearVal");         //해제농도
        String moveName = (String)line.get("moveName");         //권역명
        ///////////////////////////////////////////////////////////////
        //지역명 -> 권역명 -> 발령일 -> 발령시간 -> (항목명 구분 +)경보단계(+ 관리번호) -> 발령농도
        String type;
        type = itemCode.equals("PM25") ? "미세먼지" : "초미세먼지";
        System.out.println("/////////////////////발령/////////////////////");
        System.out.println(RegionName + " " + moveName + " " + issueDate + " " + issueTime + " " + type + issueGbn
        + "("+sn+")"+ " " + issueVal + "㎍/m3");
        //해제일 -> 해제시간 -> 해제농도
        System.out.println("/////////////////////해제/////////////////////");
        System.out.println(EndDate + " " + clearTime + " " + clearVal + "㎍/m3");
    }
}
