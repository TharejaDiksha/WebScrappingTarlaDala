package com.WebScrappingTarlaDala.TestCases;

import org.testng.annotations.Test;


public class RecipeTestBatch_004  extends RecipeScraperBase
{

	@Test
	public void TestBatch_004()
	{
		//Call base method
		ExcelFileName = "RecipeBook_4";//Read from Property File
		Scrape(3,3);//Read from Property File
	}
	
}
