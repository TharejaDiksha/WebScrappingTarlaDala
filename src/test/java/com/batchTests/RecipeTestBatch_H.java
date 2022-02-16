package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_H extends RecipeScraperBase
{

	@Test
	public void TestBatch_H()
	{
		//Call base method
		ExcelFileName = "RecipeBook_H.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int h = (int)('H');
		System.out.println("Executing TestBatch_H");
		Scrape(h-a,h-a);
	}
	
}
