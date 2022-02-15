package com.WebScrappingTarlaDala.TestCases;

import org.testng.annotations.Test;


public class RecipeTestBatch_005  extends RecipeScraperBase
{

	@Test
	public void TestBatch_005()
	{
		//Call base method
		ExcelFileName = "RecipeBook_5.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		Scrape(4,4);
	}
	
}
