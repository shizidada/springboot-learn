package org.excel.operator;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.excel.operator.poi.XSSFOperator;

/**
 * @author taohua
 */
public class ExcelOperatorApplication {

  public static void main(String[] args) throws IOException {
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();

    XSSFOperator xssfOperator = new XSSFOperator(sqlSession);
    String filePath = XSSFOperator.class.getResource("/").getPath() + "anqin2.xlsx";
    xssfOperator.importExcelFile(filePath);

    //xssfOperator.exportExcelFile();
  }
}
