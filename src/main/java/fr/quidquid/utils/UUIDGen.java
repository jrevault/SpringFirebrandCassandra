package fr.quidquid.utils;


import com.eaio.uuid.UUID;

public class UUIDGen {

  public static String get() {
    return new UUID().toString();
  }

  public static String get( String salt ) {
    return new UUID( salt ).toString();
  }


}
