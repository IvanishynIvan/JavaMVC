package sample;
/** 
 * ���� ��������� � ����������� <b>numberofcar</b>, <b>marka</b>,<b>probig</b>,<b>maister</b>,<b>price</b>.
 * @autor ���� �������
 * @version 1.0
*/

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.*;

public class CarService {//implements Comparable<CarService> {
	/**
     * ������� - ����� ���������
     */
    private IntegerProperty numberofcar = new SimpleIntegerProperty(this, "numberofcar", -1);
    /**
     * ������� - ����� ���������
     */
    //protected String marka;
    private StringProperty marka = new SimpleStringProperty(this, "marka", "");
    /**
     * ������� - ����� ���������
     */
    private IntegerProperty probig = new SimpleIntegerProperty(this, "probig", -1);
    /**
     * ������� - �������, ���� ��������� ������
     */
    private StringProperty maister = new SimpleStringProperty(this, "maister", "");
    /**
     * ������� - ������� �������
     */
    private IntegerProperty price = new SimpleIntegerProperty(this, "price", -1);
  
    public static final String[] title = {"Numberofcar", "Marka", "Probig", "Maister", "Price"};
    public static int[] ColINDX = {0, 1, 2, 3, 4};
    String date = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new Date());
    /**
     * ������� - ���������� ��������
     */
    public static void IndexCols(String fLine) throws Exception {
        if (fLine == null) {
            throw new Exception("File is empty!");
        }
        String pNames[] = fLine.split("\t");
        if (pNames.length != title.length) {
            throw new Exception("Number of colums is wrong: "+pNames.length + " insead of "+ title.length);
        }
        for (int i = 0; i < title.length; i++) {
            int j = pNames.length - 1;
            for (; j >= 0; j--) {
                if (title[i].equalsIgnoreCase(pNames[j])) {
                    ColINDX[i] = j;
                    break;
                }
                if (j == -1) {
                    throw new Exception("no col " + title[i]);
                }
            }
        }
    }
    /** 
     * ����������� - ��������� ������ ����� � ����������� ���������
     * @param Data - ����������
     * @see CarService#CarService()
     */
    public CarService(String Data) throws Exception {
        String cols[] = Data.split("\t");
        int colNo = -1;
        setNumberofCar(cols[ColINDX[++colNo]]);
        setMarka(cols[ColINDX[++colNo]]);
        setProbig(cols[ColINDX[++colNo]]);
        setMaister(cols[ColINDX[++colNo]]);
        setPrice(cols[ColINDX[++colNo]]);
    }

    /**
     * ����������� ��-������������.
     */
    public CarService() {
    }
    /**
     * ����������� � �����������.
     */
    public CarService(IntegerProperty numberofcar, StringProperty marka, 
    		IntegerProperty probig, StringProperty maister, IntegerProperty price) {
        this.numberofcar = numberofcar;
        this.marka = marka;
        this.probig = probig;
        this.maister = maister;
        this.price = price;
    }
    /**
     * ����� ���� {@link CarService#numberofcar}.
     */
    
    public void setNumberofCar(int numberofcar) {
        this.numberofcar.set(numberofcar);
    } 
    /**
     * ����� ���� numberofcar ��� String
     */
    public void setNumberofCar(String numberofcar)  throws Exception
    {
        try
        {
            this.setNumberofCar(Integer.parseInt(numberofcar));
        }
        catch(NumberFormatException exception)	
        {
            throw new Exception(date + " :  Incorrect CarServiceNumb\n");
        }
    }
    /**
     * ����� ���� {@link CarService#marka}.
     */
    
    public void setMarka(String marka) {
    	if (marka.isEmpty()) throw new NullPointerException("Data is empty");
        this.marka.set(marka);
    }
    
    /**
     * ����� ���� {@link CarService#probig}.
     */
    public void setProbig(int probig) {
        this.probig.set(probig);
    }
    /**
     * ����� ���� probig ��� String
     */
    public void setProbig(String probig)  throws Exception
    {
        try
        {
            this.setProbig(Integer.parseInt(probig));
        }
        catch(NumberFormatException exception)
        {
            throw new Exception(date + " :  Incorrect Probig\n");
        }
    }
    /**
     * ����� ���� {@link CarService#maister}.
     */
    public void setMaister(String maister) {
    	if (maister.isEmpty()) throw new NullPointerException("Data is empty");
        this.maister.set(maister);
    }
    /**
     * ����� ���� {@link CarService#price}.
     */
    public void setPrice(int price) {
        this.price.set(price);
    }
    /**
     * ����� ���� price ��� String
     */
    public void setPrice(String price)  throws Exception
    {
        try
        {
            this.setPrice(Integer.parseInt(price));
        }
        catch(NumberFormatException exception)
        {
            throw new Exception(date +" :  Incorrect Price\n");
        }
    }
    
    /**
     * ����� ���� {@link CarService#numberofcar}
     */
    public int getNumberofcar() {
        return numberofcar.get();
    }
    public IntegerProperty numberofcarProperty() {
        return numberofcar;
    }
    
    /**
     * ����� ���� {@link CarService#marka}
     */
    
    public String getMarka() {
        return marka.get();
    }
    public StringProperty markaProperty() {
        return marka;
    }
    
    /**
     * ����� ���� {@link CarService#probig}
     */
    public int getProbig() {
        return probig.get();
    }
    public IntegerProperty probigProperty() {
        return probig;
    }
    
    /**
     * ����� ���� {@link CarService#maister}
     */
    
    public String getMaister() {
        return maister.get();
    }
    public StringProperty maisterProperty() {
        return maister;
    }
   
    /**
     * ����� ���� {@link CarService#price}
     */
    
    public int getPrice() {
        return price.get();
    }
    public IntegerProperty priceProperty() {
        return price;
    }
    
    /**
     * ������� � ����������� ��� ���� ����� CarService
     */
    public String toString() {
    	return "Number of car: " + getNumberofcar() + " | Marka: " + getMarka() +
    			" | Probig: " + getProbig() + "km" + " | Maister: " + getMaister() + " | Price: " 
    			+ getPrice() +"$";
    
    }
    /**
     * ������� � ����������� ��� ���� ����� CarService � ������ Json
     */
    public String toJson() {
        String s = "   \"" + "Number of car" + "\": \"" + getNumberofcar() + "\",\n"
                + "   \"" + "Marka" + "\": \"" + getMarka() + "\",\n"
                + "   \"" + "Probig" + "\": \"" + getProbig() + "\",\n"
                + "   \"" + "Maister" + "\": \"" + getMaister() + "\",\n"
                + "   \"" + "Price" + "\": \"" + getPrice() + "\",\n";
        return s;
    }
    /**
     * ��������� ���� ����� ����� CarServiceChild �� �������
     * @param carservice2 ���� ����� CarServiceChild
     */
    /*@Override
   	public int compareTo(CarService carservice2) {
        return probig - carservice2.probig;
    }*/

}
