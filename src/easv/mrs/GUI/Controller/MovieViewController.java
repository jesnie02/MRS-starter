package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.MovieDAO_File;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {
    @FXML
    private TableView<Movie> tblMovies;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, Integer> colYear;
    @FXML

    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    @FXML
    private TextField txtNewMovieTitle,txtNewMovieYear;

    private MovieDAO_File movieDAO;
    private MovieModel movieModel;



    public MovieViewController() throws IOException {

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

        tblMovies.setItems(movieModel.getObservableMovies());

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        tblMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedMovie) -> {
            if (selectedMovie != null) {
                txtNewMovieTitle.setText(selectedMovie.getTitle());
                txtNewMovieYear.setText(String.valueOf(selectedMovie.getYear()));
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedMovie) -> {
            if (selectedMovie != null) {
                txtNewMovieTitle.setText(selectedMovie.getTitle());
                txtNewMovieYear.setText(String.valueOf(selectedMovie.getYear()));
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


    public void handleUpdateMovie(ActionEvent actionEvent) {
        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null) {
            String updatedTitle = txtNewMovieTitle.getText();
            int updatedYear = Integer.parseInt(txtNewMovieYear.getText());

            try {
                selectedMovie.setTitle(updatedTitle);
                selectedMovie.setYear(updatedYear);

                movieModel.updateMovie(selectedMovie);


            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {
        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleClearTextFields(ActionEvent event) {
        txtNewMovieTitle.clear();
        txtNewMovieYear.clear();
        tblMovies.getSelectionModel().clearSelection();
    }


}

