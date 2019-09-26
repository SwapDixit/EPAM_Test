package com.starwars.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.base.TestBase;
public class VideoPage extends TestBase {
	// Initializing the Page Objects:
		public VideoPage() {
			PageFactory.initElements(driver, this);
		}
		
		@FindBy(xpath = "//input[@class='large button']")
		public WebElement searchBtn;
		
		@FindBy(xpath = "//p[contains(.,'Video')]")
		public WebElement video;
		
		@FindBy(xpath = "//div[@class='suggestionNoResults']")
		public WebElement noResultText;
		
		@FindBy(xpath = "//section[@class='module search']//input[@name ='q']")
		public WebElement searchInput;
		
		@FindBy(xpath = "//div[@class='show_more_container']")
		public WebElement searchShowMore;
		
		
		
		//
}
