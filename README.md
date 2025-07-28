
#FakeStoreRestFulAPI

FakeStoreAPI is a free online REST API that you can use whenever you need Pseudo-real data for your e-commerce or shopping website without running any server-side code. It's awesome for teaching purposes, sample codes, tests and etc.

You can visit in detail docs in FakeStoreAPI for more information.
🤝 About the Author

👨‍💻 Madhukar Pandey
🚀 Automation Test Engineer | Passionate about Quality Engineering | BDD + Selenium + Java + CI/CD Enthusiast

## Tech Stack
| Technology            | Version  |
| ---------------       | -------- |
| Java                  | 11+      |
| Maven                 | 3.6+     |
| Selenium              | 4.x      |
| RESTAssured           | 7.x      |
| TestNG                | 7.x      |
| ExtentReports         | 5.x      |
| Jenkins               | Optional |
| GitHub Actions        | Optional |



## ## 🔗 Resources Available

| Resource | Endpoint |
|----------|----------|
| 🛍️ Products | `https://fakestoreapi.com/products` |
| 🛒 Carts    | `https://fakestoreapi.com/carts`    |
| 👤 Users    | `https://fakestoreapi.com/users`    |
| 🔐 Auth     | `https://fakestoreapi.com/auth/login` |

New! ✅ **Rating** (includes `rate` and `count`) has been added to every product!




## Dependencies


1. https://mvnrepository.com/artifact/org.seleniumhq.selenium/   selenium-java 

2. https://mvnrepository.com/artifact/org.apache.poi/poi 

3. https://mvnrepository.com/artifact/io.rest-assured/rest-assured  

4. https://mvnrepository.com/artifact/io.rest-assured/json-path 

5. https://mvnrepository.com/artifact/io.rest-assured/xml-path 

6. https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator

7. https://mvnrepository.com/artifact/org.json/json

8. https://mvnrepository.com/artifact/com.google.code.gson/gson 

9. https://mvnrepository.com/artifact/org.testng/testng

10. https://mvnrepository.com/artifact/com.github.scribejava/scribejava-apis

11. https://mvnrepository.com/artifact/com.github.javafaker/javafaker

12. https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind

13. https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml

## 📊 Reporting

After execution, view the rich Extent HTML Report at:

/test-output/ExtentReports/ExtentReport.html

Custom look & feel configured via extent-config.xml.
## 🤖 CI/CD Integration (Jenkins Ready)

1) Parametrized execution for Smoke/Regression suites.

2) Headless execution on Jenkins nodes.

3) ExtentReport generated post build.
## 📬 Feedback & Contributions

Suggestions and pull requests are welcome!
Fork this repo and contribute to make it even better.
## 📚 All Available Routes

| Method    | Endpoint                       |
| --------- | ------------------------------ |
| GET       | `/products`                    |
| GET       | `/products/:id`                |
| GET       | `/products?limit=5`            |
| GET       | `/products?sort=desc`          |
| GET       | `/products/categories`         |
| GET       | `/products/category/:category` |
| POST      | `/products` *(mock)*           |
| PUT/PATCH | `/products/:id` *(mock)*       |
| DELETE    | `/products/:id` *(mock)*       |

## 🛒 Carts

| Method    | Endpoint                                         |
| --------- | ------------------------------------------------ |
| GET       | `/carts`                                         |
| GET       | `/carts/:id`                                     |
| GET       | `/carts/user/:id`                                |
| GET       | `/carts?startdate=YYYY-MM-DD&enddate=YYYY-MM-DD` |
| POST      | `/carts` *(mock)*                                |
| PUT/PATCH | `/carts/:id` *(mock)*                            |
| DELETE    | `/carts/:id` *(mock)*                            |

## 👤 Users

| Method    | Endpoint                   |
| --------- | -------------------------- |
| GET       | `/users`                   |
| GET       | `/users/:id`               |
| GET       | `/users?limit=5&sort=desc` |
| POST      | `/users` *(mock)*          |
| PUT/PATCH | `/users/:id` *(mock)*      |
| DELETE    | `/users/:id` *(mock)*      |

## 🔐 Authentication

| Method | Endpoint      |
| ------ | ------------- |
| POST   | `/auth/login` |

## 🙌 Contribution

This project is for educational and testing purposes only. If you have any suggestions or want to collaborate, feel free to fork or open an issue.

⚠️ Note: Data is not actually stored or deleted. This is a mock API for development and testing.




## 📃 License

MIT License © 2025
##  Test Screenshot
<img width="1918" height="1078" alt="API_Tests_AllureReport" src="https://github.com/user-attachments/assets/b4667c3f-0e1c-4bd8-815b-5753e282b438" />
<img width="1918" height="1078" alt="API_Tests_Extent Report " src="https://github.com/user-attachments/assets/41862271-0fa4-4591-8926-aeb07204cb11" />
<img width="1918" height="1076" alt="API-Tests-Extent-Report" src="https://github.com/user-attachments/assets/0feb9316-0a85-4490-861c-fab091cfaac2" />
<img width="1918" height="1077" alt="Cmd-RestfulAPIbooker" src="https://github.com/user-attachments/assets/3ee6e910-516a-487a-8a06-5a1f62fa1573" />
