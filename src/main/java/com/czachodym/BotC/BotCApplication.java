package com.czachodym.BotC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class BotCApplication {
	public static void main(String[] args) {
		SpringApplication.run(BotCApplication.class, args);
	}
//	public static void main(String[] args) {
//		// Load file from resources
//		InputStream inputStream = BotCApplication.class.getResourceAsStream("/tuples.csv");
//
//		if (inputStream == null) {
//			System.out.println("File not found in resources!");
//			return;
//		}
//
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
//			String line;
//
//			while ((line = br.readLine()) != null) {
//				// Remove spaces if any
//				line = line.trim();
//
//				// Split by "),"
//				String[] tuples = line.split("\\),");
//
//				int id = 1;
//				int lastScript = 0;
//				int order = 1;
//
//				for (String tuple : tuples) {
//					// Clean brackets and spaces
//					tuple = tuple.replace("(", "").replace(")", "").trim();
//
//					// Split into two numbers
//					String[] parts = tuple.split(",");
//					if (parts.length == 2) {
//						int script_id = Integer.parseInt(parts[0].trim());
//						int character_id = Integer.parseInt(parts[1].trim());
//						if(lastScript != script_id){
//							order = 1;
//						}
//
//						//script_script_characters
////						System.out.print("(" + script_id + ", " + id + "), ");
//
//						//script_character
//						System.out.print("(" + id + ", " + order + ", " + character_id + "), ");
//
//						id += 1;
//						order += 1;
//						lastScript = script_id;
//					}
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
