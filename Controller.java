package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import sample.Exception;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Контролер для макета огляду файлів. Надає основні дії
 * для зміни, видалення та створення файлів, об'єктів.
 * @autor Іван Іванішин
 * @version 1.0
*/

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="CarNumber"
    private TableColumn<CarService, Integer> CarNumber;

    @FXML // fx:id="Marka"
    private TableColumn<CarService, String> Marka;

    @FXML // fx:id="Probig"
    private TableColumn<CarService, Integer> Probig;

    @FXML // fx:id="Maister"
    private TableColumn<CarService, String> Maister;

    @FXML // fx:id="Price"
    private TableColumn<CarService, Integer> Price;

    @FXML // fx:id="CarNumberField"
    private TextField carnumberField;

    @FXML // fx:id="markaField"
    private TextField markaField;

    @FXML // fx:id="probigField"
    private TextField probigField;

    @FXML // fx:id="maisterField"
    private TextField maisterField;

    @FXML // fx:id="priceField"
    private TextField priceField;

    @FXML
    private TableView<CarService> carTable;
    
    @FXML
    private TextArea listLogs;   

    @FXML // fx:id="addBtn"
    private Button addBtn;

    @FXML // fx:id="deleteBtn"
    private Button deleteBtn;

    @FXML
    private MenuBar fileMenu;

    ObservableList<CarService> observableCarList = FXCollections.observableArrayList();
    private String exceptionInfo = "";
    ServiceContainer container = new ServiceContainer();
    
    
    /**
     * Ініціазілація класа контролера. 
     * 
     * @param URl -  шлях до файлу fxml, ResourceBundle - властивості інтернатоналізації графічного інтерфейсу
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ініціалізування зміни атрибутів
        CarNumber.setOnEditCommit(e -> CarNumber_OnEditCommit(e));
        Marka.setOnEditCommit(e -> Marka_OnEditCommit(e));
        Probig.setOnEditCommit(e -> Probig_OnEditCommit(e));
        Maister.setOnEditCommit(e -> Maister_OnEditCommit(e));
        Price.setOnEditCommit(e -> Price_OnEditCommit(e));

        carTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        CarNumber.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Marka.setCellFactory(TextFieldTableCell.forTableColumn());
        Probig.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Maister.setCellFactory(TextFieldTableCell.forTableColumn());
        Price.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        CarNumber.setCellValueFactory(cellData -> cellData.getValue().numberofcarProperty().asObject());
        Marka.setCellValueFactory(cellData -> cellData.getValue().markaProperty());
        Probig.setCellValueFactory(cellData -> cellData.getValue().probigProperty().asObject());
        Maister.setCellValueFactory(cellData -> cellData.getValue().maisterProperty());
        Price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        addBtn.setDisable(true);
        deleteBtn.setDisable(true);
        carTable.setItems(observableCarList);
        carTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        carTable.setPlaceholder(new Label("Your Table is Empty"));

        carnumberField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (carnumberField.isFocused()) {
                    addBtn.setDisable(false);
                }
            }
        });
        carTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (carTable.isFocused()) {
                    deleteBtn.setDisable(false);
                }
            }
        });
    }//кінець ініціалізації

    /*
    ----------------------------------------------Управління обробниками---------------------------------------------
     */
    
    /**
     * Додавання до таблиці отриманих даних від користувача
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleAddButtonClick(ActionEvent event) throws Exception {
            if (isValidInput(event)) {  
            	CarService car = new CarService(); 
                    try {
                    	car.setNumberofCar(carnumberField.getText());
                        } catch (Exception exc) {
                        	listLogs.appendText(exc.getMessage());
                        }
                    try {
                    	car.setMarka(markaField.getText());
                        } catch (Exception exc) {
                        	listLogs.appendText(exc.getMessage());
                        }
                    try {
                    	car.setProbig(probigField.getText());
                        } catch (Exception exc) {
                        	listLogs.appendText(exc.getMessage());
                        }
                    try {
                    	car.setMaister(maisterField.getText());
                        } catch (Exception exc) {
                        	listLogs.appendText(exc.getMessage());
                        }
                    try {
                    	car.setPrice(priceField.getText());
                        } catch (Exception exc) {
                        	listLogs.appendText(exc.getMessage());
                        }
                    priceField.clear();
                    maisterField.clear();
                    maisterField.clear();
                    probigField.clear();
                    markaField.clear();
                    carnumberField.clear();
                    String str = date() + " :  Object added\n";
                    this.exceptionInfo += str;
                    listLogs.appendText(str);
                    observableCarList.add(car); 
            }
    }
    
    /**
     * У разі порожніх полів. Повідомляє про відповідне порожнє поле.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    private boolean isValidInput(ActionEvent event) {
        Boolean validInput = true;
        if(carnumberField == null || carnumberField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyFirstName = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyFirstName.setContentText("CarNumber is EMPTY");
            emptyFirstName.initModality(Modality.APPLICATION_MODAL);
            emptyFirstName.initOwner(owner);
            emptyFirstName.showAndWait();
            if(emptyFirstName.getResult() == ButtonType.OK) {
                emptyFirstName.close();
                carnumberField.requestFocus();
            }
        }
        if(markaField == null || markaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyLastName = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyLastName.setContentText("Marka is EMPTY");
            emptyLastName.initModality(Modality.APPLICATION_MODAL);
            emptyLastName.initOwner(owner);
            emptyLastName.showAndWait();
            if (emptyLastName.getResult() == ButtonType.OK) {
                emptyLastName.close();
                markaField.requestFocus();
            }
        }
        if(probigField == null || probigField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyNetID = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyNetID.setContentText("Probig is EMPTY");
            emptyNetID.initModality(Modality.APPLICATION_MODAL);
            emptyNetID.initOwner(owner);
            emptyNetID.showAndWait();
            if (emptyNetID.getResult() == ButtonType.OK) {
                emptyNetID.close();
                probigField.requestFocus();
            }
        }
        if(maisterField == null || maisterField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyAge = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyAge.setContentText("Maister is EMPTY");
            emptyAge.initModality(Modality.APPLICATION_MODAL);
            emptyAge.initOwner(owner);
            emptyAge.showAndWait();
            if (emptyAge.getResult() == ButtonType.OK) {
                emptyAge.close();
                maisterField.requestFocus();
            }
        }
        if(priceField == null || priceField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyGPA = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyGPA.setContentText("Price is EMPTY");
            emptyGPA.initModality(Modality.APPLICATION_MODAL);
            emptyGPA.initOwner(owner);
            emptyGPA.showAndWait();
            if (emptyGPA.getResult() == ButtonType.OK) {
                emptyGPA.close();
                priceField.requestFocus();
            }
        }
        return validInput;
    }
    
    /**
     * Обробка зміни стовпців.
     * @param Event - cемантична подія, яка вказує на те, що сталася дія
     */
	@SuppressWarnings("unchecked")
	public void CarNumber_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<CarService, Integer> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<CarService, Integer>) e;
        CarService car = cellEditEvent.getRowValue();
        String str = date() + " :  CarNumber was changed from '" + cellEditEvent.getRowValue().getNumberofcar() 
        		+ "' to '" + cellEditEvent.getNewValue() + "'\n" ;
        car.setNumberofCar(cellEditEvent.getNewValue());
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    @SuppressWarnings("unchecked")
	public void Marka_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<CarService, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<CarService, String>) e;
        CarService car = cellEditEvent.getRowValue();
        String str = date() + " :  Marka was changed from '" + cellEditEvent.getRowValue().getMarka() 
        		+ "' to '" + cellEditEvent.getNewValue() + "'\n" ;
        car.setMarka(cellEditEvent.getNewValue());
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    @SuppressWarnings("unchecked")
	public void Probig_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<CarService, Integer> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<CarService, Integer>) e;
        CarService car = cellEditEvent.getRowValue();
        String str = date() + " :  Probig was changed from '" + cellEditEvent.getRowValue().getProbig() 
        		+ "' to '" + cellEditEvent.getNewValue() + "'\n" ;
        car.setProbig(cellEditEvent.getNewValue());
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    @SuppressWarnings("unchecked")
	public void Maister_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<CarService, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<CarService, String>) e;
        CarService car = cellEditEvent.getRowValue();
        String str = date() + " :  Maister was changed from '" + cellEditEvent.getRowValue().getMaister() 
        		+ "' to '" + cellEditEvent.getNewValue() + "'\n" ;
        car.setMaister(cellEditEvent.getNewValue());
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    @SuppressWarnings("unchecked")
	public void Price_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<CarService, Integer> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<CarService, Integer>) e;
        CarService car = cellEditEvent.getRowValue();
        String str = date() + " :  Price was changed from '" + cellEditEvent.getRowValue().getPrice() 
        		+ "' to '" + cellEditEvent.getNewValue() + "'\n" ;
        car.setPrice(cellEditEvent.getNewValue());
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    
    /**
     * Видалення об'єкта з таблиці вибраного користувачем.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleDeleteButtonClick(ActionEvent event) {
        if(!observableCarList.isEmpty()) {
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Confirm", ButtonType.OK, ButtonType.CANCEL);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            deleteAlert.setContentText("Are you sure you want to delete this?\n\nTHIS CANNOT BE UNDONE.");
            deleteAlert.initModality(Modality.APPLICATION_MODAL);
            deleteAlert.initOwner(owner);
            deleteAlert.showAndWait();
            String str = date() + " :  The object has been deleted\n";
            if(deleteAlert.getResult() == ButtonType.OK) {
                observableCarList.removeAll(carTable.getSelectionModel().getSelectedItems());
                carTable.getSelectionModel().clearSelection();
                this.exceptionInfo += str;
                listLogs.appendText(str);
            }
            else {
                deleteAlert.close();
            }
        }
    }
    
    /**
     * Очистка полів для вводу користувача.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleClearButtonClick(ActionEvent event) {
        carnumberField.clear();
        markaField.clear();
        probigField.clear();
        maisterField.clear();
        priceField.clear();
        String str = date() + " :  Attributes were cleared\n";
        this.exceptionInfo += str;
        listLogs.appendText(str);
    }
    
    /**
     * Відкриття FileChooser для вибору файла для зчитування даних.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleOpen(ActionEvent event) throws Exception {
    	ServiceContainer container = new ServiceContainer();
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CarService Table");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Txt", "*.txt"));
        File file = fileChooser.showOpenDialog(secondaryStage);
        String str = date() + " :  The file "+ file.getName() + " was opened\n";
        try {
        	listLogs.appendText(str);
        	this.exceptionInfo += str;
        	container.readFromFile(file);
        } catch (Exception exc) {
        	listLogs.appendText(exc.getMessage());
        	this.exceptionInfo += exc.getMessage();
        }
        observableCarList = FXCollections.observableArrayList(container.getArray());
        carTable.setItems(observableCarList);
    }
    
    /**
     * Відкриття FileChooser для збереження json файла.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleSaveJsonFile(ActionEvent event) throws Exception {	
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Json File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Json", "*.json"));
        if(observableCarList.isEmpty()) {
            secondaryStage.initOwner(this.fileMenu.getScene().getWindow());
            Alert emptyTableAlert = new Alert(Alert.AlertType.ERROR, "EMPTY TABLE", ButtonType.OK);
            emptyTableAlert.setContentText("You have nothing to save");
            emptyTableAlert.initModality(Modality.APPLICATION_MODAL);
            emptyTableAlert.initOwner(this.fileMenu.getScene().getWindow());
            emptyTableAlert.showAndWait();
            if(emptyTableAlert.getResult() == ButtonType.OK) {
                emptyTableAlert.close();
            }
        }
        else {
            File file = fileChooser.showSaveDialog(secondaryStage);
            if(file != null) {
                container.toJson(carTable.getItems(), file);
                String str = date() + " :  The file "+ file.getName() + " was saved\n";
            	this.exceptionInfo += str;
            	listLogs.appendText(str);
            	
            }
        }
    }
    
    /**
     * Відкриття FileChooser для збереження log файла.
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void handleSaveLogFile(ActionEvent event)  throws Exception {
    	 Stage secondaryStage = new Stage();
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Save Log File");
         fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
         fileChooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("Log", "*.log"));
         Exception.FileName = fileChooser.showSaveDialog(secondaryStage);
         String str = date() + " :  The file "+ Exception.FileName.getName() + " was saved\n";
     	 this.exceptionInfo +=  str;
     	 listLogs.appendText(str);
     	 Exception.ErrInfo(exceptionInfo);
    }
    
    /**
     * Відкриття вікна "About"
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною 
     */
    public void handleAbout(ActionEvent event) {
    	Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.initStyle(StageStyle.UTILITY);
		dialog.setTitle("About the program and the author");
		dialog.setHeaderText("Practical work # 2.\r\n" + 
				"Separation of behavior, appearance and management; the MVC template.\r\n" + 
				"Graphical user interface."
				+ " ");
		dialog.setContentText("The author of the work - Ivanishyn Ivan Volodumurovych");
		dialog.showAndWait();
    }
    
    /**
     * Закриття програми
     * @param ActionEvent - це клас чи подія, яка отримуює повні посилання, коли подія буде виконаною
     */
    public void closeApp(ActionEvent event) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
        Stage stage = (Stage) fileMenu.getScene().getWindow();
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.initOwner(stage);
        exitAlert.showAndWait();

        if(exitAlert.getResult() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            exitAlert.close();
        }
    }
    
    /**
     * Функція створення дати у певному форматі
     * @return date - дата та час в даний момент 
     */
    public String date() {
    	String date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new Date());
    	return date;
    }
}







