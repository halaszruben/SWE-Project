package Gravel.DataBase;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Represnet some minor SQLQueries.
 */
@RegisterBeanMapper(Result.class)
public interface ResultDAO {

    @SqlUpdate("""
            create TABLE if not exists Score(
            firstPlayer varchar not null,
            secondPlayer varchar not null,
            winnerPlayer varchar not null)
            """)
    void create();

    @SqlUpdate("insert into Score values(:firstPlayer,:secondPlayer,:winnerPlayer)")
    void addResult(@BindBean Result result);

    @SqlQuery("SELECT * FROM Score")
    List<Result> listResult();
}
