package Metropolises;

import java.sql.SQLException;

/**
 * Interface takes care of input, when new input is added this function must be called, to tell base that new input must be added.
 */
public interface inputAddedListener {
    void fireInputAdded(Metropolis metropolis) throws SQLException;
}
