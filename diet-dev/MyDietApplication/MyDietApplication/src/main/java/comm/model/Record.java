package comm.model;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Julio on 17/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class Record {

    private int id;
    private String recordDate;
    private int calories;
    private Date registerDate;
    private ArrayList<Meal> meals;
    private ArrayList<Ingredient> ingredients;

    /**
     *
     * @return Record ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Registered date
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     *
     * @param registerDate
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     *
     * @return list of meals
     */
    public ArrayList<Meal> getMeals() {
        return meals;
    }

    /**
     *
     * @param meals
     */
    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    /**
     *
     * @return list of ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     *
     * @param ingredients
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Calculate the total calories for a record
     * including calories ingredients and meals
     * @return meal CALORIE VALUE include ingredients
     */
    public int calculateCaloricValue() {
        int caloriesSum = 0;

        if (this.getIngredients() != null){
            for(int i=0; i < this.getIngredients().size(); i++){
                caloriesSum = caloriesSum + this.getIngredients().get(i).getCalorieValue();
            }

        }else if (this.getMeals() != null){
            for(int i=0; i < this.getMeals().size(); i++){
                caloriesSum = caloriesSum + this.getMeals().get(i).calculateCalories();
            }
        }

        return caloriesSum;
    }

    /**
     *
     * @return
     */
    public String getRecordDate() {
        return recordDate;
    }

    /**
     *
     * @param recordDate
     */
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    /**
     *
     * @return
     */
    public int getCalories() {
        return calories;
    }

    /**
     *
     * @param calories
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }
}
