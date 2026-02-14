# 🚀 Hybrid SDET Framework: Selenium & Playwright Integration

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java Version](https://img.shields.io/badge/Java-24-blue)
![Automation](https://img.shields.io/badge/Automation-Selenium%20%26%20Playwright-orange)

A professional-grade, cross-browser automation ecosystem designed for high-performance end-to-end (E2E) testing. This framework demonstrates a dual-engine approach to automating the [SauceDemo](https://www.saucedemo.com/) platform, focusing on scalability, mobile responsiveness, and industry-standard OOPS principles.

## 🏗️ Architectural Highlights & OOPS Concepts

This project is built on core Object-Oriented Programming principles to ensure "Real-World" maintainability:

* **Inheritance**: Tiered `BaseTest` architecture (`BrowserDriversSetup` and `PlaywrightBase`) centralizes browser lifecycle management.
* **Encapsulation**: Centralized locators in `TestData` and environment configs in `.properties` files shield test logic from UI changes.
* **Polymorphism**: Dynamic browser instantiation allows a single script to execute across Chromium, Firefox, and WebKit via TestNG parameters.
* **Abstraction**: Logical separation between "What to test" (Test Scripts) and "How to interact" (Utility Layers).

---

## 🛠️ Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 24 |
| **Automation Engines** | Selenium WebDriver 4 & Microsoft Playwright |
| **Testing Framework** | TestNG (Parallel Execution & Parameterization) |
| **Reporting** | Allure Reports (Interactive Dashboards) |
| **Emulation** | Playwright Device Registry (Mobile Simulation) |

---

## 📱 Mobile & Cross-Browser Strategy

* **Mobile Emulation**: Native simulation of **iPhone 13** and **Pixel 5** using Playwright’s `DeviceDescriptor`, handling touch events and mobile viewports.
* **Thread-Safe Parallelism**: Configured for simultaneous Selenium and Playwright execution across multiple threads without session collision.
* **Robust Synchronization**: Combines Selenium’s **Explicit Waits** with Playwright’s **Auto-waiting** to handle asynchronous DOM updates.



---

## 🚦 Execution & Reporting

### Local Execution
```bash
mvn clean test allure:serve