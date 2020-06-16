package org.excel.operator.utils;

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
 * @see org.excel.operator.utils
 */
public class PageInfoUtil {

  private PageInfoUtil() {
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
    map.put("list", page.getList());
    return map;
  }
}
