package Metropolises;

import java.util.List;

/**
 * implementator of this interface must override one method, this interface takes care of sending requests to base and returns List witch is returned from base,
 */
public interface requestBase {
    public List <Metropolis> getNewDisplay(SearchEngine currentEngine);
}
