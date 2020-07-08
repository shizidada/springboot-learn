package org.moose.configuration.handle;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/12 14:
 * @see org.moose.configuration.handle
 */
@Controller
@RequestMapping("/error")
@Slf4j
public class GlobalExceptionController extends AbstractErrorController {
  private final ErrorProperties errorProperties;

  @Autowired
  public GlobalExceptionController(ErrorAttributes errorAttributes,
      ServerProperties serverProperties) {
    super(errorAttributes);
    this.errorProperties = serverProperties.getError();
  }

  @Override
  public String getErrorPath() {
    return errorProperties.getPath();
  }

  @RequestMapping(produces = "text/html")
  public ModelAndView errorHtml(HttpServletRequest request,
      HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("error");
    Map<String, Object> errorMap =
        getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
    if (errorMap != null) {
      /*timestamp status error message path*/
      modelAndView.addObject("msg", errorMap.get("error"));
      modelAndView.addObject("statusCode", errorMap.get("status"));
      logHandler(errorMap);
    }
    return modelAndView;
  }

  @RequestMapping
  @ResponseBody
  public ResponseResult<Object> error(HttpServletRequest request) {
    Map<String, Object> errorMap =
        getErrorAttributes(request, isIncludeStackTrace(request, MediaType.APPLICATION_JSON));
    logHandler(errorMap);
    return new ResponseResult<>("测试成功");
  }

  private void logHandler(Map<String, Object> errorMap) {
    log.error("url:{},status{},time:{},errorMsg:{}", errorMap.get("path"), errorMap.get("status"),
        errorMap.get("timestamp"), errorMap.get("message"));
  }

  protected boolean isIncludeStackTrace(HttpServletRequest request,
      MediaType produces) {
    ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
    if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
      return true;
    }
    if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
      return getTraceParameter(request);
    }
    return false;
  }

  private ErrorProperties getErrorProperties() {
    return this.errorProperties;
  }
}
