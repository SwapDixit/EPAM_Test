package com.testscripts;

import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.starwars.pages.HomePage;

public class HomePageTest extends TestBase {
	HomePage homePage;

	public HomePageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException, FileNotFoundException {
		initialization();
		homePage = new HomePage();
		if (homePage.iframe != null) {
			driver.switchTo().frame(homePage.google_ads_iframe);
			homePage.closeOverLayBtn.click();
			driver.switchTo().parentFrame();
		}

	}

	@Test(description = "Verify homePage data and Title is displayed")
	public void verifyHomePageTitleTest() throws InterruptedException {
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertTrue(homePage.logInBtn.isDisplayed());
		System.out.println(homePageTitle);
		Assert.assertEquals(homePageTitle, "StarWars.com | The Official Star Wars Website",
				"Home page title not matched");

	}

	@Test(description = "Verify multiple slides are displayed on page")
	public void verifySlideDisplayed() throws InterruptedException {
		int slidecount = homePage.slide.size();
		Assert.assertTrue(slidecount > 1, "Slide is not displayed on Homepage");
	}

	@Test(description = "Verify User can navigate to Next and Previous slides")
	public void verifySlideNextPrev() throws InterruptedException {
		int slidecount = homePage.slide.size();
		Assert.assertTrue(slidecount > 1, "Slide is not displayed on Homepage");
		for (int i = 0; i < slidecount; i++) {
			homePage.slideNext.click();
			Assert.assertTrue(homePage.slideImg.isEnabled(), "Slide is not enable image");

		}
		for (int i = 0; i < slidecount; i++) {
			homePage.slidePrev.click();
			Assert.assertTrue(homePage.slideImg.isEnabled(), "Slide is not enable image");
		}

	}

	@Test(description = "Verify User can search from HomePage")
	public void verifySearchResultDisplayed() {
		Assert.assertTrue(homePage.searchInput.isDisplayed() && homePage.searchInput.isEnabled(),
				"Search input is not Displayed or Enable ");
		homePage.searchInput.sendKeys("Star");
		homePage.searchInput.sendKeys(Keys.RETURN);
		Assert.assertTrue(homePage.searchResultPage.isDisplayed());

	}

	@Test(description = "Verify user can login to account with valid username and password")
	public void verifyLoginWithValidData() throws InterruptedException {
		homePage.loginBtn.click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.xpath("//body[@class='mtt']/div/iframe[1]")));
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@name='disneyid-iframe']")));
		homePage.email.sendKeys("Test2337@gmail.com");
		homePage.password.sendKeys("Test2337@1");
		homePage.signInBtn.click();
		driver.switchTo().parentFrame();
		driver.switchTo().parentFrame();
		Thread.sleep(3000);
		Assert.assertTrue(homePage.verifylogin("sws3567884713").isDisplayed(), "Login Failure");
	}

	@Test(description = "Verify user won't get login to account with invalid username and password")
	public void verifyInvalidLogin() throws InterruptedException {
		homePage.loginBtn.click();
		Thread.sleep(3000);
		driver.switchTo().frame(driver.findElement(By.xpath("//body[@class='mtt']/div/iframe[1]")));
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@name='disneyid-iframe']")));
		homePage.email.sendKeys("Test@gmail.com");
		homePage.password.sendKeys("Test123");
		homePage.signInBtn.click();

		Thread.sleep(3000);
		Assert.assertTrue(homePage.invalidLogin.isDisplayed(), "Invalid User Login");
	}

	@Test(description = "Verify user can logout from account")
	public void verifyLogout() throws InterruptedException {
		verifyLoginWithValidData();

		homePage.logOut.click();

		Thread.sleep(1000);
		Assert.assertTrue(homePage.loginBtn.isDisplayed(), "LogOut Failure");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
