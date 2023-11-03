package com.luckyseven.greendrive;

import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@SpringBootApplication
public class GreendriveApplication implements CommandLineRunner {

	@Autowired
	private SpaceRepository spaceRepository;

	public static void main(String[] args) {
		SpringApplication.run(GreendriveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		makeSpaceDB(); // 최초 실행 시 주차장 DB 자동 생성 함수
	}

	public void makeSpaceDB() throws Exception{
		ClassPathResource resource = new ClassPathResource("parksData.xls");
		InputStream inputStream = resource.getInputStream();
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();

		// 컬럼명 스킵
		if (rows.hasNext()) {
			rows.next();
		}

		while (rows.hasNext()) {
//			System.out.println("while 진입");
			Row row = rows.next();
			String specialNote = row.getCell(25).getStringCellValue();

			if (specialNote.contains("저공해")) {
				Space space = new Space();

				String latitude = row.getCell(28).getStringCellValue();
				String longtitude = row.getCell(29).getStringCellValue();
				try {
					if ( (latitude.isEmpty()) || (longtitude.isEmpty()) ) {
						throw new NumberFormatException();
					}
					space.setLatitude(Double.parseDouble(latitude));
					space.setLongitude(Double.parseDouble(longtitude));
				} catch (NumberFormatException e) {
					continue;
				}

				space.setId(row.getCell(0).getStringCellValue());
				space.setParkName(row.getCell(1).getStringCellValue());
				space.setAddress(row.getCell(4).getStringCellValue().isEmpty() ? row.getCell(5).getStringCellValue():row.getCell(4).getStringCellValue());
				space.setType("공영".equals(row.getCell(2).getStringCellValue())?0:1);

				space.setWeekdayStart(LocalTime.parse(row.getCell(10).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));
				space.setWeekdayEnd(LocalTime.parse(row.getCell(11).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));
				space.setSaturdayStart(LocalTime.parse(row.getCell(12).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));
				space.setSaturdayEnd(LocalTime.parse(row.getCell(13).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));
				space.setHolidayStart(LocalTime.parse(row.getCell(14).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));
				space.setHolidayEnd(LocalTime.parse(row.getCell(15).getStringCellValue(), DateTimeFormatter.ofPattern("HH:mm")));


				spaceRepository.save(space);
//				System.out.println(space.toString());
			}
		}

		workbook.close();
		inputStream.close();
	}
}
