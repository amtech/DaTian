package cn.edu.bjtu.bean.search;

/**
 * ����������bean
 * @author RussWest0
 * @date   2015��6��9�� ����10:49:10
 */
public class LinetransportSearchBean {

	private String startPlace;//��ֹ����
	private String endPlace;//��ֹ����
	private String transportType;//��������
	private Float refPrice;//�ο�����
	
	private String fromPlace;//��Ӧʼ�����У�������startPlace�ظ����ȱ���

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public Float getRefPrice() {
		return refPrice;
	}

	public void setRefPrice(Float refPrice) {
		this.refPrice = refPrice;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	
	
	
}
