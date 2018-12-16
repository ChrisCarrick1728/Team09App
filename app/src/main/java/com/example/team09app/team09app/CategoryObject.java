package com.example.team09app.team09app;

import java.util.List;

/** The class declaration for a category.
 * @version 1.0
 * @author team 09
 */
public class CategoryObject {


    private List<Item> item;
    private List<Integer>  numItems;

    /** Gets the a List of item.
     * @return A List of Items.
     */
    public List<Item> getItem() {
        return item;
    }

    /** Gets a List containing the number of items in each Category.
     * @return A List of Integers.
     */
    public List<Integer> getNumItems() {
        return numItems;
    }

    /** Sets the List of Items.
     * @param item A list of Items.
     */
    public void setItem(List<Item> item) {
        this.item = item;
    }

    /** Sets the List of Integers.
     * @param numItems A list of Integers containing the number of items.
     */
    public void setNumItems(List<Integer> numItems) {
        this.numItems = numItems;
    }
}
