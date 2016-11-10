package forecast;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class DAT_1 {

	public static WebDriver driver;
	String code = null;
	private static Property_Utility loginUtil = new Property_Utility(PropertiesFiles_Source.loginpage_path);
	private static Property_Utility forecastUtil = new Property_Utility(PropertiesFiles_Source.Forecast_path);
	private static Property_Utility opporUtil = new Property_Utility(PropertiesFiles_Source.opportunity_path);
	
	@BeforeTest
	public void essentials()
	{
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
	
	@Test
	public void opportunity_creation() throws Exception
	{
		    String path = PropertiesFiles_Source.testdata_path+PropertiesFiles_Source.forecastData_excel;
			ExcelUtils.setExcelFile(path,"Opportunity");
			//driver = new FirefoxDriver();
			driver.get(loginUtil.getEnvironmentProperty("loginURL"));
			driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("username"))).sendKeys(ExcelUtils.getExcelData("username", "CreateOpportunity"));
			driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("password"))).sendKeys(ExcelUtils.getExcelData("password", "CreateOpportunity"));
			driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("submit"))).click();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUtil.getEnvironmentProperty("plus"))));
			driver.findElement(By.xpath(loginUtil.getEnvironmentProperty("plus"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginUtil.getEnvironmentProperty("forecasts"))));
			driver.findElement(By.linkText(loginUtil.getEnvironmentProperty("forecasts"))).click();
			
			driver.findElement(By.xpath(forecastUtil.getEnvironmentProperty("opportunitiesTab"))).click();
			driver.findElement(By.xpath(forecastUtil.getEnvironmentProperty("createOpportunity"))).click();
			driver.findElement(By.id(opporUtil.getEnvironmentProperty("viewName"))).sendKeys(ExcelUtils.getExcelData("viewname", "CreateOpportunity"));
			driver.findElement(By.id(opporUtil.getEnvironmentProperty("viewUniqueName"))).sendKeys(ExcelUtils.getExcelData("viewuniquename", "CreateOpportunity"));
			String owner = ExcelUtils.getExcelData("owner", "CreateOpportunity");
			if(owner.equalsIgnoreCase("My"))
				driver.findElement(By.id(opporUtil.getEnvironmentProperty("myOpportunities"))).click();
			else
				driver.findElement(By.id(opporUtil.getEnvironmentProperty("allOpportunities"))).click();
			Select availableFields = new Select(driver.findElement(By.id(opporUtil.getEnvironmentProperty("availableFields"))));
			String required = ExcelUtils.getExcelData("requiredfields","CreateOpportunity");
			String requiredFields[] = required.split(":");
			for(int i=0;i<requiredFields.length;i++)
			{
				availableFields.selectByVisibleText(requiredFields[i]);
				driver.findElement(By.xpath(opporUtil.getEnvironmentProperty("addArrow"))).click();
			}
			String visibility = ExcelUtils.getExcelData("visibility", "CreateOpportunity");
			if(visibility.equalsIgnoreCase("all"))
				driver.findElement(By.xpath(opporUtil.getEnvironmentProperty("visibleToAll"))).click();
			driver.findElement(By.xpath(opporUtil.getEnvironmentProperty("save"))).click();
	}

	  @AfterTest
	  public void afterMethod()
	  {
		  driver.quit();
	  }

}
