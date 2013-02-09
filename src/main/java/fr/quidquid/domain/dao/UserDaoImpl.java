package fr.quidquid.domain.dao;

import fr.quidquid.domain.model.User;
import org.springframework.stereotype.Service;

@Service( "userDao" )
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

}
