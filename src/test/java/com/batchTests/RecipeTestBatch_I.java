package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_I extends RecipeScraperBase
{

	@Test
	public void TestBatch_I()
	{
		//Call base method
		ExcelFileName = "RecipeBook_I.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int i = (int)('I');
		System.out.println("Executing TestBatch_I");
		Scrape(i-a,i-a);
	}
	
}
