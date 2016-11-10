package forecast;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utilities.ExcelUtils;
import utilities.PropertiesFiles_Source;
import utilities.Property_Utility;

public class Forecast_submission {
	
	public static WebDriver driver;
	
	String code = null;
	private static Property_Utility loginUtil = new Property_Utility(PropertiesFiles_Source.loginpage_path);
	
	@BeforeTest
	public void essentials()
	{
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
  @Test
  public void forecast() throws Exception {
	 String path = PropertiesFiles_Source.testdata_path+PropertiesFiles_Source.forecastData_excel;
		ExcelUtils.setExcelFile(path,"Login");
	
		//driver = new FirefoxDriver();
		driver.get(loginUtil.getEnvironmentProperty("loginURL"));
		driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("username"))).sendKeys(ExcelUtils.getExcelData("username", "forecast_submission"));
		driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("password"))).sendKeys(ExcelUtils.getExcelData("password", "forecast_submission"));
		driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("submit"))).click();
	
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUtil.getEnvironmentProperty("plus"))));
		driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("plus"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginUtil.getEnvironmentProperty("forecasts"))));
		driver.findElement(By.linkText(loginUtil.getEnvironmentProperty("forecasts"))).click();
		Select month = new Select(driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("rangestart_month"))));
		month.selectByVisibleText(ExcelUtils.getExcelData("month", "forecast_submission"));
		Select year = new Select(driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("rangestart_year"))));
		year.selectByVisibleText(ExcelUtils.getExcelData("year", "forecast_submission"));
		Select period = new Select(driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("rangelength"))));
		period.selectByVisibleText(ExcelUtils.getExcelData("period", "forecast_submission"));
		driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("submit_forecast"))).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
  }
  @AfterTest
  public void afterMethod()
  {
	  driver.quit();
  }
	
}
