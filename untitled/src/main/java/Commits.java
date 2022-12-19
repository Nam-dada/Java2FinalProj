import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commits {
  public static void main(String[] args) throws IOException, ParseException {
    int m = 2;
    List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
    String s = "https://api.github.com/repos/lin-xin/vue-manage-system/commits?per_page=100";
    URL url = new URL(s);
    PrintWriter out = new PrintWriter("commits_vue-manage-system.txt");
    PrintWriter out2 = new PrintWriter("Time_vue-manage-system.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    int[] d = new int[8];
    while (m != 10) {
      StringBuilder j = new StringBuilder();
      Scanner in = new Scanner(connection.getInputStream());
      while (in.hasNext()) {
        j.append(in.next());
      }
      getCommitList(out2, d, dataList, j.toString());
      s = "https://api.github.com/repos/lin-xin/vue-manage-system/commits?per_page=100&page=" + m;
      url = new URL(s);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      m++;
    }
    out.println("Total");
    out.println("--------------");
    out.println("Morning: " + d[0]);
    out.println("Noon: " + d[1]);
    out.println("Afternoon: " + d[2]);
    out.println("Evening: " + d[3]);
    out.println("Night: " + d[4]);
    out.println("--------------");
    out.println("Weekday: " + d[5]);
    out.println("Weekend: " + d[6]);
    for (int i = 0; i < dataList.size(); i++) {
      out.println(dataList.get(i));
    }
    out.close();
    out2.close();
  }
  
  public static void getCommitList(PrintWriter out, int[] d, List<LinkedHashMap<String, Object>> datalist, String s) throws ParseException {
    JSONArray commits = JSONArray.parseArray(s);
    for (int i = 0; i < commits.size(); i++) {
      JSONObject js  = commits.getJSONObject(i);
      JSONObject commit = js.getJSONObject("commit");
      JSONObject author = commit.getJSONObject("author");
      String name = author.get("name").toString();
      String time = author.get("date").toString();
      String Time1 = time.replace("Z", " UTC");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date timeEnd = format.parse(Time1);
      String Time2 = format1.format(timeEnd);
      Date date = format1.parse(Time2);
      String t1 = Time2.replace(":", "");
      String t2 = t1.replace("-", "");
      String t3 = t2.replace(" ", "");
      out.println(t3);
      System.out.println(Time2);
      String hour = Time2.substring(11, 13);
      int iHour = Integer.parseInt(hour);
      String day;
      String dd;
      if (iHour >= 6 && iHour <= 11) {
        day = "morning";
        d[0]++;
      } else if (iHour > 11 && iHour <= 13) {
        day = "noon";
        d[1]++;
      } else if (iHour > 13 && iHour <= 18) {
        day = "afternoon";
        d[2]++;
      } else if (iHour > 18 && iHour <= 22) {
        day = "evening";
        d[3]++;
      } else {
        day = "night";
        d[4]++;
      }
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int weekday = calendar.get(Calendar.DAY_OF_WEEK);
      if (weekday >= 1 && weekday <= 5) {
        dd = "Weekday";
        d[5]++;
      } else {
        dd = "Weekend";
        d[6]++;
      }
      String t = dd + " " + day;
      LinkedHashMap<String, Object> map = new LinkedHashMap<>();
      map.put("Name", name);
      map.put("Date", Time2);
      map.put("Time", t);
      datalist.add(map);
    }
  }
}
