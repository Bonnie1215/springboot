package com.buleocean_health.springboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CvsUtils {
	public static void main(String[] args) throws Exception {
		// 获取cvs中的所有数据放到csvAllList中
//		List<Object> csvAllList = new ArrayList<>();
//		readCvs("D:\\test\\1111哈.csv", csvAllList);
//		csvAllList.forEach(aa ->{
//			System.out.println(aa);
//		});
//		
//		
//		// xlsx转成cvs
//		{
//			// 方式一：使用文件路径方式xlsx转成cvs
//			XLSX2CSV xlsx2csv = new XLSX2CSV("D:\\test\\哈哈.xlsx", "D:\\test\\1111哈.csv");
//			xlsx2csv.process();
//			
//			// 方式二：使用流形式xlsx转成cvs
//			InputStream inputStream = new FileInputStream(new File("D:\\test\\哈哈.xlsx"));
//			OPCPackage pkg = OPCPackage.open(inputStream);
//			PrintStream output = new PrintStream(new File("D:\\test\\hello哈1111.csv"));
//			XLSX2CSV xlsx2csv2 = new XLSX2CSV(pkg, output, 0);
//			xlsx2csv2.process();
//		}
		
		// xls转成cvs
		{
			// 方式一：使用文件路径方式xls转成cvs
//			XLS2CSV xls2csv = new XLS2CSV("D:\\test\\护工模板 (1).xls", "D:\\test\\xls2cvs01.csv");
//			xls2csv.process();
			
			// 方式二：使用流形式xls转成cvs
			FileInputStream inputStream = new FileInputStream(new File("D:\\test\\护工模板 (1).xls"));
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);  
			PrintStream output = new PrintStream(new File("D:\\test\\xls2cvs02.csv"));
			XLS2CSV xls2csv2 = new XLS2CSV(fs, output, 0);
			xls2csv2.process();
		}
		// TODO 删除新创建的cvs文件(D:\\test\\hello哈1111.csv)
		
	}
	
	
	/**
	 * 读取cvs文件
	 * @param cvsPath
	 * @param csvAllList  存放csv中的所有数据
	 * @throws Exception
	 */
	public static void readCvs(String cvsPath, List<Object> csvAllList) throws Exception{
		CSVReader csvReader = null;
		InputStreamReader csvInputStreamReader = null;
		try {
			List<String> lineList = new ArrayList<>();
			File cvsFile = new File(cvsPath);
			csvInputStreamReader = new InputStreamReader(new FileInputStream(cvsFile), "GBK");
			csvReader = new CSVReader(csvInputStreamReader, CSVWriter.DEFAULT_SEPARATOR,  CSVWriter.DEFAULT_QUOTE_CHARACTER);
			List<String[]> csvList = csvReader.readAll();
			Boolean isEmptyLine = false;
			for(int i=0, csvLength=csvList.size(); i<csvLength && !isEmptyLine; i++) {
				lineList = Arrays.asList(csvList.get(i));
				boolean flag = true;
				for (int j=0; j<lineList.size(); j++) { 
					if (StringUtils.isEmpty(lineList.get(j))) {
						flag =  false;
					}
				}
				isEmptyLine = !flag;
				if (!isEmptyLine) {
					csvAllList.add(lineList);
				}
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("文件不存在");
		} finally {
			csvInputStreamReader.close();
			csvReader.close();
		}
	}
}
