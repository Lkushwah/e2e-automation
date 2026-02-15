# 🚀 Hybrid SDET E-Commerce Framework: Selenium & Playwright Integration 

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java Version](https://img.shields.io/badge/Java-24-blue)
![Reporting](https://img.shields.io/badge/Reporting-Allure-red)

A professional-grade, cross-browser automation ecosystem designed for high-performance E2E testing. This framework demonstrates a unique **Dual-Engine approach**, bridging **Selenium 4** and **Playwright Java** to automate the [SauceDemo](https://www.saucedemo.com/) platform.

---

## 🏗️ Architectural Highlights & OOPS Concepts

This project implements industry-standard OOPS principles to ensure enterprise-level maintainability:

* **Inheritance**: Tiered `BaseTest` architecture (`BrowserDriversSetup` and `playwrightBase`) centralizes browser lifecycle and driver management.
* **Encapsulation**: UI locators and environment configurations are isolated within `TestData.java`, shielding test logic from DOM changes.
* **Polymorphism**: Dynamic browser instantiation (Chromium, Firefox, WebKit) and device emulation via TestNG `@Parameters`.
* **Abstraction**: Logical separation between "What to test" (Test Scripts) from "How to interact" (Utility Layers and Engine Logic).

---

## 🛠️ Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | **Java 24** (Modern Syntax) |
| **Automation** | Selenium WebDriver 4 & Playwright Java 1.58.0 |
| **Test Runner** | TestNG (Parallel Execution & Parameterization) |
| **Reporting** | **Allure Reports** (Interactive Dashboards) |
| **Emulation** | Manual Java Device Profiling (Viewport, UserAgent, Touch) |

---

## 📱 Mobile & Cross-Browser Strategy

* **Java-Specific Mobile Emulation**: Implements manual device profiling for **iPhone 13** and **Pixel 5** using `Browser.NewContextOptions`. This bypasses the limitations of the Java API to provide native mobile simulation.
* **Thread-Safe Parallelism**: Configured for simultaneous engine execution across multiple threads without session collision or driver leaks.
* **Advanced Synchronization**: Leverages Playwright’s **Auto-waiting** alongside Selenium's **Explicit Waits** to handle asynchronous DOM updates and dynamic state changes.

---

## 🚦 Execution & Reporting

### Local Execution
To execute the full suite and launch the interactive report:
```bash
mvn clean test allure:serve