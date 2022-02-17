package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_S extends RecipeScraperBase
{

	@Test
	public void TestBatch_S()
	{
		//Call base method
		ExcelFileName = "RecipeBook_S.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int s = (int)('S');
		System.out.println("Executing TestBatch_S");
		Scrape(s-a,s-a);
	}
	
}
