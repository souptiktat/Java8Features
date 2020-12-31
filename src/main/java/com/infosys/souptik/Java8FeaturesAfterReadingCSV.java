package com.infosys.souptik;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.infosys.souptik.constant.ExcelConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Java8FeaturesAfterReadingCSV {
	
	public static void main(String[] args) {
		
		try {
			log.info("{}", ReadPropertiesFile.getValueFromPropertiesFile(ExcelConstant.EXCEL_FILE_LOCATION,ExcelConstant.PROPERTIES_KEY_CSV));
			Reader reader = Files.newBufferedReader(Paths.get(ReadPropertiesFile.getValueFromPropertiesFile(ExcelConstant.EXCEL_FILE_LOCATION,ExcelConstant.PROPERTIES_KEY_CSV)));
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
			
			List<CountryDetails> countryDetailsList = new ArrayList<>();
			CountryDetails countryDetails = null;
			
			for(CSVRecord csvRecord : records) {
				countryDetails = new CountryDetails();
				countryDetails.setCountry(csvRecord.get(0));
				countryDetails.setRegion(csvRecord.get(1));
				countryDetails.setPopulation(csvRecord.get(2).equals("")?0.0:Double.parseDouble(csvRecord.get(2)));
				countryDetails.setArea(csvRecord.get(3).equals("")?0.0:Double.parseDouble(csvRecord.get(3)));
				countryDetails.setDensity(csvRecord.get(4).equals("")?0.0:Double.parseDouble(csvRecord.get(4)));
				countryDetails.setCoastline(csvRecord.get(5).equals("")?0.0:Double.parseDouble(csvRecord.get(5)));
				countryDetails.setNetMigration(csvRecord.get(6).equals("")?0.0:Double.parseDouble(csvRecord.get(6)));
				countryDetails.setInfrantMortality(csvRecord.get(7).equals("")?0.0:Double.parseDouble(csvRecord.get(7)));
				countryDetails.setGdp(csvRecord.get(8).equals("")?0:Integer.parseInt(csvRecord.get(8)));
				countryDetails.setLiteracy(csvRecord.get(9).equals("")?0.0:Double.parseDouble(csvRecord.get(9)));
				countryDetails.setPhones(csvRecord.get(10).equals("")?0.0:Double.parseDouble(csvRecord.get(10)));
				countryDetails.setArable(csvRecord.get(11).equals("")?0.0:Double.parseDouble(csvRecord.get(11)));
				countryDetails.setCrops(csvRecord.get(12).equals("")?0.0:Double.parseDouble(csvRecord.get(12)));
				countryDetails.setOthers(csvRecord.get(13).equals("")?0.0:Double.parseDouble(csvRecord.get(13)));
				countryDetails.setClimate(csvRecord.get(14).equals("")?0.0:Double.parseDouble(csvRecord.get(14)));
				countryDetails.setBirthRate(csvRecord.get(15).equals("")?0.0:Double.parseDouble(csvRecord.get(15)));
				countryDetails.setDeathRate(csvRecord.get(16).equals("")?0.0:Double.parseDouble(csvRecord.get(16)));
				countryDetails.setAgriculture(csvRecord.get(17).equals("")?0.0:Double.parseDouble(csvRecord.get(17)));
				countryDetails.setIndustry(csvRecord.get(18).equals("")?0.0:Double.parseDouble(csvRecord.get(18)));
				countryDetails.setService(csvRecord.get(19).equals("")?0.0:Double.parseDouble(csvRecord.get(19)));
				
				countryDetailsList.add(countryDetails);
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
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
