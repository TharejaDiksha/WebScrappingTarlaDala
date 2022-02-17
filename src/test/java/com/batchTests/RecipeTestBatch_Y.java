package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_Y extends RecipeScraperBase
{

	@Test
	public void TestBatch_Y()
	{
		//Call base method
		ExcelFileName = "RecipeBook_Y.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int y = (int)('Y');
		System.out.println("Executing TestBatch_Y");
		Scrape(y-a,y-a);
	}
	
}
