package sample;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/** 
 * ���� ��������� � ������������ <b>container</b>.
 * @autor ���� �������
 * @version 1.0
*/
public class ServiceContainer {
	
	List<CarService> container = new ArrayList<CarService>();
	 private String exceptionInfo = "";	
	 String date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new Date());
    /**
     * ����������� ��-������������.
     */
    public ServiceContainer() {}

    public List<CarService> getArray() {
    	return container;
    }
    
    public String getExcInfo() {	
    	return exceptionInfo;
    
    }
    
    /**
     * ���� ���� � ������� CarService i ������ �������� ��������
     * @param fName ����� �����
     * @return 
     * @return 
     * @throws IOException ������� ��� ������������� ��� �����
     */
    public void readFromFile(File fName) throws Exception {
        //�������� ���� "FileReader" ��� ����� "File"
        try {
            FileReader fileRead = new FileReader(fName);
            //�������� "BufferedReader" ��� ������� ����� �� ������
            BufferedReader buffRead = new BufferedReader(fileRead);
            //������ ������ �����
            CarService.IndexCols(buffRead.readLine());
            Exception fileReadingException = null;
            String line;
            int lineNo = 1;
            line = buffRead.readLine();
            while (line != null) {
                try {
                    container.add(new CarService(line));
                    line = buffRead.readLine();
                    lineNo++;
                } catch (Exception exc) {
                    line = buffRead.readLine();
                    fileReadingException = new Exception(date + " :  Incorrect attribute in line " + lineNo + "\n");
                }
            }
            fileRead.close();
            if (fileReadingException != null) {
                throw fileReadingException;
            }
        } catch (IOException exc) {
            throw new Exception(exc.getLocalizedMessage());
        }
    }
    
    /**
     * ������� ������ ���������� � ����������
     */
    public void showAll() {
    	System.out.format("\nAll data available:\n");
        for (int i = 0; i < container.size(); i++) {
            System.out.println(container.get(i).toString());
        }
    }
    /**
     * ����� ���������� � ���� � ������ Json
     * @param fileOut ����� �����
     */
    public void toJson(ObservableList<CarService> observableCarList, File fileOut) throws Exception {
        try {
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(fileOut));
            outWriter.write("[\n");
            int i=0;
            for(CarService CarServices : observableCarList) {
            	String s =" {\n"+ CarServices.toJson()+ " }";
            	if (i < observableCarList.size() - 1) {
                    s = s + ",";
                }
            	i+=1;
                outWriter.write(s);
                outWriter.newLine();
            }
            outWriter.write("]");
            System.out.println(observableCarList.toString());
            outWriter.close();
        } catch (IOException e) {
            Alert ioAlert = new Alert(Alert.AlertType.ERROR, "OOPS!", ButtonType.OK);
            ioAlert.setContentText("Sorry. An error has occurred.");
            ioAlert.showAndWait();
            if(ioAlert.getResult() == ButtonType.OK) {
                ioAlert.close();
            }
    }
}
}