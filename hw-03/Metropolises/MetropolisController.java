package Metropolises;

import java.sql.SQLException;
import java.util.List;


/*
    this class implements inputAddedListener and requestBase intefrace, this class connects view and model of this project
    also registers listeners to view, which listens for change of input and also it activates when user needs request to base.
 */
public class MetropolisController implements inputAddedListener, requestBase {
    private BasicView view;
    private MetropolisDao dao;


    //initializes instances and also registers listeners
    public MetropolisController(BasicView view, MetropolisDao dao){
        this.view = view;
        this.dao = dao;
        view.registerInputAddedListener(this);
        view.registerRequestBaseListener(this);
    }


    /*
        When user enters search button view requests to search in base with given rules this method provides list of
        valid metropolis objects and returns to the view to display info.
     */
    @Override
    public List<Metropolis> getNewDisplay(SearchEngine currentEngine){
        return dao.search(currentEngine);
    }


    /*
      When user add's new Metropolis this method takes this new input and gives info to base.
     */
    @Override
    public void fireInputAdded(Metropolis metropolis) throws SQLException {
        int numInserted = dao.addInput(metropolis);
    }
}
