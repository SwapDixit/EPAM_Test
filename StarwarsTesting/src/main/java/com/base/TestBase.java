package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

//import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;


	public TestBase() {
		try {
			prop = new Properties();
			File fil = new File("Config/config.properties");
			System.out.println(fil.getAbsolutePath().toString());
			prop.load(new FileInputStream(fil.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() throws FileNotFoundException {
		String browserName = prop.getProperty("browser");
		File DriverPath = new File(prop.getProperty("browserdriverpath"));

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", DriverPath.getAbsolutePath().toString());
			driver = new ChromeDriver();
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", DriverPath.getAbsolutePath().toString());
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));

	}

}
