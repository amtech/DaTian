package cn.edu.bjtu.util;

import java.util.Calendar;
import java.util.Date;

/**
 * id������
 * @author RussWest0
 *
 */
public class IdCreator {

	/**
	 * 
	 * @return �����������id
	 */
	public static String createClientId() {
		return "CL" + (int)(Math.random() * 100000000);
	}
	/**
	 * ������ҵ�ͻ���id
	 * @return
	 */
	public static String createBusinessClientId()
	{
		return "BU"+(int)(Math.random() * 100000000);
	}
	/**
	 * ���س��˷�id
	 * @return
	 */
	public static String createCarrierId() {
		return "CA" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���ظ���id
	 * @return
	 */
	public static String createlineTransportId() {
		return "LI" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���ػ���id
	 * @return
	 */
	public static String createGoodsId() {
		return "GO" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���س�������id
	 * @return
	 */
	public static String createCityLineId() {
		return "CI" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���س���id�����ǳ��ƣ�
	 * @return
	 */
	public static String createCarId() {
		return "CAR" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���زֿ�id
	 * @return
	 */
	public static String createRepositoryId() {
		return "RE" + (int)(Math.random() * 100000000);
	}
	/**
	 * ����˾��id
	 * @return
	 */
	public static String createDriverId() {
		return "DR" + (int)(Math.random() * 100000000);
	}
	/**
	 * ���ض���id-ʱ������
	 * @return
	 */
	public static String createOrderId() {
		return "OR"+(int)(Math.random() * 100000000);
	}
	
	/**
	 * ����Ͷ��id-ʱ������
	 * @return
	 */
	public static String createComplaintId() {
		return "CO"+(int)(Math.random() * 100000000);
	}
	/**
	 * ��������id-ʱ������
	 * @return
	 */
	public static String createAssessId() {
		return "AS"+(int)(Math.random() * 100000000);
	}
	/**
	 * ���غ�ͬid
	 * @return
	 */
	public static String createContractId(){
		return "CO"+(int)(Math.random()*100000000);
	}
	
	

}
