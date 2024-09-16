package com.mehmetc.utility;

import java.util.Scanner;

public class ConsoleTextUtils {
	// ANSI renk kodları
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_ORANGE = "\u001B[38;5;214m";
	public static final String ANSI_GREEN = "\033[0;32m";
	
	// Başarılı mesajları yeşil renk ile yazdırır
	public static void printSuccessMessage(String message) {
		System.out.println(ANSI_GREEN + message + ANSI_RESET);
	}
	
	// Hata mesajlarını kırmızı renk ile yazdırır
	public static void printErrorMessage(String message) {
		System.out.println(ANSI_RED + message + ANSI_RESET);
	}
	
	// Başlık yazdırır, etrafını yıldızlarla sarar
	public static void printTitle(int yildizSayisi, String metin) {
		String yildizSatiri = ANSI_RED + "*".repeat(yildizSayisi) + ANSI_RESET;
		int boslukUzunlugu = yildizSayisi - 2;
		int solBosluk = (boslukUzunlugu - metin.length()) / 2;
		int sagBosluk = boslukUzunlugu - solBosluk - metin.length();
		String ortaliMetin = "*%s%s%s*".formatted(
				" ".repeat(solBosluk),
				ANSI_ORANGE + metin + ANSI_RESET,
				" ".repeat(sagBosluk)
		);
		System.out.println(yildizSatiri);
		System.out.println(ANSI_RED + "*" + ANSI_RESET + ortaliMetin.substring(1, ortaliMetin.length() - 1) + ANSI_RED + "*" + ANSI_RESET);
		System.out.println(yildizSatiri);
	}
	
	// Varsayılan yıldız sayısı ile başlık yazdırır
	public static void printTitle(String metin) {
		printTitle(30, metin);
	}
	
	// Menü seçeneklerini yıldızlı bir şekilde yazdırır
	public static void printMenuOptions(int yildizSayisi, String... secenekler) {
		String yildizSatiri = ANSI_RED + "*".repeat(yildizSayisi) + ANSI_RESET;
		System.out.println(yildizSatiri);
		
		for (int i = 0; i < secenekler.length; i++) {
			String secenek = "%d- %s".formatted(i + 1, secenekler[i]);
			int boslukUzunlugu = yildizSayisi - 2;
			
			// Bosluk uzunluğunun negatif olmamasını sağlıyoruz
			int remainingSpace = boslukUzunlugu - secenek.length();
			if (remainingSpace < 0) remainingSpace = 0; // Negatiften kaçınmak için kontrol
			
			String secenekSatiri = "*%s%s*".formatted(
					secenek,
					" ".repeat(remainingSpace)
			);
			System.out.println(ANSI_RED + "*" + ANSI_RESET + secenekSatiri.substring(1, secenekSatiri.length() - 1) + ANSI_RED + "*" + ANSI_RESET);
		}
		System.out.println(yildizSatiri);
	}
	
	// Varsayılan yıldız sayısı ile menü seçeneklerini yazdırır
	public static void printMenuOptions(String... secenekler) {
		printMenuOptions(30, secenekler);
	}
	
	// Kullanıcıdan tam sayı girişi alır
	public static int getIntUserInput(String soru) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(soru + " ");
		
		while (true) {
			if (scanner.hasNextInt()) {
				return scanner.nextInt();
			} else {
				printErrorMessage("Geçersiz giriş! Lütfen bir sayı giriniz.");
				scanner.next(); // Geçersiz girişi temizle
				System.out.print(soru + " ");
			}
		}
	}
	
	// Kullanıcıdan string girişi alır
	public static String getStringUserInput(String soru) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(soru + " ");
		return scanner.nextLine();
	}
	
	public static void printResultMessage(boolean success, String successMessage, String errorMessage) {
		if (success) {
			printSuccessMessage(successMessage);
		} else {
			printErrorMessage(errorMessage);
		}
	}
}