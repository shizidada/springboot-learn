package org.excel.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-30 18:41:18:41
 * @see org.excel.operator
 */
public class DingDingRobotTests {
  private static final OkHttpClient client = new OkHttpClient();

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {
    String url = "";
    TextEntity textEntity = new TextEntity();
    textEntity.setMsgType("text");
    textEntity.setContent("在呢，汪汪...");
    ArrayList<String> atMobiles = new ArrayList<>();
    atMobiles.add("17600606308");
    atMobiles.add("16601322126");
    textEntity.setAtMobiles(atMobiles);
    textEntity.setAtAll(Boolean.FALSE);
    String jsonObjectString = textEntity.getJSONObjectString();
    System.out.println(jsonObjectString);
    sendToDingDing(textEntity.getJSONObjectString(), url);
  }

  public static String getSign() throws Exception {
    Long timestamp = System.currentTimeMillis();
    String secret = "";
    String stringToSign = timestamp + "\n" + secret;
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
    byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
    return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
  }

  public static void sendToDingDing(String jsonString, String webhook) {
    System.out.println(String.format("jsonString :%s", jsonString));
    try {
      String type = "application/json; charset=utf-8";
      RequestBody body = RequestBody.create(MediaType.parse(type), jsonString);
      Request.Builder builder = new Request.Builder().url(webhook);
      builder.addHeader("Content-Type", type).post(body);

      Request request = builder.build();
      Response response = client.newCall(request).execute();
      String string = response.body().string();
      System.out.println(String.format("send ding message:%s", string));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static class TextEntity {
    private String msgType;

    // 显示内容
    private String content;

    // 是否at所有人
    private Boolean isAtAll;

    // 被@人的手机号(在content里添加@人的手机号)
    private List<String> atMobiles;

    public String getMsgType() {
      return msgType;
    }

    public void setMsgType(String msgType) {
      this.msgType = msgType;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public Boolean getAtAll() {
      return isAtAll;
    }

    public void setAtAll(Boolean atAll) {
      isAtAll = atAll;
    }

    public List<String> getAtMobiles() {
      return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
      this.atMobiles = atMobiles;
    }

    public String getJSONObjectString() {
      Map<String, Object> content = Maps.newHashMap();
      content.put("content", this.getContent());

      // at some body
      Map<String, Object> atMobile = Maps.newHashMap();
      if (this.getAtMobiles().size() > 0) {
        List<String> mobiles = new ArrayList<String>();
        for (int i = 0; i < this.getAtMobiles().size(); i++) {
          mobiles.add(this.getAtMobiles().get(i));
        }
        if (mobiles.size() > 0) {
          atMobile.put("atMobiles", mobiles);
        }
        atMobile.put("isAtAll", this.getAtAll());
      }

      Map<String, Object> allContents = Maps.newHashMap();
      allContents.put("msgtype", this.getMsgType());
      allContents.put("text", content);
      allContents.put("at", atMobile);
      String jsonString = null;
      try {
        jsonString = objectMapper.writeValueAsString(allContents);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

      return jsonString;
    }
  }
}
