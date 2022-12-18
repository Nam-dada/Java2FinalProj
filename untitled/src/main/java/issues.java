import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class issues {
  public static void main(String[] args) throws IOException, ParseException {
    int m = 2;
    List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
    String s = "https://api.github.com/repos/alibaba/fastjson/issues?state=all&per_page=100";
    URL url = new URL(s);
    PrintWriter out = new PrintWriter("issues_fastjson.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    while (m != 50) {
      StringBuilder j = new StringBuilder();
      Scanner in = new Scanner(connection.getInputStream());
      while (in.hasNext()) {
        j.append(in.next());
      }
      getJsList(dataList, j.toString());
      s = "https://api.github.com/repos/alibaba/fastjson/issues?state=all&per_page=100&page="+m;
      url = new URL(s);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      m++;
    }
    int open = 0;
    int close = 0;
    List<String> outList = new ArrayList<>();
    Collections.sort(dataList, (m1, m2) -> Integer.compare(Integer.parseInt
       (m2.get("comments").toString()), Integer.parseInt(m1.get("comments").toString())));
    for (int i = 0; i < dataList.size(); i++) {
      if (dataList.get(i).get("state").toString().equals("open")) {
        open++;
      } else {
        close++;
      }
      outList.add(dataList.get(i).toString());
    }
    out.println("Openissues: "+ open);
    out.println("Closedissues "+ close);
    for (int i = 0; i < outList.size(); i++) {
      out.println(outList.get(i));
    }
    out.close();
  }
  public static String getDurationTime(String creatTime, String closeTime) throws ParseException {
    if (closeTime.equals("null")) {
      return  "No";
    }
    return getTime(creatTime, closeTime);
  }
  public static String getTime(String cT, String clT) throws ParseException {
    String cTime1 = cT.replace('T', ' ');
    String cTime2 = cTime1.replace('Z',' ');
    String clTime = clT.replace('T', ' ');
    String clTime2 = clTime.replace('Z', ' ');
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
    long sd = 1000 * 24 * 60 * 60;
    long sh = 1000 * 60 * 60;
    long sm = 1000 * 60;
    long ss = 1000;
    Date creatTime = format.parse(cTime2);
    Date closeTime = format.parse(clTime2);
    long diff = closeTime.getTime() - creatTime.getTime();
    long days = diff / sd;
    diff -= days * sd;
    long hours = diff / sh;
    diff -= hours * sh;
    long minutes = diff / sm;
    diff -= minutes * sm;
    long seconds = diff / ss;
    return days + " Days "+hours+" Hours "+minutes+ " Minutes "+seconds+" Seconds";
  }
  public static void getJsList(List<LinkedHashMap<String, Object>> list, String js) throws ParseException {
    JSONArray issues = JSONArray.parseArray(js);
    for (int i = 0 ; i < issues.size(); i++) {
      JSONObject j = (JSONObject)issues.get(i);
      String comments = j.get("comments").toString();
      String state = j.get("state").toString();
      String creatTime = j.get("created_at").toString();
      String closeTime;
      if (j.get("closed_at") == null) {
        closeTime = "null";
      } else {
        closeTime = j.get("closed_at").toString();
      }
      String durationTime = getDurationTime(creatTime, closeTime);
      LinkedHashMap<String, Object> map = new LinkedHashMap<>();
      map.put("comments", comments);
      map.put("state", state);
      map.put("durationTime", durationTime);
      list.add(map);
    }
  }
}



