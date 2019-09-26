package com.testscripts;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.starwars.pages.FilmsPage;
import com.starwars.pages.HomePage;

public class FilmsPageTest extends TestBase {
	HomePage homePage;
	FilmsPage filmsPage;


	public FilmsPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException, FileNotFoundException {
		initialization();
		filmsPage = new FilmsPage();
		homePage = new HomePage();
		if (homePage.iframe != null) {
			driver.switchTo().frame(homePage.google_ads_iframe);
			homePage.closeOverLayBtn.click();
			driver.switchTo().parentFrame();
		}
		filmsPage.Films.click();

	}

	@Test(description = "Verify FilmPage is displayed")
	public void verifyFilmPageOpen() {
		String openurl = driver.getCurrentUrl();
		Assert.assertEquals(openurl, "https://www.starwars.com/films");
	}

	@Test(description = "Verify list of films are displayed in film dropdown")
	public void verifyListOfFilmsDisplayedInSelector() throws InterruptedException {
		filmsPage.categoryDropDown.click();
		Assert.assertTrue(filmsPage.films.size() > 1, "Films are not found");
		Thread.sleep(1000);
		for (WebElement iterable_element : filmsPage.films) {
			System.out.println(iterable_element.getText());
		}
	}

	@Test(description = "Verify list of films are present in film page")
	public void verifyFilmList() throws InterruptedException {
		for (WebElement iterable_element : filmsPage.filmsList) {
			System.out.println(iterable_element.getText());
			Assert.assertTrue(iterable_element.isEnabled() && iterable_element.isDisplayed(),
					"Film is not Enable - " + iterable_element.getText());
			Reporter.log(iterable_element.getText());
		}
	}

	@Test(description = "Verify selected film page get opened on selecting film from film dropdown")
	public void verifyFilmPageOpenAfterSelectingFilm() throws InterruptedException {

		for (int i = 1; i < filmsPage.films.size(); i++) {
			filmsPage.categoryDropDown.click();
			Thread.sleep(1000);

			Reporter.log(filmsPage.films.get(i).getText());
			filmsPage.films.get(i).click();
			Thread.sleep(3000);
			Assert.assertTrue(filmsPage.filmDesc.isDisplayed());

		}

	}

	@Test(description = "Verify IMDB page get loaded with selected film")
	public void verifyMovieOnIMDB() throws InterruptedException {

		filmsPage.categoryDropDown.click();
		Thread.sleep(1000);
		filmsPage.films.get(2).click();
		Thread.sleep(3000);

		String currentPageHandle = driver.getWindowHandle();

		ArrayList<String> tabHandles = new ArrayList<String>(driver.getWindowHandles());
		boolean myNewTabFound = false;
		String pageTitle = "Star Wars: Attack of the Clones | StarWars.com";
		if (driver.findElements(By.xpath("//a[contains(text(),'IMDB')]")).size() > 0) {
			filmsPage.imdbWatchOption.click();
			Thread.sleep(3000);
			for (String eachHandle : tabHandles) {
				driver.switchTo().window(eachHandle);
				System.out.println("Film Title " + driver.getTitle());

				if (driver.getTitle().equalsIgnoreCase(pageTitle)) {
					System.out.println("after title match");
					Reporter.log("IMDB page " + driver.getTitle());
					driver.switchTo().window(currentPageHandle);
					myNewTabFound = true;

				}
			}

			Assert.assertTrue(myNewTabFound, "Wrong IMdb Page loading");
		} else {
			Reporter.log("Film doesnot have the IMDB watch options available");
		}

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
