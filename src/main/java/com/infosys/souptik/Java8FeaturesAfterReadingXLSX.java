package com.infosys.souptik;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.infosys.souptik.constant.ExcelConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Java8FeaturesAfterReadingXLSX {
	
	public static void main(String[] args) throws IOException {
		XSSFWorkbook wb = null;
		try {
			File file = new File(ReadPropertiesFile.getValueFromPropertiesFile(ExcelConstant.EXCEL_FILE_LOCATION,ExcelConstant.PROPERTIES_KEY_XLSX));
			//File file = new File("C:\\GradleProject\\Countries_of_the_world.xlsx");   //creating a new file instance  
			log.info("filename : {} " , file.getName());
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			int count =0;
			Row row = null;
			wb = new XSSFWorkbook(fis);   
			log.info("------Reading the file ----------" );
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			log.info("Total Number of Rows : {}", sheet.getPhysicalNumberOfRows());
			
			if(!checkFileStructure(sheet)) {
				log.info("{} can not be uploaded because file structure is not specified format", file.getName());
				System.exit(0);
			}
			else if(checkJunkCharacter(sheet)) {
				log.info("{} can not be uploaded because Junk character exists", file.getName());
				System.exit(0);
			}
			else {
				Iterator<Row> rowItr = sheet.rowIterator();    //iterating over excel file  
				List<CountryDetails> countryDetailsList = new ArrayList<>();
				CountryDetails countryDetails = null;
				while (rowItr.hasNext()) {
					if(count < 1) {
						count++;
						rowItr.next();
					}
					else {
						row = rowItr.next();
						if(!isRowEmpty(row)) {
							countryDetails = new CountryDetails();
							countryDetails.setCountry(getCellValue(row,0));
							countryDetails.setRegion(getCellValue(row,1));
							countryDetails.setPopulation(getCellValue(row,2).equals("")?0.0:Double.parseDouble(getCellValue(row,2)));
							countryDetails.setArea(getCellValue(row,3).equals("")?0.0:Double.parseDouble(getCellValue(row,3)));
							countryDetails.setDensity(getCellValue(row,4).equals("")?0.0:Double.parseDouble(getCellValue(row,4)));
							countryDetails.setCoastline(getCellValue(row,5).equals("")?0.0:Double.parseDouble(getCellValue(row,5)));
							countryDetails.setNetMigration(getCellValue(row,6).equals("")?0.0:Double.parseDouble(getCellValue(row,6)));
							countryDetails.setInfrantMortality(getCellValue(row,7).equals("")?0.0:Double.parseDouble(getCellValue(row,7)));
							countryDetails.setGdp(getCellValue(row,8).equals("")?0:Integer.parseInt(getCellValue(row,8)));
							countryDetails.setLiteracy(getCellValue(row,9).equals("")?0.0:Double.parseDouble(getCellValue(row,9)));
							countryDetails.setPhones(getCellValue(row,10).equals("")?0.0:Double.parseDouble(getCellValue(row,10)));
							countryDetails.setArable(getCellValue(row,11).equals("")?0.0:Double.parseDouble(getCellValue(row,11)));
							countryDetails.setCrops(getCellValue(row,12).equals("")?0.0:Double.parseDouble(getCellValue(row,12)));
							countryDetails.setOthers(getCellValue(row,13).equals("")?0.0:Double.parseDouble(getCellValue(row,13)));
							countryDetails.setClimate(getCellValue(row,14).equals("")?0.0:Double.parseDouble(getCellValue(row,14)));
							countryDetails.setBirthRate(getCellValue(row,15).equals("")?0.0:Double.parseDouble(getCellValue(row,15)));
							countryDetails.setDeathRate(getCellValue(row,16).equals("")?0.0:Double.parseDouble(getCellValue(row,16)));
							countryDetails.setAgriculture(getCellValue(row,17).equals("")?0.0:Double.parseDouble(getCellValue(row,17)));
							countryDetails.setIndustry(getCellValue(row,18).equals("")?0.0:Double.parseDouble(getCellValue(row,18)));
							countryDetails.setService(getCellValue(row,19).equals("")?0.0:Double.parseDouble(getCellValue(row,19)));
							
							countryDetailsList.add(countryDetails);
						}
					}
				}	
				log.info("CountryList size :: {}" , countryDetailsList.size());
				System.out.println("CountryList size :: " + countryDetailsList.size());
				log.info("Countries of Africa with GDP reverse order {}", countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("AFRICA"))
											.sorted(Comparator.comparing(CountryDetails::getGdp).reversed())
											.map(country-> country.getCountry() + " " + country.getGdp())
											.collect(Collectors.toList()));
				System.out.println("Countries of Africa with GDP reverse order " + countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("AFRICA"))
											.sorted(Comparator.comparing(CountryDetails::getGdp).reversed())
											.map(country-> country.getCountry() + " " + country.getGdp())
											.collect(Collectors.toList()));
				
				log.info("Country List of Latin America region {}", countryDetailsList.stream()
											.filter(reg -> reg.getRegion().contains("AMER")).collect(Collectors.toList()));
				System.out.println("Country List of Latin America region " + countryDetailsList.stream()
										 .filter(reg -> reg.getRegion().contains("AMER")).collect(Collectors.toList()));
				log.info("Average literacy of countries of Latin America {}", countryDetailsList.stream()
											.filter(reg -> reg.getRegion().contains("AMER"))
											.mapToDouble(lit -> lit.getLiteracy()).average().getAsDouble());
				System.out.println("Average literacy of countries of Latin America " + countryDetailsList.stream()
											.filter(reg -> reg.getRegion().contains("AMER"))
											.mapToDouble(lit -> lit.getLiteracy()).average().getAsDouble());
				log.info("Country List of Oceania region {}", countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.collect(Collectors.toList()));
				System.out.println("Country List of Oceania region " + countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.collect(Collectors.toList()));
				
				log.info("Death Rate number in countries of Oceania region {}", countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getDeathRate()).count());
				System.out.println("Death Rate number in countries of Oceania region " + countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getDeathRate()).count());
				
				log.info("Total Death Rate in countries of Oceania region {}", countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getDeathRate()).sum());
				System.out.println("Total Death Rate in countries of Oceania region " + countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getDeathRate()).sum());
				
				log.info("Total Birth Rate in countries of Oceania region {}", countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getBirthRate()).sum());
				System.out.println("Total Birth Rate in countries of Oceania region " + countryDetailsList.stream()
											.filter(region -> region.getRegion().contains("OCEANIA"))
											.mapToDouble(d -> d.getBirthRate()).sum());
			}
		}  
		catch(Exception e) {  
			e.printStackTrace();  
		}  
		finally {
			wb.close();
		}
	}
	
	public static String getCellValue(Row row,int index) {
		String s = StringUtils.EMPTY;
		try {
			if(row.getCell(index) == null)
				s = StringUtils.EMPTY;
			else
				s = row.getCell(index).getStringCellValue().trim();
		}
		catch(Exception e) {
			String temp = String.valueOf(row.getCell(index) == null ? 0 : row.getCell(index).getNumericCellValue());
			s = trailingZeroDiscard(temp); 
		}
		return s;
	}
	
	@SuppressWarnings("deprecation")
	private static String trailingZeroDiscard(String param) {
		if(NumberUtils.isNumber(param)) {
			DecimalFormat decimalFormat = new DecimalFormat("0.#####");
			param = decimalFormat.format(Double.valueOf(param));
		}
		return param;
	}
	
	public static boolean isRowEmpty(Row row) {
		boolean bool = false;
		StringBuilder sb = new StringBuilder();
		int noOfColumns = row.getLastCellNum();
		for(int i = 0; i < noOfColumns; i++) {
			sb.append(getCellValue(row,i));
		}
		if(sb.toString().equals(StringUtils.EMPTY))
			bool = true;
		else
			bool = false;
		return bool;
	}
	
	private static boolean checkFileStructure(XSSFSheet sheet) throws IOException {
		String propertiesFile = ExcelConstant.FILE_STRUCTURE_LOCATION;
		log.debug(propertiesFile);
		boolean bool = true;
		XSSFRow row = sheet.getRow(0);
		Map<String,String> fileStructureMap = new LinkedHashMap<>();
		int noOfColumns = row.getLastCellNum();
		String propValue = StringUtils.EMPTY;
		log.debug("Number of columns {}", noOfColumns);
		for(int i=0; i<noOfColumns; i++) {
			if(row.getCell(i).getAddress() != null && row.getCell(i) != null)
				fileStructureMap.put(row.getCell(i).getAddress().toString(), row.getCell(i).toString());
		}
		for(Map.Entry<String,String> m: fileStructureMap.entrySet()) {
			propValue = ReadPropertiesFile.getValueFromPropertiesFile(propertiesFile, m.getKey().trim());
			if(!m.getValue().trim().equals(propValue.trim())) {
				log.debug("{} index is wrongly mapped with {}" , m.getKey(), m.getValue());
				bool = false;
				continue;
			}
		}
		return bool;
	}
	
	private static boolean checkJunkCharacter(XSSFSheet sheet) {
		boolean bool = false;
		Row row = null;
		boolean charFound = true;
		String str = "[^a-zA-Z0-9!@#$%^&*()_+{}:;.,-=<>?/~`'\"\\n ]";
		Iterator<Row> rowIter = sheet.rowIterator();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		log.debug("No of Columns {}", noOfColumns);
		while(rowIter.hasNext()) {
			row = rowIter.next();
			Pattern p = Pattern.compile(str);
			Matcher m = null;
			for(int i=0; i<noOfColumns; i++) {
				m = p.matcher(getCellValue(row, i).trim());
				charFound = m.find();
				
				if(charFound && !(getCellValue(row, i).trim().contains("-")) && !(getCellValue(row, i).trim().contains("["))
						&& !(getCellValue(row, i).trim().contains("]"))
						&& !(getCellValue(row, i).trim().startsWith(" "))
						&& !(getCellValue(row, i).trim().endsWith(" "))) {
					log.debug("Junk character exists in {} row {} cell" , (row.getRowNum()+1) , getCellValue(row, i).trim());
					bool = true;
				}
			}
			if(!bool)
				continue;
		}
		return bool;
	}
	
}
