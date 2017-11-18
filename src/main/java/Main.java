import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String line = null;
        String province = null;
        String district = null;
        FileReader fileReader = new FileReader("province_district3.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        while((line = reader.readLine()) != null){
            province = line.split(",")[1];
            district = line.split(",")[0];
            int distance = getDistance(province, district);
            System.out.println(province+","+district+","+distance);

        }
    }

    private static int getDistance(String province, String district) throws Exception {
        String url = "";
        if (province.equalsIgnoreCase("กรุงเทพมหานคร")){
            url = "สำนักงานเขต"+district + "+" + province;
        }else{
            url = district + "+" + province ;
        }
        String url2 = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Somdej+Pra+Chao+Tak+Sin+Maharat+Public+Park&destinations=" + URLEncoder.encode(url, "UTF-8") + "&key=AIzaSyDBctsi5YEwIEo1DCPFG_iJem0GB5z21u4";
//        System.out.println(url2);
        String json = readUrl(url2);
//        String json = readUrl("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Somdej+Pra+Chao+Tak+Sin+Maharat+Public+Park&destinations=%E0%B8%AA%E0%B8%B3%E0%B8%99%E0%B8%B1%E0%B8%81%E0%B8%87%E0%B8%B2%E0%B8%99%E0%B9%80%E0%B8%82%E0%B8%95%E0%B8%94%E0%B8%B4%E0%B8%99%E0%B9%81%E0%B8%94%E0%B8%87&key=AIzaSyAIXRufofxdNmH90YYvbmNZr2ByqNOyIUs");
        Type type = new TypeToken<Map<String, String>>(){}.getType();

//        System.out.println("json = " + json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        mapInfo map = gson.fromJson(json, mapInfo.class);
//        System.out.println("Main(main) : map = " + map);
//        System.out.println("distance : " + map.rows.get(0).elements.get(0).distance.value);
        try{
            int m = map.rows.get(0).elements.get(0).distance.value;
            int km = m/1000;
            return km;
        }catch (Exception e){
            System.err.println(province+"\t"+district);
            System.err.println("url : " + url2);
        }
        return 0;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
