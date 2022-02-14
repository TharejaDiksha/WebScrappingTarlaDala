package com.WebScrappingTarlaDala.TestCases;

import org.testng.annotations.Test;


public class RecipeTestBatch_002 extends RecipeScraperBase
{

	@Test
	public void TestBatch_002()
	{
		//Call base method
		ExcelFileName = "RecipeBook_1";//Read from Property File
		Scrape(1,1);//Read from Property File
	}
	
}
