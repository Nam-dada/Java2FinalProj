import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.camel.util.json.JsonArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class release {
  public static void main(String[] args) throws IOException, ParseException {
    String s = "https://api.github.com/repos/alibaba/fastjson/releases?per_page=100";
    URL url = new URL(s);
    Scanner in1 = new Scanner(new File("Time.txt"));
    PrintWriter out = new PrintWriter("release.txt");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    Scanner in = new Scanner(connection.getInputStream());
    StringBuilder js = new StringBuilder();
    while(in.hasNext()) {
      js.append(in.next());
    }
    ArrayList<Long> time = new ArrayList<>();
    while (in1.hasNext()) {
      time.add(in1.nextLong());
    }
    Collections.reverse(time);
    String s2 = js.toString();
    List<LinkedHashMap<String, Object>> dateList = new ArrayList<>();
    getReleasesJs(dateList, s2, time);
    out.println("Total Releases: "+dateList.size());
    for (int i = 0; i  < dateList.size(); i++) {
      out.println(dateList.get(i));
    }
    out.close();
  }
  public static void getReleasesJs (List<LinkedHashMap<String, Object>> dateList, String s, ArrayList<Long> time) throws ParseException {
    JSONArray release = JSONArray.parseArray(s);
    for (int i = 0 ; i < release.size(); i++) {
      LinkedHashMap<String, Object> map = new LinkedHashMap<>();
      JSONObject re = release.getJSONObject(i);
      String tag = re.get("tag_name").toString();
      String cr = re.get("created_at").toString();
      String en = re.get("published_at").toString();
      String Time1 = en.replace("Z", " UTC");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date timeEnd = format.parse(Time1);
      String Time2 = format1.format(timeEnd);
      String Timec = cr.replace("Z", " UTC");
      System.out.println(cr);
      System.out.println(en);
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
      SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date timeSt = format2.parse(Timec);
      String TimeSt = format3.format(timeSt);
      String t1 = Time2.replace(":", "");
      String t2 = t1.replace("-", "");
      String t3 = t2.replace(" ","");
      long end = Long.parseLong(t3);
      String t4 = TimeSt.replace(":", "");
      String t5 = t4.replace("-", "");
      String t6 = t5.replace(" ","");
      long begin = Long.parseLong(t6);
      int totalCommit = getCommits(begin, end, time);
      map.put("Tag_name", tag);
      map.put("Published_at", Time2);
      map.put("NewCommits", totalCommit);
      dateList.add(map);
    }
  }
  public static int getCommits (long begin, long end, ArrayList<Long> time) {
    int blue1 = -1;
    System.out.println(begin);
    System.out.println(end);
    int red1 = time.size();
    int mid1;
    while (blue1 + 1 != red1) {
       mid1 = (blue1 + red1) / 2;
      if (time.get(mid1) < begin) {
        blue1 = mid1;
      } else {
        red1 = mid1;
      }
    }
    int blue2 = -1;
    int red2 = time.size();
    int mid2;
    while (blue2 + 1 != red2) {
      mid2 = (blue2 + red2) / 2;
      if (time.get(mid2) <= end) {
        blue2 = mid2;
      } else {
        red2 = mid2;
      }
    }
    return blue2 - red1 + 1;
  }
}
