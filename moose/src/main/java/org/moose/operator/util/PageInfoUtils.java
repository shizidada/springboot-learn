package org.moose.operator.util;

import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/29 22:15
 * @see org.moose.operator.util
 */
public class PageInfoUtils {

  private PageInfoUtils() {
  }

  /**
   * 获取分页基本数据信息
   */
  public static Map<String, Object> getBasePageInfo(PageInfo page) {
    Map<String, Object> map = new HashMap<>(16);
    map.put("pageNum", page.getPageNum());
    map.put("pageSize", page.getPageSize());
    map.put("total", page.getTotal());
    map.put("pages", page.getPages());
    return map;
  }
}
