package com.smartcity360;

import com.smartcity360.complaints.*;
import com.smartcity360.policy.*;
import com.smartcity360.security.*;
import com.smartcity360.streetlights.*;
import com.smartcity360.zones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartCity360Test {

    // =========================================================================
    // PART A: DECORATOR PATTERN TESTS
    // =========================================================================
    @Test
    @DisplayName("Part A: Decorator Pattern preserves data integrity through multi-layer wrapping")
    void testDecoratorPatternIntegrity() {
        String originalData = "{\"meterId\":\"MTR-99\",\"usage\":45.2}";
        DataStream baseStream = new MeterDataStream("MTR-99");
        DataStream decoratedStream = new EncryptionDecorator(
                new CompressionDecorator(
                        new AuthenticationDecorator(baseStream)
                )
        );

        decoratedStream.writeData(originalData);
        String receivedData = decoratedStream.readData();

        assertEquals(originalData, receivedData, "Recovered data must exactly match original data.");
    }

    @Test
    @DisplayName("Part A: AuthenticationDecorator detects payload tampering")
    void testAuthenticationDecoratorTampering() {
        DataStream baseStream = new MeterDataStream("MTR-TEST");
        AuthenticationDecorator authStream = new AuthenticationDecorator(baseStream);

        authStream.writeData("ValidPayload");

        // Manually tamper with raw underlying stream
        baseStream.writeData("AUTH-SIG:invalid_hash::PAYLOAD::TamperedPayload");

        assertThrows(SecurityException.class, authStream::readData,
                "Tampered signature must cause SecurityException.");
    }

    // =========================================================================
    // PART B: COMPOSITE PATTERN TESTS
    // =========================================================================
    @Test
    @DisplayName("Part B: Composite Pattern aggregates power usage recursively")
    void testCompositePatternUsageAggregation() {
        ZoneComposite city = new ZoneComposite("Metro City", "CITY");
        ZoneComposite district = new ZoneComposite("District 1", "DISTRICT");
        Building b1 = new Building("Hospital", 500.0);
        Building b2 = new Building("Mall", 300.0);

        district.add(b1);
        district.add(b2);
        city.add(district);

        assertEquals(800.0, city.reportUsage(), 0.001);

        // Test uniform power shutdown
        city.shutdownPower();
        assertEquals(0.0, city.reportUsage(), 0.001);

        // Test restore
        city.restorePower();
        assertEquals(800.0, city.reportUsage(), 0.001);
    }

    // =========================================================================
    // PART C: CHAIN OF RESPONSIBILITY TESTS
    // =========================================================================
    @Test
    @DisplayName("Part C: Chain of Responsibility routes complaints to correct tiers")
    void testChainOfResponsibilityEscalation() {
        ComplaintHandler local = new LocalStaffHandler();
        ComplaintHandler supervisor = new SupervisorHandler();
        ComplaintHandler cityManager = new CityManagerHandler();

        local.setNext(supervisor).setNext(cityManager);

        Complaint low = new Complaint("C1", "Alice", "Pothole", "Minor pothole", SeverityLevel.LOW);
        Complaint med = new Complaint("C2", "Bob", "Pipe Leak", "Water line cracked", SeverityLevel.MEDIUM);
        Complaint high = new Complaint("C3", "Charlie", "Fire Hazard", "Substation on fire", SeverityLevel.CRITICAL);

        local.processComplaint(low);
        assertTrue(low.isResolved());
        assertEquals("Local Staff Field Unit", low.getResolvedBy());

        local.processComplaint(med);
        assertTrue(med.isResolved());
        assertEquals("District Operations Supervisor", med.getResolvedBy());

        local.processComplaint(high);
        assertTrue(high.isResolved());
        assertEquals("City General Manager", high.getResolvedBy());
    }

    // =========================================================================
    // PART D: FLYWEIGHT PATTERN TESTS
    // =========================================================================
    @Test
    @DisplayName("Part D: Flyweight Factory reuses existing instances")
    void testFlyweightInstanceSharing() {
        StreetLightType type1 = StreetLightFactory.getStreetLightType("LED-50W", 50, 3000, 5000, "v1");
        StreetLightType type2 = StreetLightFactory.getStreetLightType("LED-50W", 50, 3000, 5000, "v1");
        StreetLightType type3 = StreetLightFactory.getStreetLightType("LED-100W", 100, 4000, 10000, "v2");

        assertSame(type1, type2, "Factory must return the exact same instance for identical intrinsic state.");
        assertNotSame(type1, type3, "Factory must create distinct instances for different intrinsic states.");
    }

    // =========================================================================
    // PART E: INTERPRETER PATTERN TESTS
    // =========================================================================
    @Test
    @DisplayName("Part E: Interpreter Pattern parses and evaluates complex DSL expressions")
    void testInterpreterPatternEvaluation() {
        String dsl = "noise_level > 70 AND hour >= 22";
        Expression expression = PolicyParser.parse(dsl);

        Context nightNoisyContext = new Context();
        nightNoisyContext.set("noise_level", 85.0);
        nightNoisyContext.set("hour", 23);

        assertTrue(expression.interpret(nightNoisyContext), "Violates policy (noise > 70 and hour >= 22)");

        Context dayNoisyContext = new Context();
        dayNoisyContext.set("noise_level", 85.0);
        dayNoisyContext.set("hour", 14);

        assertFalse(expression.interpret(dayNoisyContext), "Does not violate policy because hour < 22");
    }
}
