package sample;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

/**
 * ���� ���� ��������������� ��� ������������ � �����������
 * <b>FileName</b>,<b>exc</b>.
 * 
 * @author ������� ����
 * @version 1.0
 */
public class Exception extends RuntimeException {

	static File FileName = null;
	private String exc;

	/**
	 * ����������� � ����������.
	 */

	public Exception(String message) {
	    super(message);
	}
	
	/**
	 * ������� - ������ ����� � ���� ��� ����
	 */
	static public void ErrInfo(String exc) {
		System.out.println(exc);
		if (FileName != null) {
			try {
				FileWriter log = new FileWriter(FileName, true);
				log.write(exc);
				log.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
