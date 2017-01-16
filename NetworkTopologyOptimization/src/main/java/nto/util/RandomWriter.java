package nto.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomWriter {

	public void write(String filename, int number) {
		File out = new File(filename);
		int n = number;
		try (FileWriter fw = new FileWriter(out); BufferedWriter writer = new BufferedWriter(fw);) {
			Random random = new Random();
			while (n > 0) {
				writer.write(String.format("%s;%s", random.nextFloat() * 100, random.nextFloat() * 100));
				writer.newLine();
				n--;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
