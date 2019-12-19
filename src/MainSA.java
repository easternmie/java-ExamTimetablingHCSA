
/**
 *
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

/**
 * @author Alun & Robbani
 *
 */
public class MainSA {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//DATA CAR F 92
		File f1 = new File("car-f-92.stu"); // Creation of File Descriptor for input file
		int jumlahexam = 543;
		int timeslot = 32;
		double students = 18419;
		
//		//DATA CAR S 91
//		File f1 = new File("car-s-91.stu"); // Creation of File Descriptor for input file
//		int jumlahexam = 682;
//		int timeslot = 35;
//		double students = 16925;
//		
//		//DATA TRE S 92
//		File f1 = new File("tre-s-92.stu"); // Creation of File Descriptor for input file
//		int jumlahexam = 261;
//		int timeslot = 23;
//		double students = 4360;

		int linecount = 0; // Intializing linecount as zero

		FileReader fr = new FileReader(f1); // Creation of File Reader object

		BufferedReader br = new BufferedReader(fr); // Creation of File Reader object

		String s;

		while ((s = br.readLine()) != null) // Reading Content from the file line by line
		{
			linecount++; // For each line increment linecount by one
		}

		String text = "";
		int[][] dataexam = new int[linecount][10];
		int[][] confmatrix = new int[jumlahexam][jumlahexam];
		int lineNumber;
		try {
			//Data CAR F 92
			FileReader readfile = new FileReader("car-f-92.stu");
			
//			//Data CAR S 91
//			FileReader readfile = new FileReader("car-s-91.stu");
//			
//			//Data TRE S 92
//			FileReader readfile = new FileReader("tre-s-92.stu");
			BufferedReader readbuffer = new BufferedReader(readfile);
			for (lineNumber = 1; lineNumber <= linecount; lineNumber++) {
				if (lineNumber >= 1) {
					text = readbuffer.readLine();
					String[] parts = text.split(" ");
					for (int i = 0; i < parts.length; i++) {

						dataexam[lineNumber - 1][i] = Integer.parseInt(parts[i]);

					}
					if (parts.length > 1) {
						for (int i = 0; i < parts.length - 1; i++) {
							for (int j = i + 1; j < parts.length; j++) {
								confmatrix[dataexam[lineNumber - 1][i] - 1][dataexam[lineNumber - 1][j] - 1]++;
								confmatrix[dataexam[lineNumber - 1][j] - 1][dataexam[lineNumber - 1][i] - 1]++;
							}

						}
					}

				} else {
					lineNumber = lineNumber + linecount;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		fr.close();
		int[][] confmatrix2 = new int[2][jumlahexam];
		int[][] confmatrix3 = new int[2][jumlahexam];
		for (int i = 0; i < jumlahexam; i++) {
			for (int j = 0; j < jumlahexam; j++) {
				if (confmatrix[i][j] != 0) {
					confmatrix2[0][i]++;

					confmatrix3[0][i]++;
				}
			}

		}

		for (int j = 0; j < jumlahexam; j++) {
			confmatrix2[1][j] = j + 1;
		}

		Arrays.sort(confmatrix3[0]);

		for (int i = 0; i < jumlahexam / 2; i++) {
			int temp = confmatrix3[0][i];
			confmatrix3[0][i] = confmatrix3[0][jumlahexam - i - 1];
			confmatrix3[0][jumlahexam - i - 1] = temp;
		}

		for (int i = 0; i < jumlahexam; i++) {
			for (int j = 0; j < jumlahexam; j++) {
				if (confmatrix3[0][i] == confmatrix2[0][j] && confmatrix2[1][j] != -1) {
					confmatrix3[1][i] = confmatrix2[1][j];
					confmatrix2[1][j] = -1;
					j = j + jumlahexam;
				}
			}
		}

		int[][] plotexam = new int[jumlahexam][2];

//       for(int i =0; i<32; i++) {
//    	   plotexam[0][i]= confmatrix3[1][i];
//       }

		for (int i = 0; i < jumlahexam; i++) {

			plotexam[i][0] = confmatrix3[1][i];
			for (int j = 0; j < timeslot; j++) {
				if (i == 0) {
					plotexam[i][1] = 0;
				}
				for (int k = 0; k < i; k++) {
					if (plotexam[k][1] == j) {
						if (confmatrix[plotexam[i][0] - 1][plotexam[k][0] - 1] != 0) {
							k = k + i;
						}

					}
					if (k == i - 1) {
						plotexam[i][1] = j;

						j = j + timeslot;

					}
				}

			}

		}

		int[][] hasilexam = new int[2][jumlahexam];
		int[][] temphasilexam = new int[2][jumlahexam];

		for (int i = 0; i < jumlahexam; i++) {
			hasilexam[0][i] = plotexam[i][0];
			hasilexam[1][i] = plotexam[i][1];

		}

		Arrays.sort(hasilexam[0]);

		for (int i = 0; i < jumlahexam; i++) {
			for (int j = 0; j < jumlahexam; j++) {
				if (hasilexam[0][i] == plotexam[j][0]) {
					hasilexam[1][i] = plotexam[j][1];
					j = j + jumlahexam;
				}
			}
		}

		for (int j = 0; j < jumlahexam; j++) {
			temphasilexam[0][j] = hasilexam[0][j];
			temphasilexam[1][j] = hasilexam[1][j];
		}

		int fitnes = 0;
		int cekvalue = 0;
		int fitnesbaru = 0;
		int fitnestmp = 0;

		for (int i = 0; i < dataexam.length; i++) {
			if (dataexam[i][1] != 0) {
				for (int j = 0; j < 10; j++) {
					for (int k = j + 1; k < 10; k++) {
						cekvalue = (Math.abs(hasilexam[1][dataexam[i][j] - 1] - hasilexam[1][dataexam[i][k] - 1])) - 1;

						if (cekvalue == 0) {
							fitnes = fitnes + 16;
						} else if (cekvalue == 1) {
							fitnes = fitnes + 8;
						} else if (cekvalue == 2) {
							fitnes = fitnes + 4;
						} else if (cekvalue == 3) {
							fitnes = fitnes + 2;
						} else if (cekvalue == 4) {
							fitnes = fitnes + 1;
						}

						if (dataexam[i][k + 1] == 0) {
							k = k + 10;
						}
					}
					if (dataexam[i][j + 2] == 0) {
						j = j + 10;
					}
				}
			}

		}

		Random random = new Random();
		int maxiter = 300000;
		int random1 = 0;
		int random2 = 0;
		int tmp = 0;
		int delta = 0;
		Double suhu = 4000.0;
		double cRate = 0.00002;
		Double[] SA = new Double[maxiter];
		for (int i = 0; i < maxiter; i++) {

			random1 = random.nextInt(jumlahexam - 1);
			random2 = random.nextInt(jumlahexam - 1);
			if (random1 == random2) {
				random2 = random2 + 1;
			}

			tmp = temphasilexam[1][random1];
			temphasilexam[1][random1] = temphasilexam[1][random2];
			temphasilexam[1][random2] = tmp;

			for (int j = 0; j < jumlahexam; j++) {
				if (temphasilexam[1][j] == temphasilexam[1][random1]) {
					if (confmatrix[temphasilexam[0][j] - 1][temphasilexam[0][random1] - 1] != 0) {

						fitnestmp = 99999999;
						j = j + (jumlahexam + 1);
					}
				}
			}

			for (int j = 0; j < jumlahexam; j++) {
				if (temphasilexam[1][j] == temphasilexam[1][random2]) {
					if (confmatrix[temphasilexam[0][j] - 1][temphasilexam[0][random2] - 1] != 0) {
						fitnestmp = 99999999;
						j = j + (jumlahexam + 1);
					}
				}
			}

			if (fitnestmp == 0) {

				for (int k = 0; k < dataexam.length; k++) {
					if (dataexam[k][1] != 0) {
						for (int l = 0; l < 10; l++) {
							for (int m = l + 1; m < 10; m++) {
								cekvalue = (Math.abs(
										temphasilexam[1][dataexam[k][l] - 1] - temphasilexam[1][dataexam[k][m] - 1]))
										- 1;

								if (cekvalue == 0) {
									fitnestmp = fitnestmp + 16;
								} else if (cekvalue == 1) {
									fitnestmp = fitnestmp + 8;
								} else if (cekvalue == 2) {
									fitnestmp = fitnestmp + 4;
								} else if (cekvalue == 3) {
									fitnestmp = fitnestmp + 2;
								} else if (cekvalue == 4) {
									fitnestmp = fitnestmp + 1;
								}

								if (dataexam[k][m + 1] == 0) {
									m = m + 10;
								}
							}
							if (dataexam[k][l + 2] == 0) {
								l = l + 10;
							}
						}
					}

				}
			}
//    		 System.out.println(fitnestmp);

			if (fitnestmp <= fitnes) {
				fitnes = fitnestmp;
				for (int j = 0; j < jumlahexam; j++) {
					hasilexam[1][j] = temphasilexam[1][j];
				}
				SA[i] = fitnes / students;
			}

			else {
				if (fitnestmp != 99999999) {

					delta = fitnestmp - fitnes;
					Double delta2 = Double.valueOf(delta);

					if ((delta2) < suhu) {
						fitnes = fitnestmp;
						for (int j = 0; j < jumlahexam; j++) {
							hasilexam[1][j] = temphasilexam[1][j];
						}
						SA[i] = fitnes / students;

					}
				}
				tmp = temphasilexam[1][random1];
				temphasilexam[1][random1] = temphasilexam[1][random2];
				temphasilexam[1][random2] = tmp;
				fitnestmp = 0;
				SA[i] = fitnes / students;
			}
			suhu *= 1 - cRate;

		}
		
		System.out.println("PENALTY SIMULATED ANNEALING");
		for (int j = 0; j < maxiter; j++) {
			System.out.println(SA[j]);
		}

	}

}