# Spring Boot MVC Dynamic Form Assignment – S4-E2

##  Aim
To create a Spring Boot MVC application that captures a user's name, email, multiple addresses (with city and state), and phone numbers through a dynamic form. The submitted data should be mapped to a nested object structure using Spring MVC and displayed in a structured format on the result page.

---

## Technologies Used
- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf (for view templating)
- Maven

---


## Execution Flow
1. `GET /form` → Displays a form to enter user details.
2. User fills in name, email, dynamic addresses, and phone numbers.
3. `POST /submit` → Maps the submitted data to a `User` object and displays it.

---

##  Model Classes

### ➤ Address.java
```java
package com.example.springmvcform.model;

public class Address {
    private String city;
    private String state;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
```

---

### ➤ User.java
```java
package com.example.springmvcform.model;

import java.util.List;

public class User {
    private String name;
    private String email;
    private List<Address> addresses;
    private List<String> phones;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public List<String> getPhones() { return phones; }
    public void setPhones(List<String> phones) { this.phones = phones; }
}
```

---

##  Controller

### ➤ UserController.java
```java
package com.example.springmvcform.controller;

import com.example.springmvcform.model.Address;
import com.example.springmvcform.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class UserController {

    @GetMapping("/form")
    public String showForm(Model model) {
        User user = new User();
        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(new Address()); // initial address
        user.setAddresses(addressList);
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "result";
    }
}
```

---

## 🖼 View Templates

### ➤ form.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User Form</title>
</head>
<body>
<h2>Enter User Details</h2>
<form th:action="@{/submit}" th:object="${user}" method="post">
    Name: <input type="text" th:field="*{name}" /><br/>
    Email: <input type="email" th:field="*{email}" /><br/><br/>

    <div id="addresses">
        <div th:each="addr, iStat : *{addresses}">
            City: <input type="text" th:field="*{addresses[__${iStat.index}__].city}" />
            State: <input type="text" th:field="*{addresses[__${iStat.index}__].state}" /><br/>
        </div>
    </div>

    <button type="button" onclick="addAddress()">+ Add Address</button><br/><br/>

    Phone 1: <input type="text" name="phones[0]" /><br/>
    Phone 2: <input type="text" name="phones[1]" /><br/><br/>

    <input type="submit" value="Submit" />
</form>

<script>
function addAddress() {
    const index = document.querySelectorAll('#addresses input').length / 2;
    const div = document.createElement('div');
    div.innerHTML = `
        City: <input type="text" name="addresses[${index}].city" />
        State: <input type="text" name="addresses[${index}].state" /><br/>
    `;
    document.getElementById("addresses").appendChild(div);
}
</script>
</body>
</html>
```

---

### ➤ result.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Submission Result</title>
</head>
<body>
<h2>User Info Submitted</h2>
<p><strong>Name:</strong> <span th:text="${user.name}"></span></p>
<p><strong>Email:</strong> <span th:text="${user.email}"></span></p>

<p><strong>Addresses:</strong></p>
<ul>
    <li th:each="addr : ${user.addresses}" th:text="${addr.city + ', ' + addr.state}"></li>
</ul>

<p><strong>Phones:</strong></p>
<ul>
    <li th:each="ph : ${user.phones}" th:text="${ph}"></li>
</ul>
</body>
</html>
```

---

## ▶Running the Project
1. Open the project in your IDE.
2. Run `SpringmvcformApplication.java`.
3. Visit: `http://localhost:8080/form` in your browser.

---

##  Sample Input

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/2dd81624-e94f-4ba5-9e22-8a8804d9665b" />


### Expected Output:

<img width="1920" height="1080" alt="Screenshot from 2025-07-29 14-02-15" src="https://github.com/user-attachments/assets/ede9d52b-3ac4-48b6-84c7-a41d4d53820d" />
