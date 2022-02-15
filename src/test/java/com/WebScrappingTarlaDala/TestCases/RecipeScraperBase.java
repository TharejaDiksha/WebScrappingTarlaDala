package com.WebScrappingTarlaDala.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.CellBase;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xml.security.keys.content.KeyValue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RecipeScraperBase {
	
	//static WebDriver driver;
	WebDriver driver;
	static String baseUrl = "https://www.tarladalal.com/";
	int recipeCount = 0;
	char startPage = 'A';
	int firstPageIndex = 1;
	int totalAlphabets = 26;
	String beginswith = "beginswith=";
	String pageindex = "&pageindex=";
	String recipeA2ZUrl = "/RecipeAtoZ.aspx?";
	public String ExcelFileName = "";
	int rowNumber = 1;

	@BeforeTest
	public void Setup() throws InterruptedException
	{
		createDriver();
	}
	
	public void createDriver()
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		//options.setPageLoadStrategy(PageLoadStrategy.NONE);
		driver = new ChromeDriver(options);
		//driver = new ChromeDriver();
		driver.manage().window().maximize();
		//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void afterClass()
	{
		driver.close();
	}


	
	public void Scrape(int startIndex, int endIndex)
	{
		try {
			
			Navigate(baseUrl);
			

			//Navigate all recipe pages for a range of indexes/alphabets
			NavigateAToZPages(startIndex, endIndex);
		}
		catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		

	}
	
	

	private void Navigate(String url) throws InterruptedException
	{
		int sleepTimeMs = 500;
		try {
			if(driver == null)
			{
				System.out.println("driver is null");
				Setup();
				return;
			}
			driver.get(url);
		}
		catch(TimeoutException timeout)
		{
			System.out.println("Timeout exception occrued : " + timeout.getMessage());
			Thread.sleep(sleepTimeMs);
			driver.navigate().refresh();
			Thread.sleep(sleepTimeMs);
			driver.get(url);
		}
		catch(Exception e)
		{
			System.out.println("exception occrued : " + e.getMessage());
		}
	}

	private void NavigateAToZPages(int startIndex, int endIndex) throws InterruptedException
	{    
		

		for(int i = startIndex ; i <= endIndex; i++)
		{
			List<String> recipeUrls = new ArrayList<String>();
			char pageAlphabet = (char)((int)startPage + i);
			String aToZurl = baseUrl + recipeA2ZUrl + beginswith + pageAlphabet + pageindex;
			
			// add all recipe urls in the list
			recipeUrls.addAll(GetMultiPageUrls(firstPageIndex, aToZurl ));
			
			System.out.println("Total Recipies on page " + String.valueOf(pageAlphabet) + " = " + String.valueOf(recipeUrls.size()));
			String fileName = "RecipeBook_New.xlsx";
			//get the recipe details from each recipe url	
			GetRecipeDetails(recipeUrls, ExcelFileName,String.valueOf(pageAlphabet));
			
			//recipeCount = 0;
			
		}

	}

	private List<String> GetMultiPageUrls(int pageIndex, String aToZurl) throws InterruptedException 
	{

		List<String> subUrls = new ArrayList<String>();
		int totalSubPages = 0;
		
		//Get the total number of sub-pages count
		if(pageIndex == firstPageIndex)
		{
			Navigate(aToZurl + pageIndex);
			totalSubPages = GetSubPagesCount();
			subUrls.addAll(GetRecipiesUrls());
		}
		
		// set the next page index (i.e. 2)
		int nextPageIndex = ++pageIndex;
		
		System.out.println("getting multi page recipe urls");

		//for(int index = ++nextPageIndex ; index <= totalSubPages; index++)//check1
		for(int index = nextPageIndex ; index <= totalSubPages; index++)//check1
		{
			Navigate(aToZurl + index);

			subUrls.addAll(GetRecipiesUrls());

		}

		return subUrls;

	}

	// Get Recipe details from the recipe page navigating to the urls
	
	private void GetRecipeDetails(List<String> recipeUrls, String fileName, String sheetName) throws InterruptedException {

		List<Recipe> recipeList = new ArrayList<Recipe>();
		int maxListSize = 10;
		

		for(int i = 0 ; i < recipeUrls.size(); i++)
		{
			
			String url = recipeUrls.get(i);

			try {
				
				Navigate(url);
				recipeCount++;
				
				Recipe recipe = new Recipe(recipeCount);
				recipe.setLinkToRecipe(url);
				recipe.setTitle(GetTitle());
				recipe.setCategory(GetCategory());
				recipe.setLinkToRecipe(url);
				recipe.setIngredients(GetIngredients());
				recipe.setRecipeSteps(GetMethod());
				recipe.setRecipeImageLink(GetRecipeImageLink());
				recipe.setNutrientValues(GetNutrientValues());
				
				recipeList.add(recipe);
				//Proceed to scrape
				//Get all recipe data and print
				//ConsoleLog(recipe);
			
				// flush the recipe list to the excel file once the size of the recipe list == maxListSize default set to 10
				// Clear the list for the next set of recipes
				if(recipeList.size() % maxListSize == 0)
				{
					//ConsoleLog(recipeList);
					System.out.println("Writing data to excel file...");
					ExcelWrite(recipeList,fileName,sheetName,rowNumber);
					rowNumber += maxListSize;
					recipeList.clear();
				}
				
				System.out.print("Recipe Number = " + String.valueOf(recipe.getId()) + "\n");
				System.out.print("Recipe Url = " + recipe.getLinkToRecipe() + "\n");
				
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
				Thread.sleep(500);
				//if (ex.equals("Timed out receiving message from renderer: 300.000"))
				//{
				//Navigate(url);
			}
						
			
			//System.out.print(recipeText);
			
			
			
		}
		
		if(recipeList.size() > 0)
		{
			// Write recipe data for the page to the file if any more left
			ExcelWrite(recipeList,fileName, sheetName,rowNumber);
		}


	}
	
	private void ConsoleLog(List<Recipe> recipes)
	{
		for(int i = 0 ; i < recipes.size(); i++)
		{
			Recipe recipe = recipes.get(i);
			System.out.print("Recipe Number = " + String.valueOf(recipe.getId()) + "\n");
			System.out.print("Recipe Url =" + recipe.getLinkToRecipe() + "\n");
			//System.out.println("Title = "+recipe.getTitle()+"\n");
			//System.out.print("Category = " + recipe.getCategory() + "\n");
			//System.out.print("NutrientValues = " + recipe.getNutrientValues() + "\n");
			
			//System.out.print("Ingredients = " + recipe.getIngredients()+ "\n");
			//System.out.print("Method = " + recipe.getRecipeSteps()+ "\n");
			//System.out.print("ImageLink = " + recipe.getRecipeImageLink()+ "\n");
		}
	}
	
	public String GetCategory()
	{
		String category = "";
		try {
			WebElement categoryRoot = driver.findElement(By.xpath("//div[@id='show_breadcrumb']"));
			List<WebElement> categories = categoryRoot.findElements(By.className("breadcrumb-link-wrap"));
			
			
			for(int i = 0 ; i < categories.size(); i++)
			{
				WebElement element = categories.get(i);
				if(!element.getText().toLowerCase().equals("home"))
					category = category + element.getText() + " ";
			}
			return category;
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Categories not found");
		}
		
		return category;
	}
	
	public String GetTitle()
	{
		String retValue = "Title not found";
		try {
			String title= driver.getTitle();
			return title;
		}
		catch(NoSuchElementException e)
		{
			System.out.println("title not found");
		}
		
		return retValue;
	}
	
	
	
	
	public String GetCooktime()
	{
		String retValue = "";
		try {
			WebElement cooktime = driver.findElement(By.cssSelector("#ctl00_cntrightpanel_pnlRecipeScale > section > p:nth-child(4) > time:nth-child(2)"));
			return cooktime.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("CookTime not found");
		}
		
		return retValue;
	}
	
	//Checking BakeTime for Recipe
	public String GetBaketime()
	{
		String retValue = "";
		try {
			WebElement cooktime = driver.findElement(By.cssSelector("#ctl00_cntrightpanel_pnlRecipeScale > section > p:nth-child(4) > time:nth-child(2)"));
			return cooktime.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("CookTime not found");
		}
		
		return retValue;
	}
	
	
	
	public String GetServings()
	{
		String retValue = "";
		try {
			WebElement servings = driver.findElement(By.cssSelector("#ctl00_cntrightpanel_lblServes"));
			return servings.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Servings not found");
		}
		
		return retValue;
	}
	
	public String GetNutrientValues()
	{
		String retValue = "Nutrition Info not found";
		try {

			boolean present = driver.findElement(By.xpath("//table[@id='rcpnutrients']")).isDisplayed();
			if(present) {
				WebElement NutrientValues = driver.findElement(By.xpath("//table[@id='rcpnutrients']"));
				//System.out.println(NutrientValues.getText());
				return NutrientValues.getText();
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("Nutrition Info not found");
			//e.printStackTrace();
		}
		
		return retValue;
	}
	
	public String GetRecipeImageLink()
	{
		String retValue ="Image link is not found";
		try {

			boolean present = driver.findElement(By.xpath("//img[@id='ctl00_cntrightpanel_imgRecipe']")).isDisplayed();
			if(present) {
				WebElement RecipeImageLink = driver.findElement(By.xpath("//img[@id='ctl00_cntrightpanel_imgRecipe']"));
				//System.out.println(RecipeImageLink.getAttribute("src"));
				return RecipeImageLink.getAttribute("src");
			}
		}catch (NoSuchElementException e) {
			System.out.println("Image link is not found");
			//e.printStackTrace();
		}
		
		return retValue;
	}
	
	
	
	
	
	
	public String GetIngredients()
	{
		String retValue = "";
		try {
			WebElement ingredients = driver.findElement(By.xpath("//*[@id=\"recipe_ingredients\"]"));
			return ingredients.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Ingredients not found");
		}
		
		return retValue;
	}
	
	public String GetInstructions()
	{
		String retValue = "";
		try {
			WebElement instructions = driver.findElement(By.xpath("//ol[@itemprop='recipeInstructions']"));
			return instructions.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("CookTime not found");
		}
		
		return retValue;
	}
	public String GetMethod()
	{
		String retValue = "";
		try {
			WebElement recipeMethod = driver.findElement(By.xpath("//*[@id=\"ctl00_cntrightpanel_pnlRcpMethod\"]"));
			return recipeMethod.getText();
		}
		catch(NoSuchElementException e)
		{
			System.out.println("CookTime not found");
		}
		
		return retValue;
	}

	
	private List<String> GetRecipiesUrls() {

		List<String> urls = new ArrayList<String>();


		try
		{
			List<WebElement> rcc_rcpcores = driver.findElements(By.cssSelector(".rcc_rcpcore"));

			for(int i = 0; i < rcc_rcpcores.size(); i++)
			{

				WebElement element = rcc_rcpcores.get(i);
				WebElement tagA = element.findElement(By.tagName("a"));
				//if(tagA != null)///check1 uncomment
				//	urls.add(tagA.getAttribute("href"));check1 uncomment
				if(tagA != null)  //check start
				{
					String url= tagA.getAttribute("href");
					if (url.contains("arrangements"))
						continue;
					
					urls.add(tagA.getAttribute("href"));
				}//check1 ends
			}

		}
		catch(Exception ex)
		{
			System.out.print("Error processing hrefs " + ex.getMessage());
		}

		return urls;

	}

	private int GetSubPagesCount() {
		List<WebElement> pagelinks = driver.findElements(By.cssSelector(".respglink"));
		int totalSubPages = 1;

		int totalElements = pagelinks.size(); 
		if(totalElements <= 1)
			return totalSubPages;
		String lastPage = pagelinks.get(totalElements-1).getText();

		totalSubPages = Integer.parseInt(lastPage); 

		return totalSubPages ;

	}
	
	
	public void ExcelWrite(List<Recipe> recipeList, String fileName, String sheetName, int rowNumber) {
		
		try {
			File file = new  File(fileName);
			sheetName = "Recipes";
			
			FileInputStream inputStream = null;
			XSSFWorkbook wb; 
			XSSFSheet sh = null;
			
			if(!file.exists())
			{
				wb = new XSSFWorkbook();
				
				//Create new sheet
				sh = wb.createSheet(sheetName);
				
				//Add header row
				sh.createRow(0).createCell(0).setCellValue("Id");	
				sh.createRow(0).createCell(1).setCellValue("Title");	
				sh.getRow(0).createCell(2).setCellValue("Category");
				sh.getRow(0).createCell(3).setCellValue("Ingredients");
				sh.getRow(0).createCell(4).setCellValue("RecipeSteps");
				sh.getRow(0).createCell(5).setCellValue("NutrientValues");
				sh.getRow(0).createCell(6).setCellValue("RecipeImageLink");
				sh.getRow(0).createCell(7).setCellValue("LinkToRecipe");
			}
			else
			{
				// open existing workbook
	
				inputStream = new FileInputStream(file);
		        wb =  new XSSFWorkbook(inputStream);
		        System.out.println("Active sheet Index = " + wb.getActiveSheetIndex());
		        sh = wb.getSheetAt(wb.getActiveSheetIndex());
		        /*
		        for(int retry = 0 ; retry < 5 ; retry++)
		        {
		        	//sh =  wb.getSheet(sheetName);
		        	sh = wb.getSheetAt(wb.getActiveSheetIndex());
		        	if(sh!=null)
		        		break;
		        	System.out.println("Sheet not found sheet name = " + sheetName);
		        	
		        	Thread.sleep(100 * (retry+1));
		        }*/
				
				if(sh == null)
				{
					System.out.println("Sheet not found sheet name = " + sheetName);
					
					return;
					//Create new sheet
					//sh = wb.createSheet(sheetName);
					
					//Add header row
					/*
					sh.createRow(0).createCell(0).setCellValue("Id");	
					sh.createRow(0).createCell(1).setCellValue("Title");	
					sh.getRow(0).createCell(2).setCellValue("Category");
					sh.getRow(0).createCell(3).setCellValue("Ingredients");
					sh.getRow(0).createCell(4).setCellValue("RecipeSteps");
					sh.getRow(0).createCell(5).setCellValue("NutrientValues");
					sh.getRow(0).createCell(6).setCellValue("RecipeImageLink");
					sh.getRow(0).createCell(7).setCellValue("LinkToRecipe");*/
				}
			}
			
			
					
			// create new rows for the list of recipes
			for(int i = 0 ; i< recipeList.size(); i++)
			{
				Recipe recipe = recipeList.get(i);
				XSSFRow row = sh.createRow(rowNumber + i);
				row.createCell(0).setCellValue(recipe.getId());
				row.createCell(1).setCellValue(recipe.getTitle());
				row.createCell(2).setCellValue(recipe.getCategory());
				row.createCell(3).setCellValue(recipe.getIngredients());
				row.createCell(4).setCellValue(recipe.getRecipeSteps());
				row.createCell(5).setCellValue(recipe.getNutrientValues());
				row.createCell(6).setCellValue(recipe.getRecipeImageLink());
				row.createCell(7).setCellValue(recipe.getLinkToRecipe());
				
				
			}
		
		
			// Append if the file exists
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			wb.close();
			if(inputStream != null)
				inputStream.close();
			
			System.out.println("Recipe written to excel successfully ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}


	
	
}
