# 使用 Gson

Gson 是一个 Java 库，可用于将 Java 对象转换为其 JSON 表示形式。它还可用于将 JSON 字符串转换为等效的
Java 对象。Gson 可以处理任意 Java 对象，包括您没有源代码的预先存在的对象。

教程：https://futurestud.io/tutorials/gson-getting-started-with-java-json-serialization-deserialization

## 安装

- Maven 依赖

```
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.10</version>
</dependency>
```

## 使用

```java
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
```