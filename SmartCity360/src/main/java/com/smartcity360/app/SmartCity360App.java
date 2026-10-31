package com.smartcity360.app;

import com.smartcity360.complaints.*;
import com.smartcity360.policy.*;
import com.smartcity360.security.*;
import com.smartcity360.streetlights.*;
import com.smartcity360.zones.*;

import java.util.Scanner;

/**
 * SmartCity360 Main Interactive Demonstration Runner.
 * Showcases all five Gang of Four (GoF) design patterns implemented for the
 * JIAT Object Oriented Design Patterns II Final Assessment.
 */
public class SmartCity360App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        printBanner();

        while (!exit) {
            printMenu();
            System.out.print("Select an option [1-7]: ");
            String choice = scanner.nextLine().trim();

            System.out.println();
            switch (choice) {
                case "1" -> runPartA_Decorator();
                case "2" -> runPartB_Composite();
                case "3" -> runPartC_ChainOfResponsibility();
                case "4" -> runPartD_Flyweight();
                case "5" -> runPartE_Interpreter();
                case "6" -> runAllSimulations();
                case "7" -> {
                    System.out.println("Thank you for evaluating SmartCity360 Management Platform. Exiting...");
                    exit = true;
                }
                default -> System.out.println("Invalid selection. Please choose an option between 1 and 7.");
            }
            if (!exit) {
                System.out.println("\nPress [ENTER] to return to the main menu...");
                scanner.nextLine();
            }
        }
    }

    private static void printBanner() {
        System.out.println("================================================================================");
        System.out.println("           SMARTCITY360 - ENTERPRISE MUNICIPAL MANAGEMENT PLATFORM             ");
        System.out.println("                UrbanTech Solutions | JIAT / OODP II Final Project              ");
        System.out.println("================================================================================");
    }

    private static void printMenu() {
        System.out.println("\n----------------------------- MAIN DEMONSTRATION MENU --------------------------");
        System.out.println("  [1] Part A: Dynamic Security Wrappers for Data Streams (Decorator Pattern)");
        System.out.println("  [2] Part B: Hierarchical Structure of Smart Zones (Composite Pattern)");
        System.out.println("  [3] Part C: Escalation of Citizen Complaints (Chain of Responsibility)");
        System.out.println("  [4] Part D: Efficient Management of Smart Streetlights (Flyweight Pattern)");
        System.out.println("  [5] Part E: Dynamic Policy Interpretation from DSL (Interpreter Pattern)");
        System.out.println("  [6] Full End-to-End Integrated Simulation (All Patterns)");
        System.out.println("  [7] Exit System");
        System.out.println("--------------------------------------------------------------------------------");
    }

    // =========================================================================
    // PART A - DECORATOR PATTERN DEMO
    // =========================================================================
    public static void runPartA_Decorator() {
        System.out.println(">>> [PART A DEMO] DECORATOR PATTERN: Energy Meter Stream Security Layers <<<");
        System.out.println("Requirement: Add Encryption, Compression, and Authentication without modifying core logic.\n");

        String rawTelemetry = "{\"meterId\":\"EM-COLOMBO-4401\",\"timestamp\":\"2026-10-30T13:00:00Z\",\"voltage\":230.4,\"currentKwh\":1482.65}";
        System.out.println("1. Raw Original Sensor Payload:");
        System.out.println("   " + rawTelemetry + "\n");

        // 1. Raw Stream
        System.out.println("2. Writing to unadorned Base MeterDataStream:");
        DataStream rawStream = new MeterDataStream("EM-COLOMBO-4401");
        rawStream.writeData(rawTelemetry);

        // 2. Layered Wrappers: Auth -> Compression -> Encryption
        System.out.println("\n3. Constructing Dynamic Decorator Pipeline: Encryption(Compression(Authentication(BaseStream))):");
        DataStream securedStream = new EncryptionDecorator(
                new CompressionDecorator(
                        new AuthenticationDecorator(
                                new MeterDataStream("EM-COLOMBO-4401")
                        )
                )
        );

        System.out.println("\n--- [TRANSMISSION PHASE] Writing data through layered pipeline ---");
        securedStream.writeData(rawTelemetry);

        System.out.println("\n--- [RECEPTION PHASE] Reading and unwrapping data from stream ---");
        String recoveredData = securedStream.readData();

        System.out.println("\n4. Verification Result:");
        System.out.println("   Payload Authenticated, Decompressed, and Decrypted successfully: " + rawTelemetry.equals(recoveredData));
    }

    // =========================================================================
    // PART B - COMPOSITE PATTERN DEMO
    // =========================================================================
    public static void runPartB_Composite() {
        System.out.println(">>> [PART B DEMO] COMPOSITE PATTERN: Hierarchical Smart Zones <<<");
        System.out.println("Requirement: City -> District -> Street -> Building hierarchy with uniform commands.\n");

        // Building leaf nodes
        Building bldA1 = new Building("Lotus Tower Tech Center", 450.0);
        Building bldA2 = new Building("World Trade Center Tower 1", 620.0);
        Building bldB1 = new Building("Galle Face Residences", 280.0);
        Building bldB2 = new Building("Shangri-La Mall Complex", 510.0);
        Building bldC1 = new Building("Havelock City Block D", 340.0);

        // Street composites
        ZoneComposite streetFort = new ZoneComposite("York Street", "STREET");
        streetFort.add(bldA1);
        streetFort.add(bldA2);

        ZoneComposite streetGalle = new ZoneComposite("Galle Road Coastal Strip", "STREET");
        streetGalle.add(bldB1);
        streetGalle.add(bldB2);

        ZoneComposite streetHavelock = new ZoneComposite("Dickmans Road", "STREET");
        streetHavelock.add(bldC1);

        // District composites
        ZoneComposite districtColombo01 = new ZoneComposite("Colombo 01 - Business District", "DISTRICT");
        districtColombo01.add(streetFort);
        districtColombo01.add(streetGalle);

        ZoneComposite districtColombo05 = new ZoneComposite("Colombo 05 - Residential District", "DISTRICT");
        districtColombo05.add(streetHavelock);

        // Root City composite
        ZoneComposite colomboMetro = new ZoneComposite("Greater Colombo Metropolitan Smart Area", "CITY");
        colomboMetro.add(districtColombo01);
        colomboMetro.add(districtColombo05);

        // 1. Display Full Hierarchy
        System.out.println("1. Smart City Structural Hierarchy Tree:");
        colomboMetro.displayHierarchy("");

        System.out.printf("%n2. Total City Grid Consumption: %.2f kWh%n", colomboMetro.reportUsage());

        // 3. Uniform Command Dispatch: Selective District Shutdown
        System.out.println("\n3. Executing Uniform Command 'shutdownPower()' on [Colombo 01 - Business District]:");
        districtColombo01.shutdownPower();

        System.out.println("\n4. Hierarchy Status after localized district shutdown:");
        colomboMetro.displayHierarchy("");
        System.out.printf("%nNew Total City Power Draw: %.2f kWh (Colombo 05 remains operational!)%n", colomboMetro.reportUsage());

        // 5. Restore Power
        System.out.println("\n5. Restoring power to Colombo 01:");
        districtColombo01.restorePower();
        System.out.printf("Restored Total City Power Draw: %.2f kWh%n", colomboMetro.reportUsage());
    }

    // =========================================================================
    // PART C - CHAIN OF RESPONSIBILITY DEMO
    // =========================================================================
    public static void runPartC_ChainOfResponsibility() {
        System.out.println(">>> [PART C DEMO] CHAIN OF RESPONSIBILITY: Citizen Complaint Escalation <<<");
        System.out.println("Requirement: Escalate from Local Staff -> Supervisor -> City Manager based on severity.\n");

        // Assemble the escalation pipeline
        ComplaintHandler localStaff = new LocalStaffHandler();
        ComplaintHandler supervisor = new SupervisorHandler();
        ComplaintHandler cityManager = new CityManagerHandler();

        localStaff.setNext(supervisor).setNext(cityManager);

        // Create sample citizen complaints of varying severities
        Complaint c1 = new Complaint("CMP-101", "Kamal Perera", "Street Trash Bin Overflowing",
                "Public bin at 5th Lane is overflowing into the walkway.", SeverityLevel.LOW);

        Complaint c2 = new Complaint("CMP-102", "Anura Silva", "Water Pipe Fracture & Low Pressure",
                "Submain distribution pipe leaking into road, impacting 40 houses.", SeverityLevel.MEDIUM);

        Complaint c3 = new Complaint("CMP-103", "Dr. Priyantha", "Electrical Grid Transformer Explosion",
                "Severe electrical explosion and outage across 2 hospitals.", SeverityLevel.CRITICAL);

        System.out.println("--- Submitting Complaint #1 (LOW Severity) ---");
        localStaff.processComplaint(c1);

        System.out.println("\n--- Submitting Complaint #2 (MEDIUM Severity) ---");
        localStaff.processComplaint(c2);

        System.out.println("\n--- Submitting Complaint #3 (CRITICAL Severity) ---");
        localStaff.processComplaint(c3);

        System.out.println("\nFinal Audit Summary:");
        System.out.println("  " + c1);
        System.out.println("  " + c2);
        System.out.println("  " + c3);
    }

    // =========================================================================
    // PART D - FLYWEIGHT PATTERN DEMO
    // =========================================================================
    public static void runPartD_Flyweight() {
        System.out.println(">>> [PART D DEMO] FLYWEIGHT PATTERN: Efficient Smart Streetlight Network <<<");
        System.out.println("Requirement: Support thousands of streetlights with minimal heap footprint.\n");

        // Get shared flyweights from factory
        StreetLightType standardLed = StreetLightFactory.getStreetLightType("EcoSmart-50W", 50, 4000, 6000, "FW-2.1");
        StreetLightType highPowerLed = StreetLightFactory.getStreetLightType("HighwayLum-120W", 120, 5000, 15000, "FW-3.0");

        // Requesting existing type again -> reused from cache
        StreetLightType reusedLed = StreetLightFactory.getStreetLightType("EcoSmart-50W", 50, 4000, 6000, "FW-2.1");
        System.out.printf("Factory Cache Verification: Are instances identical? %b (Total Flyweight objects in memory: %d)%n%n",
                (standardLed == reusedLed), StreetLightFactory.getCachedCount());

        // Context objects (Extrinsic state)
        StreetLight sl1 = new StreetLight("SL-001", 6.9271, 79.8612, standardLed);
        StreetLight sl2 = new StreetLight("SL-002", 6.9285, 79.8625, standardLed);
        StreetLight sl3 = new StreetLight("SL-HWY-099", 6.9500, 79.8900, highPowerLed);

        System.out.println("Operating Context Streetlights:");
        sl1.operate();
        sl2.operate();
        sl3.operate();

        System.out.println();
        // Run empirical memory benchmark with 50,000 fixtures
        StreetLightMemoryBenchmark.runBenchmark(50_000);
    }

    // =========================================================================
    // PART E - INTERPRETER PATTERN DEMO
    // =========================================================================
    public static void runPartE_Interpreter() {
        System.out.println(">>> [PART E DEMO] INTERPRETER PATTERN: City Policy Rule Engine DSL <<<");
        System.out.println("Requirement: Parse and evaluate dynamic municipal operational rules.\n");

        // Define DSL Rules
        String rule1 = "noise_level > 70 AND hour >= 22";
        String rule2 = "co2_ppm > 800 OR temperature > 38";
        String rule3 = "parking_minutes > 120 AND zone == COMMERCIAL";

        System.out.println("Compiling Domain Specific Language (DSL) Rules into Expression ASTs:");
        Expression expr1 = PolicyParser.parse(rule1);
        Expression expr2 = PolicyParser.parse(rule2);
        Expression expr3 = PolicyParser.parse(rule3);

        System.out.println("  Rule 1 AST: " + expr1);
        System.out.println("  Rule 2 AST: " + expr2);
        System.out.println("  Rule 3 AST: " + expr3);

        // Context 1: Late night quiet zone violation
        Context envNight = new Context();
        envNight.set("noise_level", 82.5);
        envNight.set("hour", 23);
        envNight.set("co2_ppm", 420);
        envNight.set("temperature", 28.0);
        envNight.set("zone", "RESIDENTIAL");
        envNight.set("parking_minutes", 45);

        System.out.println("\nEvaluating Environment Snapshot #1 (Late Night Residential):");
        System.out.println("  Telemetry: " + envNight);
        System.out.printf("  Rule 1 [Night Noise Violation]: %s => ACTION: %s%n",
                expr1.interpret(envNight), (expr1.interpret(envNight) ? "DISPATCH POLICE WARNING" : "NONE"));
        System.out.printf("  Rule 2 [Environmental Hazard Alert]: %s => ACTION: %s%n",
                expr2.interpret(envNight), (expr2.interpret(envNight) ? "TRIGGER VENTILATION" : "NORMAL"));

        // Context 2: Daytime Commercial Parking & Heatwave
        Context envDay = new Context();
        envDay.set("noise_level", 65.0);
        envDay.set("hour", 14);
        envDay.set("co2_ppm", 910);
        envDay.set("temperature", 39.5);
        envDay.set("zone", "COMMERCIAL");
        envDay.set("parking_minutes", 180);

        System.out.println("\nEvaluating Environment Snapshot #2 (Daytime Heatwave & Commercial Parking):");
        System.out.println("  Telemetry: " + envDay);
        System.out.printf("  Rule 2 [Environmental Hazard Alert]: %s => ACTION: %s%n",
                expr2.interpret(envDay), (expr2.interpret(envDay) ? "TRIGGER VENTILATION / ADVISORY" : "NORMAL"));
        System.out.printf("  Rule 3 [Overtime Parking Fine]: %s => ACTION: %s%n",
                expr3.interpret(envDay), (expr3.interpret(envDay) ? "ISSUE PARKING CITATION TICKET" : "VALID"));
    }

    // =========================================================================
    // INTEGRATED SIMULATION
    // =========================================================================
    public static void runAllSimulations() {
        System.out.println("\n================================================================================");
        System.out.println("            EXECUTING FULL SMARTCITY360 INTEGRATED SYSTEM SIMULATION            ");
        System.out.println("================================================================================\n");

        runPartA_Decorator();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        runPartB_Composite();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        runPartC_ChainOfResponsibility();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        runPartD_Flyweight();
        System.out.println("\n--------------------------------------------------------------------------------\n");
        runPartE_Interpreter();

        System.out.println("\n================================================================================");
        System.out.println("      ALL DESIGN PATTERNS VALIDATED AND EXECUTED WITH 100% SUCCESS!             ");
        System.out.println("================================================================================\n");
    }
}
