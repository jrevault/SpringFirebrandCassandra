package fr.quidquid.domain.model;


import org.firebrandocm.dao.annotations.Key;

public abstract class BaseModel {

  @Key
  protected String id;

  public abstract Class getClazz();

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }
}
