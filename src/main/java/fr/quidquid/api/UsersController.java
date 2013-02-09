package fr.quidquid.api;

import fr.quidquid.api.util.ControllerManager;
import fr.quidquid.domain.dao.UserDao;
import fr.quidquid.domain.model.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping ( "/users" )
public class UsersController {

  protected final Logger log = LoggerFactory.getLogger(UsersController.class);

  @Autowired
  UserDao userDao;

  @RequestMapping ( "/create" )
  public void create( HttpServletRequest request, HttpServletResponse response,
                      @RequestParam ( value = "email", required = true ) final String email,
                      @RequestParam ( value = "name", required = true ) final String name,
                      @RequestParam ( value = "pwd", required = true ) final String password ) {
    User model = new User();
    model.setEmail(email);
    model.setName(name);
    model.setPassword(password);

    ControllerManager.rsp( request, userDao.save(model) );
  }

  @RequestMapping ( "/read" )
  public void read( HttpServletRequest request, HttpServletResponse response,
                    @RequestParam ( value = "id", required = true ) final String id ) {
    ControllerManager.rsp( request, userDao.get(id) );
  }

  @RequestMapping ( "/update" )
  public void update( HttpServletRequest request, HttpServletResponse response,
                      @RequestParam ( value = "id", required = true ) final String id,
                      @RequestParam ( value = "email", required = true ) final String email ) {
    User data = new User();
    data.setId( id );
    data.setEmail( email );

    ControllerManager.rsp( request, userDao.save(data) );
  }

  @RequestMapping ( "/delete" )
  public void delete( HttpServletRequest request, HttpServletResponse response,
                      @RequestParam ( value = "id", required = true ) final String id ) {
    ControllerManager.rsp( request, userDao.del(id) );
  }

  @RequestMapping ( "/list" )
  public void list( HttpServletRequest request, HttpServletResponse response ) {
    ControllerManager.rsp( request, userDao.list() );
  }

  @RequestMapping ( "/exists" )
  public void exists( HttpServletRequest request, HttpServletResponse response,
                      @RequestParam ( value = "id", required = true ) final String id ) {
    ControllerManager.rsp( request, userDao.exists(id) );
  }

  @RequestMapping ( "/ok" )
  public void ok( HttpServletRequest request, HttpServletResponse response ) {
    ControllerManager.rsp( request, "OK" );
  }

  @RequestMapping ( "/hello" )
  public void hello( HttpServletRequest request, HttpServletResponse response,
                     @RequestParam ( value = "name", required = true ) final String name ) {
    ControllerManager.rsp( request, "Hello, " + name );
  }


}
