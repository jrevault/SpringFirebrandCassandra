package fr.quidquid.api.pojo;

import org.apache.http.HttpStatus;

public class Msg {
  private int code = HttpStatus.SC_OK;
  private Object data;

  public Msg() {
  }

  public Msg( Object data ) {
    this.data = data;
  }

  public Msg( Object data, int code ) {
    this.code = code;
    this.data = data;
  }


  public int getCode() {
    return code;
  }

  public void setCode( int code ) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData( Object data ) {
    this.data = data;
  }
}
