import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Contributors {
  public static void main(String[] args) throws IOException {
    StringBuilder j = new StringBuilder();
    int m = 2;
    String s = "https://api.github.com/repos/alibaba/fastjson/contributors?per_page=100";
    URL url = new URL(s);
    PrintWriter out = new PrintWriter("Contributors.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    while (m != 4) {
    Scanner in = new Scanner(connection.getInputStream());
    while (in.hasNext()) {
      j.append(in.next());
    }
      s = "https://api.github.com/repos/alibaba/fastjson/contributors?per_page=100&page="+m;
      url = new URL(s);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      m++;
    }
    String temp = j.toString();
    String temp2 = temp.replace("[", "");
    String temp3 = temp2.replace("]", "");
    String js = "["+temp3+"]";
     List<LinkedHashMap<String, Object>> a = getJsonList(js);
      Collections.sort(a, (m1, m2) -> Integer.compare(Integer.parseInt(m2.get("contributions").toString()), Integer.parseInt(m1.get("contributions").toString())));
    for (int i = 0; i < a.size(); i++) {
      out.println(a.get(i));
    }
    
      out.close();
  
  }
  public static List<LinkedHashMap<String, Object>> getJsonList(String s) {
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
