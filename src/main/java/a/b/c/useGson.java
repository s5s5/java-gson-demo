package a.b.c;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class useGson {
  public static void main(String[] args) {
    Gson gson = new Gson();
    {
      // Java => JSON 序列化
      UserSimple userObject = new UserSimple(
          "Norman",
          "norman@futurestud.io",
          26,
          true
      );
      String userJson = gson.toJson(userObject);
      System.out.println(userJson);
    }
    {
      // JSON => Java 反序列化
      String userJson
          = "{'age':26,'email':'norman@futurestud.io','isDeveloper':true,'name':'Norman'}";
      UserSimple userObject = gson.fromJson(userJson, UserSimple.class);
      System.out.println(userObject);
    }
    {
      // 嵌套对象序列化
      UserAddress userAddress = new UserAddress(
          "Main Street",
          "42A",
          "Magdeburg",
          "Germany"
      );
      UserNested userObject = new UserNested(
          "Norman",
          "norman@futurestud.io",
          26,
          true,
          userAddress
      );
      String userWithAddressJson = gson.toJson(userObject);
      System.out.println(userWithAddressJson);
    }
    {
      // 嵌套对象反序列化
      String restaurantJson = """
          {
            "name": "Future Studio Steak House",
            "owner": {
              "name": "Christian",
              "address": {
                "city": "Magdeburg",
                "country": "Germany",
                "houseNumber": "42A",
                "street": "Main Street"
              }
            },
            "cook": {
              "age": 18,
              "name": "Marcus",
              "salary": 1500
            },
            "waiter": {
              "age": 18,
              "name": "Norman",
              "salary": 1000
            }
          }
          """;
      Restaurant restaurantObject = gson.fromJson(restaurantJson, Restaurant.class);
      System.out.println(restaurantObject);
    }
    {
      // 数组/列表的数据序列化
      List<RestaurantMenuItem> menu = new ArrayList<>();
      menu.add(new RestaurantMenuItem("Spaghetti", 7.99f));
      menu.add(new RestaurantMenuItem("Steak", 12.99f));
      menu.add(new RestaurantMenuItem("Salad", 5.99f));
      RestaurantWithMenu restaurant =
          new RestaurantWithMenu("Future Studio Steak House", menu);
      String restaurantJson = gson.toJson(restaurant);
      System.out.println(restaurantJson);
    }
    {
      // 数组/列表的数据反序列化
      String founderJson = """
          [
              {
                "name": "Christian",
                "flowerCount": 1
              },
              {
                "name": "Marcus",
                "flowerCount": 3
              },
              {
                "name": "Norman",
                "flowerCount": 2
              }
          ]
          """;
      // 转为数组
      Founder[] founderArray = gson.fromJson(founderJson, Founder[].class);
      System.out.println(Arrays.toString(founderArray));
      // 转为列表
      Type founderListType = new TypeToken<ArrayList<Founder>>() {}.getType();
      List<Founder> founderList = gson.fromJson(founderJson, founderListType);
      System.out.println(founderList);
    }
    {
      // Map 的序列化
      HashMap<String, List<String>> employees = new HashMap<>();
      employees.put("A", Arrays.asList("Andreas", "Arnold", "Aden"));
      employees.put("C", Arrays.asList("Christian", "Carter"));
      employees.put("M", Arrays.asList("Marcus", "Mary"));
      String employeeJson = gson.toJson(employees);
      System.out.println(employeeJson);
    }
    {
      // Map 的反序列化
      String dollarJson = """
          {
            "1$": {
              "amount": 1,
              "currency": "Dollar"
            },
            "2$": {
              "amount": 2,
              "currency": "Dollar"
            },
            "3€": {
              "amount": 3,
              "currency": "Euro"
            }
          }
          """;
      Type amountCurrencyType =
          new TypeToken<HashMap<String, AmountWithCurrency>>() {}.getType();
      HashMap<String, AmountWithCurrency> amountCurrency =
          gson.fromJson(dollarJson, amountCurrencyType);
      System.out.println(amountCurrency);
    }
    {
      // Set 的序列化
      HashSet<String> users = new HashSet<>();
      users.add("Christian");
      users.add("Marcus");
      users.add("Norman");
      users.add("Marcus");
      String usersJson = gson.toJson(users);
      System.out.println(usersJson);
    }
    {
      // Set 的反序列化
      String usersJson = """
          [
            "Christian",
            "Marcus",
            "Norman",
            "Marcus"
          ]
          """;
      Type usersSetType = new TypeToken<HashSet<String>>() {}.getType();
      HashSet<String> users = gson.fromJson(usersJson, usersSetType);
      System.out.println(users);
    }
    {
      // null 会被忽略
      UserSimple userObject = new UserSimple(null, "norman@futurestud.io", 26, true);
      String userJson = gson.toJson(userObject);
      System.out.println(userJson);
    }
  }
}

class UserSimple {
  String name;
  String email;
  int age;
  boolean isDeveloper;

  public UserSimple(String norman, String s, int i, boolean b) {
    name = norman;
    email = s;
    age = i;
    isDeveloper = b;
  }
}

class UserNested {
  String name;
  String email;
  boolean isDeveloper;
  int age;

  // new, see below!
  UserAddress userAddress;

  public UserNested(String norman, String s, int i, boolean b, UserAddress userAddress) {
    name = norman;
    email = s;
    age = i;
    isDeveloper = b;
    this.userAddress = userAddress;
  }
}

class UserAddress {
  String street;
  String houseNumber;
  String city;
  String country;

  public UserAddress(String main_street, String s, String magdeburg, String germany) {
    street = main_street;
    houseNumber = s;
    city = magdeburg;
    country = germany;
  }
}

class Restaurant {
  String name;

  Owner owner;
  Cook cook;
  Waiter waiter;
}

class Owner {
  String name;

  UserAddress address;
}

class Cook {
  String name;
  int age;
  int salary;
}

class Waiter {
  String name;
  int age;
  int salary;
}

class RestaurantWithMenu {
  String name;

  List<RestaurantMenuItem> menu;
  //RestaurantMenuItem[] menu;

  public RestaurantWithMenu(String future_studio_steak_house, List<RestaurantMenuItem> menu) {
    name = future_studio_steak_house;
    this.menu = menu;
  }
}

class RestaurantMenuItem {
  String description;
  float price;

  public RestaurantMenuItem(String spaghetti, float v) {
    description = spaghetti;
    price = v;
  }
}

class Founder {
  String name;
  int flowerCount;
}

class AmountWithCurrency {
  String currency;
  int amount;
}