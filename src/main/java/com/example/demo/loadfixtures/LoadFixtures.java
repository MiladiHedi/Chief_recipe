package com.example.demo.loadfixtures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.demo.WebAppApplication;
import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Difficulty;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.entities.UnitOfMeasure;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.RecipeRepository;


@Component
public class LoadFixtures implements ApplicationListener<ContextRefreshedEvent> {
	
// load object in database for demo
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

	private static final Logger log = LoggerFactory.getLogger(WebAppApplication.class);

	
    public LoadFixtures(CategoryRepository categoryRepository, 
    		RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	
    	try {
    		List<Recipe> recipes = getRecipesToSaveInDb();
    		for (Recipe recipe : recipes) {
    	    	recipeRepository.save(recipe);
    	    	log.info("save recipe : "+recipe.getName());
    		}
		} catch (Exception e) {
			// some concurrence error may be occurs at first startup (if multiple instance start in same time)
			log.error(e.getMessage());
		}
    	
    }

    private Map<String,Category>  getCategories() {
    	
    	Map<String,Category> categories = new HashMap<String,Category>();
    	String[] categoriesName = {"French", "Vegan", "Italian", "Mexican", "Japanese", "American"};
    	
    	for (String name : categoriesName) {
    		Optional<Category> categoryOptional = categoryRepository.findByDescription(name);
            if(!categoryOptional.isPresent()){
            	
            	Category category = new Category();
            	category.setDescription(name);
            	categoryRepository.save(category);
            	categoryOptional = categoryRepository.findByDescription(name);

            	if(!categoryOptional.isPresent()){
                    throw new RuntimeException("Fail to save category '"+name+"' on database");
                }
            }
            
            categories.put(name, categoryOptional.get());
		}    	
    	
    	return categories;
	}

	private List<Recipe> getRecipesToSaveInDb() {

        List<Recipe> recipes = new ArrayList<>();
        
        Map<String, Category> categories = getCategories();
        
        List<String> recipeList = new ArrayList<String>();
        for (Recipe recipe : recipeRepository.findAll()) {
        	recipeList.add(recipe.getName());
        	log.info("recipe : "+recipe.getName()+", is already present in db");
		}
        
        String recipe1Name = "Recipe 1";
        if(!recipeList.contains(recipe1Name)){
        	log.info("recipe : "+recipe1Name+", is not present in db, we create it");
        	Recipe recipe1 = new Recipe();
            recipe1.setName(recipe1Name);
            recipe1.setPrepTime(5);
            recipe1.setCookTime(10);
            recipe1.setServings(5);
            recipe1.setDifficulty( Difficulty.HARD);
            recipe1.setContent("Superatis Tauri montis verticibus qui ad solis ortum sublimius "
            		+ "attolluntur, Cilicia spatiis porrigitur late distentis dives bonis omnibus terra,"
            		+ " eiusque lateri dextro adnexa Isauria, pari sorte uberi palmite viget et frugibus "
            		+ "minutis, quam mediam navigabile flumen Calycadnus interscindit." + 
            		"\n" + 
            		"Advenit post multos Scudilo Scutariorum tribunus velamento subagrestis ingenii "
            		+ "persuasionis opifex callidus. qui eum adulabili sermone seriis admixto solus "
            		+ "omnium proficisci pellexit vultu adsimulato saepius replicando quod flagrantibus "
            		+ "votis eum videre frater cuperet patruelis, siquid per inprudentiam gestum est "
            		+ "remissurus ut mitis et clemens, participemque eum suae maiestatis adscisceret, "
            		+ "futurum laborum quoque socium, quos Arctoae provinciae diu fessae poscebant." + 
            		"\n" + 
            		"Latius iam disseminata licentia onerosus bonis omnibus Caesar nullum post haec "
            		+ "adhibens modum orientis latera cuncta vexabat nec honoratis parcens nec urbium "
            		+ "primatibus nec plebeiis.");

            Comment recipe1comment1 = new Comment();
            recipe1comment1.setAuthor("John Doe");
            recipe1comment1.setComContent("Good recipe");
            recipe1comment1.setRecipe(recipe1);
            recipe1comment1.setDate(new Date());
            Comment recipe1comment2 = new Comment();
            recipe1comment2.setAuthor("Jack Sparrow");
            recipe1comment2.setComContent("uuuh yes it suits me");
            recipe1comment2.setRecipe(recipe1);
            recipe1comment2.setDate(new Date());
            
            recipe1.addComment(recipe1comment1);
            recipe1.addComment(recipe1comment2);

            recipe1.addIngredient(new Ingredient("Ingredient1", new BigDecimal(2), UnitOfMeasure.PINT ));
            recipe1.addIngredient(new Ingredient("Ingredient2", new BigDecimal(1), UnitOfMeasure.EACH ));
            recipe1.addIngredient(new Ingredient("Ingredient3", new BigDecimal(1), UnitOfMeasure.DASH ));
           // recipe1.addIngredient(new Ingredient("Ingredient4", new BigDecimal(2), UnitOfMeasure.TEASPOON ));
            recipe1.addIngredient(new Ingredient("Ingredient5", new BigDecimal(3), UnitOfMeasure.CUP ));
            recipe1.addIngredient(new Ingredient("Ingredient6", new BigDecimal(3), UnitOfMeasure.TABLESPOON ));
            recipe1.getCategories().add(categories.get("French"));
            recipe1.getCategories().add(categories.get("Vegan"));

            recipes.add(recipe1);
        }
        

        //Recipe2
        String recipe2Name = "Recipe 2";
        if(!recipeList.contains(recipe2Name)){
        	log.info("recipe : "+recipe2Name+", is not present in db, we create it");
        	Recipe recipe2 = new Recipe();
            recipe2.setName(recipe2Name);
            recipe2.setCookTime(25);
            recipe2.setPrepTime(10);
            recipe2.setServings(2);
            recipe2.setDifficulty(Difficulty.EASY);

            recipe2.setContent("Ibi victu recreati et quiete, postquam abierat timor, vicos opulentos "
            		+ "adorti equestrium adventu cohortium, quae casu propinquabant, nec resistere planitie "
            		+ "porrecta conati digressi sunt retroque concedentes omne iuventutis robur relictum in"
            		+ " sedibus acciverunt.\n" + 
            		"\n" + 
            		"Ex turba vero imae sortis et paupertinae in tabernis aliqui pernoctant vinariis, "
            		+ "non nulli velariis umbraculorum theatralium latent, quae Campanam imitatus lasciviam"
            		+ " Catulus in aedilitate sua suspendit omnium primus; aut pugnaciter aleis certant "
            		+ "turpi sono fragosis naribus introrsum reducto spiritu concrepantes; aut quod est "
            		+ "studiorum omnium maximum ab ortu lucis ad vesperam sole fatiscunt vel pluviis, "
            		+ "per minutias aurigarum equorumque praecipua vel delicta scrutantes.");
         

            Comment recipe2comment1 = new Comment();
            recipe2comment1.setAuthor("John Doe");
            recipe2comment1.setComContent("Good recipe");
            recipe2comment1.setRecipe(recipe2);
            recipe2comment1.setDate(new Date());
            
            Comment recipe2comment2 = new Comment();
            recipe2comment2.setAuthor("Jack Sparrow");
            recipe2comment2.setComContent("uuuh yes it suits me");
            recipe2comment2.setRecipe(recipe2);
            recipe2comment2.setDate(new Date());

            recipe2.addComment(recipe2comment1);
            recipe2.addComment(recipe2comment2);
            
            recipe2.addIngredient(new Ingredient("Ingredient1", new BigDecimal(2), UnitOfMeasure.TABLESPOON ));
            recipe2.addIngredient(new Ingredient("Ingredient2", new BigDecimal(1), UnitOfMeasure.EACH ));
            recipe2.addIngredient(new Ingredient("Ingredient3", new BigDecimal(1), UnitOfMeasure.CUP ));
            recipe2.addIngredient(new Ingredient("Ingredient4", new BigDecimal(3), UnitOfMeasure.TEASPOON ));
            
    		recipe2.getCategories().add(categories.get("Italian"));


    		recipes.add(recipe2);
        }
        
        return recipes;
    }
}