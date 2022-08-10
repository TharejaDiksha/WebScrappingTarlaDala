package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;


public class RecipeTestBatch_A extends RecipeScraperBase
{

	@Test //(priority=2, dependsOnMethods={"createDriver"}  )
	public void TestBatch_001()
	{
		//Call base method

		ExcelFileName = "RecipeBook_1.xls";//Read from Property File
		int a = (int)('A');
		int start = (int)('Y') - a;
		int end = (int)('Y') - a;
		System.out.println("Executing TestBatch_001");
		Scrape(start, end);
		
		start = (int)('U') - a;
		end = (int)('U') - a;
		Scrape(start, end);
		//Scrape((int)('W'),(int)('W'));//Read from Property File
		System.out.println("Test");
	}

	
}
