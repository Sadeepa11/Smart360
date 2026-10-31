# SmartCity360 - Enterprise Municipal Management Platform
## JIAT / OODP II Final Assessment (JIAT/OODP II / EX / 01)

Enterprise municipal management platform applying Gang of Four (GoF) Object-Oriented Design Patterns for the Java Institute for Advanced Technology (JIAT) Final Assessment.

---

## 🏛️ Design Patterns Implemented

| Part | Subsystem | Design Pattern | Classification | Key Architectural Benefit |
| :--- | :--- | :--- | :--- | :--- |
| **Part A** | Dynamic Security Wrappers for Data Streams | **Decorator Pattern** | Structural | Dynamic layering of AES-128 Encryption, SHA-256 HMAC Authentication, and GZIP Compression without altering core stream logic. |
| **Part B** | Hierarchical Structure of Smart Zones | **Composite Pattern** | Structural | Spatial hierarchy (`City -> District -> Street -> Building`) enabling uniform recursive commands (`shutdownPower`, `reportUsage`) at any level. |
| **Part C** | Escalation of Citizen Complaints | **Chain of Responsibility** | Behavioral | Decoupled grievance dispatch routing complaints based on severity: Local Staff (`LOW`) -> Supervisor (`MEDIUM`) -> City General Manager (`CRITICAL`). |
| **Part D** | Efficient Management of Smart Streetlights | **Flyweight Pattern** | Structural | High-density fixture scaling (50,000+ units) sharing intrinsic specs via `StreetLightFactory` to achieve a verified **95.6% JVM memory reduction**. |
| **Part E** | City Operational Policies in DSL | **Interpreter Pattern** | Behavioral | Parses operational rules defined in a Domain-Specific Language (DSL) into an Abstract Syntax Tree (AST) to evaluate live sensor telemetry dynamically. |

---

## 📁 Repository Structure

```text
├── SmartCity360/                      # Core Maven Java Application
│   ├── src/main/java/com/smartcity360/
│   │   ├── security/                 # Part A: Decorator Pattern
│   │   ├── zones/                    # Part B: Composite Pattern
│   │   ├── complaints/               # Part C: Chain of Responsibility
│   │   ├── streetlights/             # Part D: Flyweight Pattern
│   │   ├── policy/                   # Part E: Interpreter Pattern
│   │   └── app/                      # Main Interactive Console Runner
│   ├── src/test/java/com/smartcity360/ # Automated JUnit 5 Test Suite (6 Tests)
│   └── pom.xml                       # Maven Configuration
├── docs/                             # Submission Documentation & Guides
│   ├── SmartCity360_Project_Report.docx        # Final Project Report (Word format)
│   ├── SmartCity360_Project_Report.pdf         # Final Project Report (PDF format)
│   ├── SmartCity360_Video_Demonstration_Guide.pdf # 10-15 Min Demonstration Video Guide
│   └── assets/                       # High-Resolution UML Diagrams & Screenshots
├── SmartCity360_SourceCode.zip       # Complete Project Source Code Archive
└── README.md
```

---

## 🚀 How to Build & Run

### Prerequisites
* Java JDK 17 (or newer)
* Apache Maven 3.8+

### 1. Compile and Run Automated Tests
```bash
cd SmartCity360
mvn clean test
```
*Result: 6/6 tests passing (BUILD SUCCESS)*

### 2. Run Interactive Console Simulation
```bash
cd SmartCity360
mvn compile exec:java
```

---

## 📄 License & Academic Declaration
Developed for academic assessment purposes at the Java Institute for Advanced Technology. All rights reserved.
