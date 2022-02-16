package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_G extends RecipeScraperBase
{

	@Test
	public void TestBatch_005()
	{
		//Call base method
		ExcelFileName = "RecipeBook_G.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int g = (int)('G');
		System.out.println("Executing TestBatch_F");
		Scrape(g-a,g-a);
	}
	
}
