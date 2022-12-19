import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Contributors {
  public static void main(String[] args) throws IOException {
    List<LinkedHashMap<String, Object>> a = new ArrayList<>();
    int m = 2;
    String s = "https://api.github.com/repos/lin-xin/vue-manage-system/contributors?per_page=100&anon=1";
    URL url = new URL(s);
    PrintWriter out = new PrintWriter("Contributors_vue-manage-system.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    while (m != 6) {
      StringBuilder j = new StringBuilder();
      Scanner in = new Scanner(connection.getInputStream());
      while (in.hasNext()) {
        j.append(in.next());
      }
      getJsonList(a, j.toString());
      s = "https://api.github.com/repos/lin-xin/vue-manage-system/contributors?per_page=100&page=" + m + "&anon=1";
      url = new URL(s);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      m++;
    }
    int total = 0;
    Collections.sort(a, (m1, m2) -> Integer.compare(Integer.parseInt(m2.get("contributions").toString()), Integer.parseInt(m1.get("contributions").toString())));
    for (int i = 0; i < a.size(); i++) {
      out.println(a.get(i));
      total += Integer.parseInt(a.get(i).get("contributions").toString());
    }
    out.println(total);
    out.close();
  
  }
  
  public static void getJsonList(List<LinkedHashMap<String, Object>> dataList, String s) {
    JSONArray contributors =  JSONArray.parseArray(s);
    for (int i = 0; i < contributors.size(); i++) {
      JSONObject j = (JSONObject) contributors.get(i);
      if (j.get("type").toString().equals("Anonymous")) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("login", "Anonymous");
        map.put("contributions", j.get("contributions").toString());
        dataList.add(map);
      } else {
        String login = j.get("login").toString();
        String contributions = j.get("contributions").toString();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("login", login);
        map.put("contributions", contributions);
        dataList.add(map);
      }
    }
  }
}
