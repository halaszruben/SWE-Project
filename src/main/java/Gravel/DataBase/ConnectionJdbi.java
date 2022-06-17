package Gravel.DataBase;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.io.File;
import java.util.List;

/**
 * {@link Class} does the insertion and the listing of the {@link Jdbi} DataBase.
 */
public class ConnectionJdbi {

    /**
     * Inserts all the given values into the current DataBase.
     *
     * @param result represents the values of the DataBase.
     */
    public static void insert(Result result) {
        Logger.info("Inserting into the Database.");
        Jdbi jdbi = Jdbi.create("jdbc:h2:file:" + System.getProperty("user.home") + File.separator + "ScoreMenu");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            ResultDAO dao = handle.attach(ResultDAO.class);
            dao.create();
            dao.addResult(result);
        }
    }

    /**
     * List all the values that are in the current DataBase.
     *
     * @return these values.
     */
    public static List<Result> lister() {
        Logger.info("Listing out from the Database.");
        Jdbi jdbi = Jdbi.create("jdbc:h2:file:" + System.getProperty("user.home") + File.separator + "ScoreMenu");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            ResultDAO dao = handle.attach(ResultDAO.class);
            dao.create();
            return dao.listResult();
        }
    }


}
