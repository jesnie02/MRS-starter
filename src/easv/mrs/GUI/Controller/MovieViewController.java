package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.MovieDAO_File;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    @FXML
    private TextField txtNewMovieTitle,txtNewMovieYear;

    private MovieDAO_File movieDAO;
    private MovieModel movieModel;

    public MovieViewController()  {

        movieDAO = new MovieDAO_File();

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void handlecreateMovie(ActionEvent actionEvent) {
       String title =txtNewMovieTitle.getText();
       int year = Integer.parseInt(txtNewMovieYear.getText());
        Movie newMovie = new Movie(-1,year,title);

        try {
            movieModel.createNewMovie(newMovie);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }

        /*
        String title = txtNewMovieTitle.getText();
        int year = Integer.parseInt(txtNewMovieYear.getText());
        try {
            Movie newMovie = movieDAO.createMovie(title, year);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}

