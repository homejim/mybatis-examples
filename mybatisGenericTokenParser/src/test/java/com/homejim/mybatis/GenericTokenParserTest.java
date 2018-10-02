package com.homejim.mybatis;


import com.homejim.myabtis.GenericTokenParser;
import com.homejim.myabtis.TokenHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GenericTokenParserTest {

  public static class VariableTokenHandler implements TokenHandler {
    private Map<String, String> variables;

    public VariableTokenHandler(Map<String, String> variables) {
      this.variables = variables;
    }

    @Override
    public String handleToken(String content) {
      return variables.get(content);
    }
  }

  @Test
  public void simpleTest() {
    GenericTokenParser parser = new GenericTokenParser("${", "}", new VariableTokenHandler(new HashMap<String, String>() {
      {
        put("driver", "com.mysql.jdbc.Driver");
        put("url", "jdbc:mysql://localhost:3306/mybatis");
        put("username", "root");
        put("password", "aaabbb");

      }
    }));

    // 测试单个解析
    assertEquals("com.mysql.jdbc.Driver", parser.parse("${driver}"));
    // 多个一起测试
    assertEquals("驱动=com.mysql.jdbc.Driver，地址=jdbc:mysql://localhost:3306/mybatis，用户名=root",
            parser.parse("驱动=${driver}，地址=${url}，用户名=${username}"));
  }
  @Test
  public void shouldDemonstrateGenericTokenReplacement() {
    GenericTokenParser parser = new GenericTokenParser("${", "}", new VariableTokenHandler(new HashMap<String, String>() {
      {
        put("first_name", "James");
        put("initial", "T");
        put("last_name", "Kirk");
        put("var{with}brace", "Hiya");
        put("", "");
      }
    }));

    assertEquals("James T Kirk reporting.", parser.parse("${first_name} ${initial} ${last_name} reporting."));
    assertEquals("Hello captain James T Kirk", parser.parse("Hello captain ${first_name} ${initial} ${last_name}"));
    assertEquals("James T Kirk", parser.parse("${first_name} ${initial} ${last_name}"));
    assertEquals("JamesTKirk", parser.parse("${first_name}${initial}${last_name}"));
    assertEquals("{}JamesTKirk", parser.parse("{}${first_name}${initial}${last_name}"));
    assertEquals("}JamesTKirk", parser.parse("}${first_name}${initial}${last_name}"));

    assertEquals("}James{{T}}Kirk", parser.parse("}${first_name}{{${initial}}}${last_name}"));
    assertEquals("}James}T{Kirk", parser.parse("}${first_name}}${initial}{${last_name}"));
    assertEquals("}James}T{Kirk", parser.parse("}${first_name}}${initial}{${last_name}"));
    assertEquals("}James}T{Kirk{{}}", parser.parse("}${first_name}}${initial}{${last_name}{{}}"));
    assertEquals("}James}T{Kirk{{}}", parser.parse("}${first_name}}${initial}{${last_name}{{}}${}"));

    assertEquals("{$$something}JamesTKirk", parser.parse("{$$something}${first_name}${initial}${last_name}"));
    assertEquals("${", parser.parse("${"));
    assertEquals("${\\}", parser.parse("${\\}"));
    assertEquals("Hiya", parser.parse("${var{with\\}brace}"));
    assertEquals("", parser.parse("${}"));
    assertEquals("}", parser.parse("}"));
    assertEquals("Hello ${ this is a test.", parser.parse("Hello ${ this is a test."));
    assertEquals("Hello } this is a test.", parser.parse("Hello } this is a test."));
    assertEquals("Hello } ${ this is a test.", parser.parse("Hello } ${ this is a test."));
  }
  @Test
  public void shallNotInterpolateSkippedVaiables() {
    GenericTokenParser parser = new GenericTokenParser("${", "}", new VariableTokenHandler(new HashMap<String, String>()));

    assertEquals("${skipped} variable", parser.parse("\\${skipped} variable"));
    assertEquals("This is a ${skipped} variable", parser.parse("This is a \\${skipped} variable"));
    assertEquals("null ${skipped} variable", parser.parse("${skipped} \\${skipped} variable"));
    assertEquals("The null is ${skipped} variable", parser.parse("The ${skipped} is \\${skipped} variable"));
  }

  @Ignore("Because it randomly fails on Travis CI. It could be useful during development.")
  @Test(timeout = 1000)
  public void shouldParseFastOnJdk7u6() {
    // issue #760
    GenericTokenParser parser = new GenericTokenParser("${", "}", new VariableTokenHandler(new HashMap<String, String>() {
      {
        put("first_name", "James");
        put("initial", "T");
        put("last_name", "Kirk");
        put("", "");
      }
    }));

    StringBuilder input = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      input.append("${first_name} ${initial} ${last_name} reporting. ");
    }
    StringBuilder expected = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      expected.append("James T Kirk reporting. ");
    }
    assertEquals(expected.toString(), parser.parse(input.toString()));
  }

}
