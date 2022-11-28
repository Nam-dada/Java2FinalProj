import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Contributors {
  public static void main(String[] args) throws IOException {
    String s = "https://api.github.com/repos/alibaba/fastjson/contributors?per_page=100";
    URL url = new URL(s);
    PrintWriter out = new PrintWriter("Contributors.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    System.out.println(connection.getResponseCode());
    if (connection.getResponseCode() == 200) {
    Scanner in = new Scanner(connection.getInputStream());
    StringBuilder j = new StringBuilder();
    while (in.hasNext()) {
      j.append(in.next());
    }
    List<LinkedHashMap<String, Object>> a = getJsonList(j);
      Collections.sort(a, (m1, m2) -> Integer.compare(Integer.parseInt(m2.get("contributions").toString()), Integer.parseInt(m1.get("contributions").toString())));
    for (int i = 0; i < a.size(); i++) {
      out.println(a.get(i));
    }
      out.close();
  }
  }
  public static List<LinkedHashMap<String, Object>> getJsonList(StringBuilder s1) {
    String s = s1.toString();
    List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
    JSONArray contributors =  JSONArray.parseArray(s);
    for (int i = 0; i < contributors.size(); i++) {
      JSONObject j = (JSONObject) contributors.get(i);
      String login = j.get("login").toString();
      String contributions = j.get("contributions").toString();
      LinkedHashMap<String, Object> map = new LinkedHashMap<>();
      map.put("login", login);
      map.put("contributions", contributions);
      dataList.add(map);
    }
    return dataList;
  }
}
