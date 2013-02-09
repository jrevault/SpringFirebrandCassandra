package fr.quidquid.domain.model;


import org.firebrandocm.dao.annotations.Column;
import org.firebrandocm.dao.annotations.ColumnFamily;
import org.joda.time.DateTime;

@ColumnFamily
public class User extends BaseModel {

  @Column( indexed = true )
  private String email;
  private String name;
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Class getClazz() {
    return User.class;
  }

}
