package fr.quidquid.api;

import fr.quidquid.utils.ClassUtil;
import org.firebrandocm.dao.PersistenceFactory;
import org.firebrandocm.dao.Query;
import org.firebrandocm.dao.annotations.ColumnFamily;
import org.firebrandocm.dao.cql.clauses.ColumnDataType;
import org.firebrandocm.dao.cql.clauses.StorageParameter;
import org.firebrandocm.dao.cql.statement.CreateColumnFamily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import java.util.List;

import static org.firebrandocm.dao.cql.QueryBuilder.*;


public class StartupServlet extends HttpRequestHandlerServlet {

  private final Logger log = LoggerFactory.getLogger(StartupServlet.class);

  public void init() {
    log.warn("QUIDQUID STARTUP ........................... OK");
    try {
      // DAATAZ logo
      startLogo();
      // DB verifications
      startDB();
    } catch (Exception e) {
      log.error("Initialisation .......................... KO ");
      e.printStackTrace();
    }
  }

  public void startDB() throws Exception {
    ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
    PersistenceFactory factory = (PersistenceFactory) ctx.getBean("persistenceFactory");
    //PersistenceFactory factory = (PersistenceFactory) getServletContext().getAttribute("persistenceFactory");

    // get the entities list
    List<Class<?>> models = ClassUtil.get("fr.quidquid.domain.model", ColumnFamily.class);

    // Checks all column family and creates them if they doesn't exists
    for (Class<?> clazz : models) {
      CreateColumnFamily ccf =
          createColumnFamily(
              columnFamily(clazz),
              columnDefinitions(
                  primaryKey("id", ColumnDataType.VARCHAR)
              ),
              storageOptions(
                  storageOption(StorageParameter.COMMENT, clazz.getSimpleName()),
                  storageOption(StorageParameter.READ_REPAIR_CHANCE, "1")
              )
          );
      Query q = Query.get(ccf);
      try {
        factory.executeQuery(clazz, q);
      } catch (Exception ex) {
        log.warn("Column family {} could not be created : {}", clazz.getName(), ex.getMessage());
      }
    }
  }

  public void startLogo() {
    String intro = "\n\n" +
        "  ____        _     _  ____        _     _ \n" +
        " / __ \\      (_)   | |/ __ \\      (_)   | |\n" +
        "| |  | |_   _ _  __| | |  | |_   _ _  __| |\n" +
        "| |  | | | | | |/ _` | |  | | | | | |/ _` |\n" +
        "| |__| | |_| | | (_| | |__| | |_| | | (_| |\n" +
        " \\___\\_\\\\__,_|_|\\__,_|\\___\\_\\\\__,_|_|\\__,_|\n" +
        "                                           \n" +
        "\n";
    log.warn(intro);
  }

}
