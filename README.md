# 🛡️ Customized Interceptors with Annotations in J2EE

> Explore how to define and apply custom interceptors using CDI/EJB annotations to modularize cross-cutting concerns such as logging, validation, and security.

---

## 📑 Table of Contents

1. [✨ Overview](#-overview)
2. [📂 Project Structure](#-project-structure)
3. [🎯 Core Concepts](#-core-concepts)
4. [🛠️ Defining Your Custom Annotation](#️-defining-your-custom-annotation)
5. [🚀 Implementing the Interceptor](#-implementing-the-interceptor)
6. [⚙️ Configuration (beans.xml)](#-configuration-beansxml)
7. [📦 Usage Examples](#-usage-examples)
8. [🔍 Deep Dive: How It Works](#-deep-dive-how-it-works)
9. [📜 License](#-license)

---

## ✨ Overview

This project demonstrates:

* Creation of a **custom interceptor annotation** (e.g., `@Loggable`).
* Implementation of an **interceptor class** using `@Interceptor` and `@AroundInvoke`.
* Registration in **`beans.xml`** for CDI/EJB interception.
* Application of interceptors at **class** or **method** level.

---

## 📂 Project Structure

```
J2EE-Customize-Interceptors/
├── src/main/java/com/tharindu/ee/interceptor
│   ├── annotation/
        ├── TimeoutLogger.java
│   │   └── Loggable.java 
│   ├── interceptor/
        ├── LoggingInterceptor.java
│   │   └── TimerInterceptor.java
│   ├── ejb/
        ├── UserSessionBean.java
│   │   └── TimerSessionBean.java 
│   └── servlet/
│       └── Test.java          # Helper used by interceptor
├── src/main/resources/
│   └── META-INF/beans.xml         # CDI config to enable interceptors
├── pom.xml                         # Maven dependencies
└── README.md                       # This documentation
```
---

## 🎯 Core Concepts

* **Interceptor Binding**: Annotations that mark where an interceptor applies.
* **Interceptor**: A class annotated with `@Interceptor` and bound to an interceptor binding.
* **InvocationContext**: Provides context (method, parameters) and controls invocation (`proceed()`).

---

## 🛠️ Defining Your Custom Annotation

```java
package com.tharindu.ee.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Loggable {
}
```

> **Explanation:**
>
> * `@InterceptorBinding` marks this as an interceptor-binding annotation.
> * `@Target` allows usage on classes or methods.
> * `@Retention(RUNTIME)` ensures availability at runtime.

---

## 🚀 Implementing the Interceptor

```java
package com.tharindu.ee.interceptor;

import com.tharindu.ee.annotation.Loggable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Loggable
@Interceptor
public class LoggingInterceptor {

  @AroundInvoke
  public Object logMethodEntry(InvocationContext ctx) throws Exception {
    System.out.println("[LOG] Entering: " + ctx.getMethod().getDeclaringClass().getSimpleName() + "." + ctx.getMethod().getName());
    long start = System.currentTimeMillis();
    try {
      return ctx.proceed();
    } finally {
      long duration = System.currentTimeMillis() - start;
      System.out.println("[LOG] Exiting: " + ctx.getMethod().getName() + " (" + duration + "ms)");
    }
  }
}
```

> **Key Points:**
>
> * `@Interceptor` enables interception.
> * `@AroundInvoke` defines the interception point.
> * `ctx.proceed()` invokes the original method.

---

## ⚙️ Configuration (beans.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
         http://xmlns.jcp.org/xml/ns/javaee
         http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd"
       bean-discovery-mode="all">

  <interceptors>
    <class>com.tharindu.ee.interceptor.LoggingInterceptor</class>
  </interceptors>

</beans>
```

> **Tip:** Ensure this is placed under `src/main/resources/META-INF/beans.xml`.

---

## 📦 Usage Examples

```java
package com.tharindu.ee.service;

import com.tharindu.ee.annotation.Loggable;
import javax.ejb.Stateless;

@Stateless
public class GreetingService {

  @Loggable
  public String greet(String name) {
    return "Hello, " + name + "!";
  }

  public String farewell(String name) {
    return "Goodbye, " + name + "!";
  }
}
```

> **Behavior:**
>
> * Calling `greet()` triggers the interceptor.
> * Calling `farewell()` runs without logging.

---

## 🔍 Deep Dive: How It Works

1. At deployment, the container scans `beans.xml` and registers `LoggingInterceptor`.
2. `@Loggable` binds the interceptor to beans/methods.
3. At runtime, when `greet()` is invoked, the container creates an `InvocationContext`.
4. `LoggingInterceptor.logMethodEntry()` is called, wrapping original method.
5. After `ctx.proceed()`, the interceptor logs exit and duration.

---

## 📜 License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

> Crafted with ☕️ and JEE magic by Tharindu714
