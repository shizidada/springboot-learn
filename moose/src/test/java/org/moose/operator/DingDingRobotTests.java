package org.moose.operator;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.moose.operator.model.vo.WebErrorReportInfoVO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-30 18:41:18:41
 * @see org.moose.operator
 */
@Slf4j
public class DingDingRobotTests {
  private static final OkHttpClient client = new OkHttpClient();

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws Exception {
    sendErrorMessageByDefaultClient();
  }

  public static void sendErrorMessageByDefaultClient() throws Exception {
    String finalUrl = getDingDingUrl();
    DingTalkClient client = new DefaultDingTalkClient(finalUrl);

    OapiRobotSendRequest request = new OapiRobotSendRequest();
    request.setMsgtype("markdown");
    OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();

    WebErrorReportInfoVO errorInfo = buildErrorInfo();

    markdown.setTitle(errorInfo.getErrorMessage());

    StringBuilder text = new StringBuilder();

    text.append("【错误提醒】").append("\n\n\n");

    if (null != errorInfo.getAppName()) {
      text.append("应用名称: ").append(errorInfo.getAppName())
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getErrorMessage()) {
      text.append("### ").append(errorInfo.getErrorMessage())
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getErrorStack()) {
      text.append("错误栈信息: ");
      text.append(errorInfo.getErrorStack())
          .append("      ")
          .append(" \r\n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getErrorFrom()) {
      text.append("错误来源: ").append(errorInfo.getErrorFrom())
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getHref()) {
      text.append("错误界面: ").append(errorInfo.getHref())
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getComponentStack()) {
      text.append("渲染组件栈: ");
      text.append(errorInfo.getComponentStack())
          .append("      ")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getSource()) {
      text.append("错误资源: ").append("**").append(errorInfo.getSource()).append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getRow()) {
      text.append("错误行: ").append("**").append(errorInfo.getRow()).append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getCol()) {
      text.append("错误列: ").append("**").append(errorInfo.getCol()).append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getBrowerName()) {
      text.append("浏览器名称: ")
          .append("**")
          .append(errorInfo.getBrowerName())
          .append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getBrowerPlatform()) {
      text.append("浏览器平台: ")
          .append("**")
          .append(errorInfo.getBrowerPlatform())
          .append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getBrowerCodeName()) {
      text.append("浏览器代码名: ")
          .append("**")
          .append(errorInfo.getBrowerCodeName())
          .append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getBrowerUserAgent()) {
      text.append("浏览器 agent: ")
          .append("**")
          .append(errorInfo.getBrowerUserAgent())
          .append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    if (null != errorInfo.getBrowerLanguage()) {
      text.append("浏览器当前语言: ")
          .append("**")
          .append(errorInfo.getBrowerLanguage())
          .append("**")
          .append(" \n")
          .append(" \n")
          .append(" \n");
    }

    markdown.setText(text.toString());
    log.info(text.toString());
    //request.setMarkdown(markdown);
    //OapiRobotSendResponse response = client.execute(request);
    //log.info(objectMapper.writeValueAsString(response));
  }

  public static void sendErrorMessage() throws Exception {
    String finalUrl = getDingDingUrl();

    TextEntity textEntity = new TextEntity();
    textEntity.setMsgType("markdown");
    textEntity.setAtAll(Boolean.FALSE);

    WebErrorReportInfoVO webErrorReportInfoVO = buildErrorInfo();
    String reportInfo = objectMapper.writeValueAsString(webErrorReportInfoVO);
    log.info(reportInfo);

    textEntity.setContent(reportInfo);
    sendToDingDing(textEntity.getJSONObjectString(), finalUrl);
  }

  public static String getDingDingUrl() throws Exception {
    //String secret = "";
    //Long timestamp = System.currentTimeMillis();
    //String sign = getSign(secret, timestamp);
    //String finalUrl = String.format(url, timestamp, sign);
    //log.info(finalUrl);
    return "";
  }

  public static void sendToDingDing(String jsonString, String webhook) {
    log.info(jsonString);
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

  public static void sendTest() {
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

  public static String getSign(String secret, Long timestamp) throws Exception {
    String stringToSign = timestamp + "\n" + secret;
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
    return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
  }

  public static WebErrorReportInfoVO buildErrorInfo() throws JsonProcessingException {
    WebErrorReportInfoVO webErrorReportInfoVO = new WebErrorReportInfoVO();
    webErrorReportInfoVO.setAppName("user");
    webErrorReportInfoVO.setBrowerCodeName("Mozilla");
    webErrorReportInfoVO.setBrowerLanguage("zh-CN");
    webErrorReportInfoVO.setBrowerName("Netscape");
    webErrorReportInfoVO.setBrowerVersion(
        "5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36");
    webErrorReportInfoVO.setBrowerUserAgent(
        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36");
    webErrorReportInfoVO.setBrowerPlatform("Win32");

    webErrorReportInfoVO.setErrorFrom("测试数据");
    webErrorReportInfoVO.setErrorMessage("a is not defined");
    webErrorReportInfoVO.setErrorStack(
        "ReferenceError: a is not defined↵    at Home.render (webpack-internal:///38Hv:114:1674)↵    at finishClassComponent (webpack-internal:///Ybsr:17160:31)↵    at updateClassComponent (webpack-internal:///Ybsr:17110:24)↵    at beginWork (webpack-internal:///Ybsr:18620:16)↵    at HTMLUnknownElement.callCallback (webpack-internal:///Ybsr:188:14)↵    at Object.invokeGuardedCallbackDev (webpack-internal:///Ybsr:237:16)↵    at invokeGuardedCallback (webpack-internal:///Ybsr:292:31)↵    at beginWork$1 (webpack-internal:///Ybsr:23203:7)↵    at performUnitOfWork (webpack-internal:///Ybsr:22154:12)↵    at workLoopSync (webpack-internal:///Ybsr:22130:22)");
    webErrorReportInfoVO.setComponentStack("Component Stack");
    webErrorReportInfoVO.setHref("http://local-user.wanshifu.com:8080/");
    webErrorReportInfoVO.setRow(236L);
    webErrorReportInfoVO.setCol(1L);
    return webErrorReportInfoVO;
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
      if (null != this.getAtMobiles() && this.getAtMobiles().size() > 0) {
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
