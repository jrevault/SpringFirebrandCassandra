package fr.quidquid.domain.dao;

import java.util.List;

public interface GenericDao<T> {

  public T save( T model );

  public boolean del( String id );

  public T get( String id );

  public boolean exists( String id );

  public List<T> list();

}
