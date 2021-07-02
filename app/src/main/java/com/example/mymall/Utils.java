package com.example.mymall;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";
    private static int ID = 0;
    private static int ORDER_ID = 0;

    private static final String ALL_ITEMS_KEY = "all_items";
    private static final String DB_NAME = "fake_database";
    private static final String CART_ITEMS_KEY = "cart_items";
    private static Gson gson = new Gson();
    private static Type grocerListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), grocerListType);
        if (null == allItems) {
            initAllItems(context);
        }
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private static void initAllItems(Context context) {

        ArrayList<GroceryItem> allItems = new ArrayList<>();
        GroceryItem milk = new GroceryItem("Milk", "Milk is a nutrient-rich liquid food produced in the " +
                "mammary glands of mammals. ... It contains many other nutrients including protein and lactose. Interspecies consumption" +
                " of milk is not uncommon, particularly among humans, many of whom consume the milk of other mammals.", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Glass_of_milk.jpg/1199px-Glass_of_milk.jpg"
                , "drink", 2.3, 8);
        allItems.add(milk);

        GroceryItem icecream = new GroceryItem("Ice Cream", "The world's tallest ice cream cone was over 9 feet tall. It was scooped in Italy. Most of the vanilla used to make ice cream comes from Madagascar & Indonesia." +
                " Chocolate syrup is the world's most popular ice cream topping.", "https://images.all-free-download.com/images/graphicthumb/cylinder_of_ice_cream_boutique_picture_167053.jpg"
                , "food", 5.4, 10);
        icecream.setPopularityPoint(10);
        icecream.setUserPoint(7);
        allItems.add(icecream);

        GroceryItem soda = new GroceryItem("Soda", "It contains many other nutrients including protein and lactose. Interspecies consumption", "http://www.freepngclipart.com/thumb/soda/3112-soda-images-hd-photo-thumb.png"
                , "Drink", 0.99, 15);
        soda.setPopularityPoint(5);
        soda.setUserPoint(15);
        allItems.add(soda);

        GroceryItem juice = new GroceryItem("Juice", "Juice is a nutrient-rich liquid food produced in the " +
                "mammary glands of mammals. ... It contains many other nutrients including protein and lactose. Interspecies consumption" +
                " of milk is not uncommon, particularly among humans, many of whom consume the milk of other mammals.", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/Glass_of_milk.jpg/1199px-Glass_of_milk.jpg"
                , "drink", 2.6, 3);
        juice.setPopularityPoint(7);
        juice.setUserPoint(17);
        allItems.add(juice);

        GroceryItem walnut = new GroceryItem("Walnut", "The world's tallest ice cream cone was over 9 feet tall. It was scooped in Italy. Most of the vanilla used to make ice cream comes from Madagascar & Indonesia." +
                " Chocolate syrup is the world's most popular ice cream topping.", "https://images.all-free-download.com/images/graphicthumb/cylinder_of_ice_cream_boutique_picture_167053.jpg"
                , "food", 4.6, 4);
        walnut.setPopularityPoint(6);
        walnut.setUserPoint(6);
        allItems.add(walnut);

        GroceryItem pistachio = new GroceryItem("pistachio", "It contains many other nutrients including protein and lactose. Interspecies consumption", "http://www.freepngclipart.com/thumb/soda/3112-soda-images-hd-photo-thumb.png"
                , "Drink", 1.0, 5);
        pistachio.setPopularityPoint(9);
        pistachio.setUserPoint(21);
        allItems.add(pistachio);

        GroceryItem soap = new GroceryItem("soap", "The world's tallest ice cream cone was over 9 feet tall. It was scooped in Italy. Most of the vanilla used to make ice cream comes from Madagascar & Indonesia." +
                " Chocolate syrup is the world's most popular ice cream topping.", "https://images.all-free-download.com/images/graphicthumb/cylinder_of_ice_cream_boutique_picture_167053.jpg"
                , "food", 4.2, 10);
        soap.setPopularityPoint(10);
        soap.setUserPoint(7);
        allItems.add(soap);

        GroceryItem spaghetti = new GroceryItem("Spaghetti", "It contains many other nutrients including protein and lactose. Interspecies consumption", "http://www.freepngclipart.com/thumb/soda/3112-soda-images-hd-photo-thumb.png"
                , "Drink", 0.99, 15);
        spaghetti.setPopularityPoint(2);
        spaghetti.setUserPoint(1);
        allItems.add(spaghetti);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();

    }

    public static ArrayList<GroceryItem> getAllItems(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), grocerListType);
        return allItems;

    }

    public static void changeRate(Context context, int itemId, int newRate) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), grocerListType);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    i.setRate(newRate);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }

    }

    public static void addReview(Context context, Review review) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);

        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }

    }

    public static ArrayList<Review> getReviewById(Context context, int itemId) {

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }

    public static void addItemToCart(Context context, GroceryItem item) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), grocerListType);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();

    }

    public static ArrayList<GroceryItem> getCartItems(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), grocerListType);
        return cartItems;
    }

    public static ArrayList<GroceryItem> searchForItems(Context context, String text) {

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }
                //when entered name is not exactly same
                String[] names = item.getName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        boolean doesExist = false;
                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                doesExist = true;
                            }
                        }
                        if (!doesExist) {
                            items.add(item);
                        }
                    }
                }
            }
            return items;
        }
        return null;
    }

    public static ArrayList<String> getCategories(Context context) {

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<String> categories = new ArrayList<>();
            for (GroceryItem item : allItems) {
                boolean doesExist = false;
                for (String s : categories) {
                    if (item.getCategory().equals(s)) {
                        doesExist = true;
                    }
                }
                if (!doesExist) {
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;
    }

    public static ArrayList<GroceryItem> getItemsByCategory(Context context, String category) {

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getCategory().equals(category)) {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }

    public static void deleteItemFromCart(Context context, GroceryItem item) {
        ArrayList<GroceryItem> cartItems = getCartItems(context);
        if (null != cartItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : cartItems) {
                if (i.getId() != item.getId()) {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void clearCartItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();
    }

    public static void increasePopularityPoint(Context context, GroceryItem item, int points) {

        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == item.getId()) {
                    i.setPopularityPoint(i.getPopularityPoint() + points);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson=new Gson();
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }

    }

    public static void changeUserPoint(Context context,GroceryItem item,int points){
        Log.d(TAG, "changeUserPoint: Attempting to add "+points+" to "+item.getName());
        ArrayList<GroceryItem> allItems=getAllItems(context);
        if(null!= allItems){
            ArrayList<GroceryItem> newItems=new ArrayList<>();
            for(GroceryItem i:allItems){
                if(i.getId()==item.getId()){
                    i.setUserPoint(i.getUserPoint()+points);
                }
                newItems.add(i);
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }

    public static String getLicences(){
        String licences="";
        licences+="This is licence";
        return licences;
    }

    public static int getID() {
        ID++;
        return ID;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }
}
