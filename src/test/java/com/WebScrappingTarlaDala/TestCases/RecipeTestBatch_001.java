package com.WebScrappingTarlaDala.TestCases;

import org.testng.annotations.Test;


public class RecipeTestBatch_001  extends RecipeScraperBase
{

	@Test
	public void TestBatch_001()
	{
		//Call base method
		ExcelFileName = "RecipeBook_1";//Read from Property File
		Scrape(0,0);//Read from Property File
	}
	
}
