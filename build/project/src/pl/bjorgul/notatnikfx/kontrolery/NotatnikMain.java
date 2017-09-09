package pl.bjorgul.notatnikfx.kontrolery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pl.bjorgul.notatnikfx.aplikacja.Main;

public class NotatnikMain implements Initializable {

	public int rozmiarCzcionki = 12;
	public String nazwaCzcionki = "Verdana";
	public String krojCzcionki = "NORMAL";

	@FXML
	private MenuItem menuNowy;

	@FXML
	private MenuItem menuOtworz;

	@FXML
	private MenuItem menuZapisz;

	@FXML
	private MenuItem menuWyjdz;

	@FXML
	private MenuItem menuWytnij;

	@FXML
	private MenuItem menuKopiuj;

	@FXML
	private MenuItem menuWklej;

	@FXML
	private MenuItem menuUsun;

    @FXML
    private MenuItem menuRozmiar;

    @FXML
    private MenuItem menuCzcionka;

	@FXML
	private CheckMenuItem menuZawijanie;

	@FXML
	private MenuItem menuProgram;

	@FXML
	private TextArea obszarNotatnika;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		konfiguracjaPlikMenu();
		konfiguracjaEdycjaMenu();
		konfiguracjaFormatMenu();
		konfiguracjaWidokMenu();
		konfiguracjaPomocMenu();
		obszarNotatnika.setFont(Font.font(nazwaCzcionki, FontWeight.NORMAL, rozmiarCzcionki));
	}

	private void konfiguracjaPlikMenu() {

		menuNowy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				obszarNotatnika.clear();
				Main.updateTitle("Bez tytu³u");

			}
		});

		menuOtworz.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				fc.getExtensionFilters().add(new ExtensionFilter("Text documents", "*.txt"));
				fc.getExtensionFilters().add(new ExtensionFilter("All files", "*.*"));
				File plik = fc.showOpenDialog(new Stage());
				try {
					Main.updateTitle(plik.getName());
					Scanner skaner = new Scanner(plik);
					while (skaner.hasNext())
						obszarNotatnika.appendText(skaner.nextLine() + "\n");
					skaner.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		menuZapisz.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				fc.getExtensionFilters().add(new ExtensionFilter("Text documents", "*.txt"));
				fc.getExtensionFilters().add(new ExtensionFilter("All files", "*.*"));
				File plik = fc.showSaveDialog(new Stage());

				try {
					Scanner skaner = new Scanner(obszarNotatnika.getText());
					PrintWriter writer = new PrintWriter(plik);
					while (skaner.hasNext()) {
						writer.println(skaner.nextLine() + "\n");
						writer.close();
					}
					Main.updateTitle(plik.getName());
					skaner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuWyjdz.setOnAction(x -> Platform.exit());

	}

	private void konfiguracjaEdycjaMenu() {

		menuWytnij.setOnAction(x -> obszarNotatnika.cut());
		menuKopiuj.setOnAction(x -> obszarNotatnika.copy());
		menuWklej.setOnAction(x -> obszarNotatnika.paste());
		menuUsun.setOnAction(x -> obszarNotatnika.replaceSelection(""));
	}

	private void konfiguracjaFormatMenu() {

		menuRozmiar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Integer> choices = new ArrayList<Integer>();
				int rozmiarCzcionkiWybor = 1;

			      while(rozmiarCzcionkiWybor<=108){
			    	  choices.add(rozmiarCzcionkiWybor);
			    	  rozmiarCzcionkiWybor++;
			      }


				ChoiceDialog<Integer> dialog = new ChoiceDialog<>(12, choices);
				dialog.setTitle("Font size");
				dialog.setHeaderText("Font size:");
				Optional<Integer> result = dialog.showAndWait();
				if (result.isPresent()){
					rozmiarCzcionki = result.get();
				    obszarNotatnika.setFont(Font.font(nazwaCzcionki, FontWeight.NORMAL, rozmiarCzcionki));
				}


			}
		});

		menuCzcionka.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<String> choices = new ArrayList<String>();
					choices.add("Arial");
			    	choices.add("Calibri");
					choices.add("Century");
					choices.add("Consolas");
					choices.add("Courier");
					choices.add("Georgia");
					choices.add("Impact");
					choices.add("Lato");
					choices.add("Verdana");
				ChoiceDialog<String> dialog = new ChoiceDialog<>("Verdana", choices);
				dialog.setTitle("Font");
				dialog.setHeaderText("Font:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					nazwaCzcionki = result.get();
				    obszarNotatnika.setFont(Font.font(nazwaCzcionki, FontWeight.NORMAL, rozmiarCzcionki));
				}


			}
		});

	}

	private void konfiguracjaWidokMenu() {

		menuZawijanie.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (menuZawijanie.isSelected()) {
					obszarNotatnika.setWrapText(true);
				} else if (!menuZawijanie.isSelected()) {
					obszarNotatnika.setWrapText(false);
				}

			}
		});

	}


	private void konfiguracjaPomocMenu() {

		menuProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("About BJORGUL.NOTE");
            	alert.setHeaderText(null);
            	alert.setContentText("author - Bart³omiej Szczêœniak \n\n"
            			+ "em@ail - bartlomiej.szczesniak@gmail.com\n\n"
            			+ "github - github.com/Bjorgul");
            	alert.showAndWait();

            }
        });

	}

}
