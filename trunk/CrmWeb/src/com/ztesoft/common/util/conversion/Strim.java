package com.ztesoft.common.util.conversion;

/**
* This class's function is  got rid of space or if
* String's value equal to  null and return "".
*/

public final class Strim {
  public static String trim(String str) {
    if (str == null) {
      return "";
    }
    else {
      return str.trim();
    }
  }

  public static String trim(int num) {
    return "" + num;
  }

}
