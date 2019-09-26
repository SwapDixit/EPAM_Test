package com.starwars.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;

public class FilmsPage extends TestBase {
	// Initializing the Page Objects:
	public FilmsPage() {
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//p[contains(.,'Films')]")
	public WebElement Films;
	
	@FindBy(xpath = "//div[@class='category-container']")
	public WebElement categoryDropDown;
	
	@FindBy(xpath = "//ul[@class='drop-container']//li")
	public List<WebElement> films;
	
	@FindBy(xpath = "//div[@class='description-container films-content']")
	public WebElement filmDesc;
	
	@FindBy(xpath = "//*[@id='burger']//section[@data-module='display']//span[@class='long-title']")
	public List<WebElement> filmsList;
	
	@FindBy(xpath = "//a[contains(text(),'IMDB')]")
	public WebElement imdbWatchOption;
	

	
}
