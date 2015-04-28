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
			String path = "D://uploadFile//linetransport";
			file = new File(path);
			file.mkdirs();//�Է��ļ��в�����
			return path;
		}
		else//linuxϵͳ (δ����)
		{
			//����ļ��в��������û�д���
			String path= "/usr/local/tomcat7/webapps/DaTian/uploadFile/linetransport";
			file=new File(path);
			file.mkdirs();
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
