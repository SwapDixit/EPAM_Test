package com.testscripts;

import java.io.FileNotFoundException;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.starwars.pages.HomePage;
import com.starwars.pages.VideoPage;

public class VideoPageTest extends TestBase {
	HomePage homePage;
	VideoPage videoPage;

	public VideoPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException, FileNotFoundException {
		initialization();
		homePage = new HomePage();
		videoPage = new VideoPage();
		if (homePage.iframe != null) {
			driver.switchTo().frame(homePage.google_ads_iframe);
			homePage.closeOverLayBtn.click();
			driver.switchTo().parentFrame();
		}
		videoPage.video.click();
		Thread.sleep(1000);

	}

	@Test(description = "Verify User can search from VideoPage")
	public void verifySearch() throws InterruptedException {
		Assert.assertTrue(videoPage.searchInput.isDisplayed() && videoPage.searchInput.isEnabled(),
				"Search input is not Displayed or Enable ");
		videoPage.searchInput.sendKeys("Star");
		videoPage.searchInput.sendKeys(Keys.RETURN);
		Thread.sleep(2000);
		videoPage.searchShowMore.click();
		Assert.assertTrue(homePage.searchResultPage.isDisplayed());
	}

	@Test(description = "Verify No Result Found displayed if searched video is not found")
	public void verifyNoResultFoundSearch() throws InterruptedException {
		String input = "1234567";
		videoPage.searchInput.sendKeys(input);
		videoPage.searchBtn.click();
		Assert.assertTrue(videoPage.noResultText.isDisplayed(), "No result found not displayed");
		Assert.assertTrue(videoPage.noResultText.getText().contains(input));
	}

	@Test(description = "Verify No Result Found displayed if searched with special characters")
	public void verifyNoResultFoundDisplayedForSpecialChar() throws InterruptedException {
		String input = "!@!##@";
		videoPage.searchInput.sendKeys(input);
		videoPage.searchBtn.click();
		Assert.assertTrue(videoPage.noResultText.isDisplayed(), "No result found not displayed");
		Assert.assertTrue(videoPage.noResultText.getText().contains(input));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
