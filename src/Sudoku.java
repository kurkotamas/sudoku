import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
	Szam sudoku[][];
	boolean ved[];
	int n = 9;
	
	public Sudoku(String filename) throws IOException{
		sudoku = new Szam[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sudoku[i][j] = new Szam(0, false);
			}
		}
		ved = new boolean[89];
		for (int i = 0; i < 88; i++) {
			ved[i] = false;
		}
		
		String file = new File(filename).getAbsolutePath();
		BufferedReader br = new BufferedReader(new FileReader(file));
		int digit;
		int i = 0;
		int j = 0;
		while((digit = br.read()) != -1) {
			digit = Character.getNumericValue(digit);
			if(digit != -1) {
				if(digit != 0) {
					if(digit < 1 || digit > n) {
						System.out.println("Hiba! " + digit + "nem adhato hozza.");
					}
					else {
						sudoku[i][j].ertek = digit;
						sudoku[i][j].vedett = true;
					}
				}
				j++;
				if(j == 9) {
					j = 0;
					i++;
					if(i == 9) {
						break;
					}
				}
			}
		}
		
	}
	public void kiir() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(sudoku[i][j].ertek + " ");
				if((j + 1) % 3 == 0) {
					System.out.print(" ");
				}
			}
			System.out.println();
			if((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
	}
	public void hozzaad(int i, int j, int szam) {
		if(szam < 1 || szam > n) {
			System.out.println("Hiba! " + szam + "nem adhato hozza.");
		}
		else {
			sudoku[i][j].ertek = szam;
			sudoku[i][j].vedett = true;
		}
	}
	public boolean ellenoriz(int sor, int oszlop, int x) {
		if(sudoku[sor][oszlop].vedett) {
			
			return false;
		}
		for (int i = 0; i < n; i++) {
			if(sudoku[sor][i].ertek == x) {
				return false;
			}
		}
		for (int i = 0; i < n; i++) {
			if(sudoku[i][oszlop].ertek == x) {
				return false;
			}
		}
		for (int i = sor - sor % 3; i < sor - sor % 3 + 3; i++) {
			for (int j = oszlop - oszlop % 3; j < oszlop - oszlop % 3 + 3; j++) {
				if(sudoku[i][j].ertek == x) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void kitolt(int sor, int oszlop) throws InterruptedException {
		for(int i = 1; i <= n; i++) {
			//ha nem vedett es 0
			if(sudoku[sor][oszlop].ertek == 0 && !sudoku[sor][oszlop].vedett) {
				if(ellenoriz(sor, oszlop, i)) {
					sudoku[sor][oszlop].ertek = i;					
					if(sor == oszlop && sor == n - 1) {
						System.out.println("Kesz!");
						kiir();
						Thread.sleep(1000000);
					}
					else {
						if(oszlop != n - 1) {
							kitolt(sor, oszlop + 1);
						}
						else {
							kitolt(sor + 1, 0);
						}
					}
				}
			}
			//ha vedett vagy nem 0
			else {
				//ha vedett
				if(sudoku[sor][oszlop].vedett) {
					//elore
					if(!ved[sor * 10 + oszlop] || (sor == oszlop && sor == 0)) {
						ved[sor * 10 + oszlop] = true;
						if(sor == oszlop && sor == n - 1) {
							System.out.println("Kesz!");
							kiir();
							System.exit(0);
						}
						else {
						if(oszlop != n - 1) {
							kitolt(sor, oszlop + 1);
						}
						else {
							kitolt(sor + 1, 0);
						}
						}
					}
					//vissza
					//ha a ved != 0, akkor kilepik az alprogrambol, kulonben a kovetkezo erteket talalgatja
					else {
						ved[sor * 10 + oszlop] = false;
						return;
					}
				}
				//ha nem vedett
				else {
					if(sor == oszlop && sor == n - 1) {
						System.out.println("Kesz!");
					}
					if(oszlop != n - 1) {
						kitolt(sor, oszlop + 1);
					}
					else {
						kitolt(sor + 1, 0);
					}
				}
			}
			if(!sudoku[sor][oszlop].vedett) {
				sudoku[sor][oszlop].ertek = 0;
			}

		}
	}
}
