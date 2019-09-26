package com.starwars.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class HomePage extends TestBase {

	@FindBy(xpath = "//*[@id='overlaybg']/*[contains(@class,'close')]")
	public WebElement closeOverLayBtn;

	@FindBy(xpath = "//div[@id='nav-utility']//div[@class='disid-login log-in-out'][contains(text(),'Log In')]")
	public WebElement logInBtn;

	@FindBy(xpath = "//div[@id='nav-utility']//div[@class='disid-signup log-in-out'][contains(text(),'Sign Up')]")
	public WebElement signUpBtn;

	@FindBy(xpath = "//body//iframe")
	public WebElement iframe;

	@FindBy(css = ".bound li")
	public List<WebElement> slide;

	@FindBy(xpath = "//*[@class='bound']//li[contains(@style,'visibility: visible')]//img")
	public WebElement slideImg;

	@FindBy(xpath = "//span[contains(@class,'next')]")
	public WebElement slideNext;

	@FindBy(xpath = "//span[contains(@class,'prev')]")
	public WebElement slidePrev;

	@FindBy(xpath = "//input[@id='nav-search-input']")
	public WebElement searchInput;

	@FindBy(xpath = "//button[@id='nav-search-icon']")
	public WebElement searchIcon;

	@FindBy(xpath = "//div[@id='nav-utility']//ol[@id='nav-ac']/li")
	public List<WebElement> searchResult;

	@FindBy(xpath = "//h2[contains(text(),'results for')]")
	public WebElement searchResultPage;

	@FindBy(xpath = "//*[@id='nav-utility']//*[@class='logged-out-container']/*[contains(text(),'Log In')]")
	public WebElement loginBtn;

	@FindBy(xpath = "//input[@type='email']")
	public WebElement email;

	@FindBy(xpath = "//input[@type='password']")
	public WebElement password;

	@FindBy(xpath = "//button[contains(.,'Sign In')]")
	public WebElement signInBtn;

	public WebElement verifylogin(String uname) {
		return driver.findElement(By.xpath("//div[@id='nav-utility']//span[contains(.,'" + uname + "')]"));
	}
	
	@FindBy(xpath = "//*[contains(@name,'google_ads_iframe')]")
	public WebElement google_ads_iframe;
	
	@FindBy(xpath = "//div[@id='did-ui-view']//div[@class='ng-scope']")
	public WebElement invalidLogin;

	@FindBy(xpath = "//*[@id='nav-utility']//*[@class='disid-flyout']/*[contains(text(),'Log Out')]")
	public WebElement logOut;
	//
	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String verifyHomePageTitle() {
		return driver.getTitle();

	}

	/*
	 * public void clickOnNewContactLink(){ Actions action = new Actions(driver);
	 * action.moveToElement(contactsLink).build().perform(); newContactLink.click();
	 * 
	 * }
	 */

}
