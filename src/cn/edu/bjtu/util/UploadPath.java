package cn.edu.bjtu.util;

import java.io.File;

/**
 * ���ظ����ϴ���·��-ÿ��·������������windows��linux
 * 
 * @author RussWest0
 *
 */
public class UploadPath {

	
	public static File file = null;

	/**
	 * ���ظ����ļ��ϴ���·��
	 * 
	 * @return
	 */
	public static String getLinetransportPath() {
		// return sep+Base_Directory+sep+"linetransport";
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//linetransport";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/linetransport";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	
	public static String getCitylinePath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//cityline";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/cityline";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	
	public static String getGoodsPath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//goods";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/goods";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	
	public static String getWarehousePath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//warehouse";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/warehouse";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}

	public static String getClientPath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//client";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/client";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	
	public static String getContactPath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//client";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/client";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	
	public static String getDriverPath() {
		if (isWindows()) {//windowsϵͳ
			String path = "D://uploadFile//driver";//�ϴ���D��
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/driver";
			file=new File(path);
			file.mkdirs();//��ֹ�ļ��в�����
			return path;
		}
	}
	/*
	 * public static void main(String [] args) { File file=new File("");
	 * System.out.println("path+"+file.getAbsolutePath()); }
	 */
	
	// �жϵ�ǰϵͳ
	public static boolean isWindows() {
		boolean flag = false;
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.indexOf("WINDOWS") != -1) {
			flag = true;
		}
		return flag;
	}

}
