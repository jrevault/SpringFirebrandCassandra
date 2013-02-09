package fr.quidquid.domain.dao;

import fr.quidquid.domain.model.User;
import fr.quidquid.domain.model.BaseModel;
import fr.quidquid.utils.UUIDGen;
import org.firebrandocm.dao.PersistenceFactory;
import org.firebrandocm.dao.Query;
import org.firebrandocm.dao.cql.statement.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.firebrandocm.dao.cql.QueryBuilder.*;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {

  protected final Logger log = LoggerFactory.getLogger(GenericDaoImpl.class);

  @Autowired
  public PersistenceFactory factory;
  private Class<T> type;

  public GenericDaoImpl() {
    Type t = getClass().getGenericSuperclass();
    ParameterizedType pt = ( ParameterizedType ) t;
    type = ( Class ) pt.getActualTypeArguments()[ 0 ];
  }

  @Override
  public T save( T model ) {
    if ( ( (BaseModel) model ).getId() == null ) {
      ( ( BaseModel ) model ).setId( UUIDGen.get() );
    }
    factory.persist( model );
    return ( T ) model;
  }

  @Override
  public boolean del( String id ) {
    T r = get( id );
    factory.remove( r );
    return true;
  }

  @Override
  public T get( String id ) {
    return factory.get( type, id );
  }

  @Override
  public boolean exists( String id ) {
    T r = get( id );
    return r != null && ( ( BaseModel ) r ).getId() != null;
  }

  @Override
  public List<T> list() {
    Select s = select( allColumns(), from( User.class ) );
    Query q = Query.get( s );
    return factory.getResultList( type, q );
  }

}
