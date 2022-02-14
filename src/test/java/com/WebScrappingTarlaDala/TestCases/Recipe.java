package com.WebScrappingTarlaDala.TestCases;

public class Recipe {
	
	private int Id;
	private String Title;
	private String Category;
	private String Ingredients;
	private String RecipeSteps;
	private String NutrientValues;
	private String RecipeImageLink;
	private String LinkToRecipe;
	
	public Recipe(int id)
	{
		Id = id;
	}
	
	public int getId() {
		return this.Id;
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String Title) {
		this.Title = Title;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String Category) {
		this.Category = Category;
	}
	public String getIngredients() {
		return Ingredients;
	}
	public void setIngredients(String Ingredients) {
		this.Ingredients = Ingredients;
	}
	public String getRecipeSteps() {
		return RecipeSteps;
	}
	public void setRecipeSteps(String Method) {
		this.RecipeSteps = Method;
	}
	public String getNutrientValues() {
		return NutrientValues;
	}
	public void setNutrientValues(String NutrientValues) {
		this.NutrientValues = NutrientValues;
	}
	public String getRecipeImageLink() {
		return RecipeImageLink;
	}
	public void setRecipeImageLink(String RecipeImageLink) {
		this.RecipeImageLink = RecipeImageLink;
	}
	public String getLinkToRecipe() {
		return LinkToRecipe;
	}
	public void setLinkToRecipe(String LinkToRecipe) {
		this.LinkToRecipe = LinkToRecipe;
	}
}

