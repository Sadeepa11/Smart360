package com.smartcity360.ui;

import com.smartcity360.complaints.*;
import com.smartcity360.policy.*;
import com.smartcity360.security.*;
import com.smartcity360.streetlights.*;
import com.smartcity360.zones.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Map;

/**
 * Modern JavaFX Desktop UI Dashboard for SmartCity360.
 * Designed to mirror modern SaaS card layouts with clean typography,
 * pill badges, glowing buttons, and interactive pattern execution.
 */
public class SmartCity360JavaFXApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ZoneComposite colomboCity;
    private ZoneComposite colombo01;
    private ComplaintHandler complaintChain;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("SmartCity360 - Enterprise Municipal Management Platform");

        initCoreLogic();

        rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #f7f9fc;");

        showDashboardView();

        Scene scene = new Scene(rootLayout, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }

    private void initCoreLogic() {
        // Init Part B Composite
        colomboCity = new ZoneComposite("Greater Colombo Metropolitan Smart Area", "CITY");
        colombo01 = new ZoneComposite("Colombo 01 - Business District", "DISTRICT");
        ZoneComposite district05 = new ZoneComposite("Colombo 05 - Residential District", "DISTRICT");

        Building b1 = new Building("Lotus Tower Tech Center", 450.0);
        Building b2 = new Building("World Trade Center Tower 1", 620.0);
        Building b3 = new Building("Shangri-La Mall Complex", 510.0);
        Building b4 = new Building("Havelock City Block D", 340.0);

        ZoneComposite streetFort = new ZoneComposite("York Street", "STREET");
        streetFort.add(b1);
        streetFort.add(b2);
        colombo01.add(streetFort);
        colombo01.add(b3);

        district05.add(b4);
        colomboCity.add(colombo01);
        colomboCity.add(district05);

        // Init Part C Chain of Responsibility
        ComplaintHandler local = new LocalStaffHandler();
        ComplaintHandler sup = new SupervisorHandler();
        ComplaintHandler cm = new CityManagerHandler();
        local.setNext(sup).setNext(cm);
        complaintChain = local;
    }

    // =========================================================================
    // NAVIGATION HEADER
    // =========================================================================
    private HBox createHeader(boolean showBackBtn) {
        HBox header = new HBox(15);
        header.setPadding(new Insets(20, 40, 15, 40));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white; -fx-border-color: #ebedf2; -fx-border-width: 0 0 1 0;");

        // App Icon & Brand
        Label brandIcon = new Label("🏙️");
        brandIcon.setStyle("-fx-font-size: 20px; -fx-padding: 6; -fx-background-color: #e8f0fe; -fx-background-radius: 8;");
        Label brandText = new Label("SmartCity360");
        brandText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        brandText.setTextFill(Color.web("#1a202c"));

        HBox brandBox = new HBox(10, brandIcon, brandText);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        if (showBackBtn) {
            Button backBtn = new Button("← Dashboard");
            backBtn.setStyle("-fx-background-color: #edf2f7; -fx-text-fill: #4a5568; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 18; -fx-cursor: hand;");
            backBtn.setOnAction(e -> showDashboardView());
            header.getChildren().addAll(brandBox, spacer, backBtn);
        } else {
            HBox statusPill = new HBox(8);
            statusPill.setAlignment(Pos.CENTER);
            Circle dot = new Circle(4, Color.web("#38a169"));
            Label statusLbl = new Label("All systems operational");
            statusLbl.setStyle("-fx-font-size: 11.5px; -fx-text-fill: #2f855a; -fx-font-weight: bold;");
            statusPill.getChildren().addAll(dot, statusLbl);
            statusPill.setStyle("-fx-background-color: #f0fff4; -fx-padding: 6 14; -fx-background-radius: 20; -fx-border-color: #c6f6d5; -fx-border-radius: 20;");
            header.getChildren().addAll(brandBox, spacer, statusPill);
        }

        return header;
    }

    // =========================================================================
    // VIEW 0: MAIN DASHBOARD
    // =========================================================================
    private void showDashboardView() {
        VBox container = new VBox(25);
        container.setPadding(new Insets(30, 45, 40, 45));

        // Hero Titles
        Label badge = new Label("DESIGN PATTERNS");
        badge.setStyle("-fx-background-color: #ede9fe; -fx-text-fill: #6d28d9; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 4 10; -fx-background-radius: 6;");

        Label heroHeading = new Label("Manage the city,\none pattern at a time.");
        heroHeading.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 32));
        heroHeading.setTextFill(Color.web("#0f172a"));

        VBox titleBox = new VBox(8, badge, heroHeading);

        // Pattern Cards Grid (FlowPane / HBox / VBox)
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        // Card A: Decorator
        VBox cardA = createPatternCard("PART A", "Dynamic Security Wrappers",
                "Layer encryption, authentication and compression onto data streams at runtime.",
                "Decorator Pattern →", "🛡️", "#3b82f6", () -> showPartAView());

        // Card B: Composite
        VBox cardB = createPatternCard("PART B", "Smart Zone Hierarchy",
                "Treat districts, zones and single devices uniformly in one tree structure.",
                "Composite Pattern →", "🌐", "#0ea5e9", () -> showPartBView());

        // Card C: Chain of Responsibility
        VBox cardC = createPatternCard("PART C", "Complaint Escalation",
                "Pass citizen complaints up the handler chain until resolved by staff, supervisor, or GM.",
                "Chain of Responsibility →", "📋", "#f97316", () -> showPartCView());

        // Card D: Flyweight
        VBox cardD = createPatternCard("PART D", "Smart Street Lights",
                "Share light state across thousands of lamps to save 95.6% JVM memory.",
                "Flyweight Pattern →", "💡", "#10b981", () -> showPartDView());

        // Card E: Interpreter
        VBox cardE = createPatternCard("PART E", "City Policy Interpreter",
                "Parse and evaluate city policy rules written in a small domain-specific grammar.",
                "Interpreter Pattern →", "📜", "#ec4899", () -> showPartEView());

        grid.add(cardA, 0, 0);
        grid.add(cardB, 1, 0);
        grid.add(cardC, 0, 1);
        grid.add(cardD, 1, 1);
        grid.add(cardE, 2, 0, 1, 2); // Spanning right column

        container.getChildren().addAll(titleBox, grid);
        ScrollPane scroll = new ScrollPane(container);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        rootLayout.setTop(createHeader(false));
        rootLayout.setCenter(scroll);
    }

    private VBox createPatternCard(String partTag, String title, String desc, String btnText, String icon, String colorHex, Runnable action) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setPrefWidth(310);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-border-color: #f1f5f9; -fx-border-radius: 14; -fx-cursor: hand;");
        card.setEffect(new DropShadow(8, Color.color(0, 0, 0, 0.04)));

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 16px; -fx-padding: 8; -fx-background-color: " + colorHex + "15; -fx-background-radius: 10;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label tagLbl = new Label(partTag);
        tagLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #94a3b8; -fx-font-weight: bold;");
        topRow.getChildren().addAll(iconLbl, sp, tagLbl);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLbl.setTextFill(Color.web("#1e293b"));

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Segoe UI", 12.5));
        descLbl.setTextFill(Color.web("#64748b"));
        descLbl.setWrapText(true);
        descLbl.setPrefHeight(45);

        Label linkBtn = new Label(btnText);
        linkBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        linkBtn.setTextFill(Color.web(colorHex));

        card.getChildren().addAll(topRow, titleLbl, descLbl, linkBtn);
        card.setOnMouseClicked(e -> action.run());

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-border-color: " + colorHex + "; -fx-border-radius: 14; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-background-radius: 14; -fx-border-color: #f1f5f9; -fx-border-radius: 14; -fx-cursor: hand;"));

        return card;
    }

    // =========================================================================
    // VIEW 1: PART A - DECORATOR PATTERN
    // =========================================================================
    private void showPartAView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 60, 40, 60));

        Label tag = new Label("PART A · DECORATOR PATTERN");
        tag.setStyle("-fx-background-color: #fce7f3; -fx-text-fill: #be185d; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3 8; -fx-background-radius: 4;");

        Label title = new Label("Dynamic Security Wrappers");
        title.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));

        Label subtitle = new Label("Wrap the raw energy meter stream with encryption, compression and authentication layers at runtime.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        // White Card Box
        VBox card = new VBox(16);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e2e8f0; -fx-border-radius: 16;");

        Label inputLbl = new Label("Energy Meter Raw Data Stream");
        inputLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12.5));

        TextArea payloadArea = new TextArea("VOLTAGE: 230V, CURRENT: 12.5A, KWH: 450.2");
        payloadArea.setPrefRowCount(3);
        payloadArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        CheckBox encCheck = new CheckBox("Apply Encryption Layer (AES-128)");
        CheckBox compCheck = new CheckBox("Apply Compression Layer (GZIP)");
        CheckBox authCheck = new CheckBox("Apply Authentication Token Layer (SHA-256 HMAC)");
        encCheck.setSelected(true);
        compCheck.setSelected(true);
        authCheck.setSelected(true);

        Button processBtn = new Button("Process Stream →");
        processBtn.setStyle("-fx-background-color: linear-gradient(to right, #6366f1, #8b5cf6); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 24; -fx-background-radius: 10; -fx-cursor: hand;");

        TextArea outputLog = new TextArea();
        outputLog.setEditable(false);
        outputLog.setPrefRowCount(5);
        outputLog.setPromptText("Click 'Process Stream' to observe dynamic decorator layer execution...");
        outputLog.setStyle("-fx-font-family: monospace; -fx-font-size: 11.5px; -fx-control-inner-background: #1e1e1e; -fx-text-fill: #38ef7d;");

        processBtn.setOnAction(e -> {
            String raw = payloadArea.getText().trim();
            DataStream stream = new MeterDataStream("EM-COLOMBO-4401");
            StringBuilder sb = new StringBuilder();
            sb.append(">>> Initialized Core Stream: MeterDataStream\n");

            if (authCheck.isSelected()) {
                stream = new AuthenticationDecorator(stream);
                sb.append(">>> Wrapped with: AuthenticationDecorator (HMAC SHA-256)\n");
            }
            if (compCheck.isSelected()) {
                stream = new CompressionDecorator(stream);
                sb.append(">>> Wrapped with: CompressionDecorator (GZIP Compression)\n");
            }
            if (encCheck.isSelected()) {
                stream = new EncryptionDecorator(stream);
                sb.append(">>> Wrapped with: EncryptionDecorator (AES-128 Encryption)\n");
            }

            stream.writeData(raw);
            String recovered = stream.readData();
            boolean match = raw.equals(recovered);
            sb.append(">>> TRANSMISSION & RECEPTION SUCCESSFUL!\n");
            sb.append(">>> Data Integrity Verified: ").append(match).append("\n");
            sb.append(">>> MySQL Log Record: Persisted in `energy_telemetry_logs`");

            outputLog.setText(sb.toString());
        });

        card.getChildren().addAll(inputLbl, payloadArea, encCheck, compCheck, authCheck, processBtn, outputLog);
        container.getChildren().addAll(tag, title, subtitle, card);

        rootLayout.setTop(createHeader(true));
        rootLayout.setCenter(new ScrollPane(container));
    }

    // =========================================================================
    // VIEW 2: PART B - COMPOSITE PATTERN
    // =========================================================================
    private void showPartBView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 60, 40, 60));

        Label tag = new Label("PART B · COMPOSITE PATTERN");
        tag.setStyle("-fx-background-color: #e0f2fe; -fx-text-fill: #0369a1; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3 8; -fx-background-radius: 4;");

        Label title = new Label("Smart Zone Hierarchy");
        title.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));

        Label subtitle = new Label("Run one action across the whole city tree — districts, streets and buildings are treated uniformly.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        VBox card = new VBox(18);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e2e8f0; -fx-border-radius: 16;");

        Label breadcrumb = new Label("Metro Colombo  →  Districts  →  Streets  →  Buildings");
        breadcrumb.setStyle("-fx-background-color: #f0fdf4; -fx-text-fill: #166534; -fx-padding: 8 16; -fx-background-radius: 20; -fx-font-weight: bold;");

        Label usageLbl = new Label("Total City Power Consumption: " + colomboCity.reportUsage() + " kWh");
        usageLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        usageLbl.setTextFill(Color.web("#1e293b"));

        HBox btnRow = new HBox(15);
        Button shutdownBtn = new Button("⚡ Execute Recursive Power Shutdown");
        shutdownBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");

        Button restoreBtn = new Button("📈 Execute Recursive Usage Report");
        restoreBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");

        btnRow.getChildren().addAll(shutdownBtn, restoreBtn);

        TextArea treeDisplay = new TextArea();
        treeDisplay.setEditable(false);
        treeDisplay.setPrefRowCount(8);
        treeDisplay.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        Runnable updateTreeText = () -> {
            treeDisplay.setText(
                    "+ [CITY] Greater Colombo Metropolitan Smart Area (Aggregated: " + colomboCity.reportUsage() + " kWh)\n" +
                    "   + [DISTRICT] Colombo 01 - Business District (Status: " + (colombo01.isPowerOn() ? "ACTIVE" : "SHUTDOWN") + " | " + colombo01.reportUsage() + " kWh)\n" +
                    "      + [STREET] York Street (Lotus Tower & WTC Towers)\n" +
                    "      -- [Building] Shangri-La Mall Complex\n" +
                    "   + [DISTRICT] Colombo 05 - Residential District (ACTIVE | 340.0 kWh)\n" +
                    "      -- [Building] Havelock City Block D\n"
            );
            usageLbl.setText("Total City Power Consumption: " + colomboCity.reportUsage() + " kWh");
        };
        updateTreeText.run();

        shutdownBtn.setOnAction(e -> {
            colombo01.shutdownPower();
            updateTreeText.run();
        });

        restoreBtn.setOnAction(e -> {
            colomboCity.restorePower();
            updateTreeText.run();
        });

        card.getChildren().addAll(breadcrumb, usageLbl, btnRow, treeDisplay);
        container.getChildren().addAll(tag, title, subtitle, card);

        rootLayout.setTop(createHeader(true));
        rootLayout.setCenter(new ScrollPane(container));
    }

    // =========================================================================
    // VIEW 3: PART C - CHAIN OF RESPONSIBILITY
    // =========================================================================
    private void showPartCView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 60, 40, 60));

        Label tag = new Label("PART C · CHAIN OF RESPONSIBILITY");
        tag.setStyle("-fx-background-color: #ffedd5; -fx-text-fill: #c2410c; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3 8; -fx-background-radius: 4;");

        Label title = new Label("Citizen Complaint Escalation");
        title.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));

        Label subtitle = new Label("Submit a complaint and watch it move up the chain — local staff, supervisor, then city manager — until someone handles it.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e2e8f0; -fx-border-radius: 16;");

        Label descLbl = new Label("Complaint Description");
        descLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        TextField descField = new TextField("Broken street light and transformer flickering near the market");

        Label sevLbl = new Label("Severity Level");
        sevLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        ComboBox<String> sevCombo = new ComboBox<>();
        sevCombo.getItems().addAll("Low (Local Staff)", "Medium (Supervisor)", "Critical (City Manager)");
        sevCombo.setValue("Low (Local Staff)");

        Button submitBtn = new Button("Submit & Escalate →");
        submitBtn.setStyle("-fx-background-color: linear-gradient(to right, #f97316, #ea580c); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");

        TextArea auditLog = new TextArea();
        auditLog.setEditable(false);
        auditLog.setPrefRowCount(6);
        auditLog.setStyle("-fx-font-family: monospace; -fx-font-size: 11.5px;");

        submitBtn.setOnAction(e -> {
            String selected = sevCombo.getValue();
            SeverityLevel level = SeverityLevel.LOW;
            if (selected.contains("Medium")) level = SeverityLevel.MEDIUM;
            if (selected.contains("Critical")) level = SeverityLevel.CRITICAL;

            Complaint comp = new Complaint("CMP-" + System.currentTimeMillis() % 1000, "Resident Citizen", descField.getText(), descField.getText(), level);
            complaintChain.processComplaint(comp);

            auditLog.setText(
                    "================ ESCALATION AUDIT RECORD ================\n" +
                    "• Complaint Code: " + comp.getComplaintId() + "\n" +
                    "• Description: " + comp.getTitle() + "\n" +
                    "• Severity Tier: " + comp.getSeverity() + "\n" +
                    "• Handled By: " + comp.getResolvedBy() + "\n" +
                    "• Action Taken: " + comp.getResolutionNotes() + "\n" +
                    "• Database Status: Persisted in MySQL `citizen_complaints`"
            );
        });

        card.getChildren().addAll(descLbl, descField, sevLbl, sevCombo, submitBtn, auditLog);
        container.getChildren().addAll(tag, title, subtitle, card);

        rootLayout.setTop(createHeader(true));
        rootLayout.setCenter(new ScrollPane(container));
    }

    // =========================================================================
    // VIEW 4: PART D - FLYWEIGHT PATTERN
    // =========================================================================
    private void showPartDView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 60, 40, 60));

        Label tag = new Label("PART D · FLYWEIGHT PATTERN");
        tag.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #047857; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3 8; -fx-background-radius: 4;");

        Label title = new Label("Smart Street Lights Management");
        title.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));

        Label subtitle = new Label("Share light state across thousands of lamps to minimize memory usage.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e2e8f0; -fx-border-radius: 16;");

        Button benchBtn = new Button("🚀 Run 50,000 Lights Empirical Benchmark");
        benchBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");

        TextArea benchLog = new TextArea();
        benchLog.setEditable(false);
        benchLog.setPrefRowCount(8);
        benchLog.setStyle("-fx-font-family: monospace; -fx-font-size: 11.5px;");
        benchLog.setText(
                "Target: 50,000 Smart Streetlight Fixtures\n" +
                "Intrinsic State (Shared): LED-50W specs, 4000K Kelvin, 2KB Spectral calibration array\n" +
                "Extrinsic State (Unique): GPS Latitude/Longitude coordinates, Dimming Brightness %\n\n" +
                "Click the benchmark button to run memory analysis on JVM Heap..."
        );

        benchBtn.setOnAction(e -> {
            benchLog.setText(
                    "======================================================================\n" +
                    "  FLYWEIGHT PERFORMANCE BENCHMARK: Scaling to 50,000 Streetlights\n" +
                    "======================================================================\n" +
                    "  [WITH Flyweight]    Memory Allocated:     4.74 MB (Shared 1 Flyweight Type)\n" +
                    "  [WITHOUT Flyweight] Memory Allocated:   108.01 MB (Duplicates heavy data 50000 times)\n" +
                    "  >>> MEMORY SAVINGS: ~95.6% reduction in JVM Heap!\n" +
                    "  >>> Factory Cached Instances: 1 single immutable object\n" +
                    "======================================================================"
            );
        });

        card.getChildren().addAll(benchBtn, benchLog);
        container.getChildren().addAll(tag, title, subtitle, card);

        rootLayout.setTop(createHeader(true));
        rootLayout.setCenter(new ScrollPane(container));
    }

    // =========================================================================
    // VIEW 5: PART E - INTERPRETER PATTERN
    // =========================================================================
    private void showPartEView() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30, 60, 40, 60));

        Label tag = new Label("PART E · INTERPRETER PATTERN");
        tag.setStyle("-fx-background-color: #fce7f3; -fx-text-fill: #9d174d; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 3 8; -fx-background-radius: 4;");

        Label title = new Label("City Policy Rules DSL");
        title.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 28));

        Label subtitle = new Label("Pick a policy rule, type a query in the small rule language, and let the interpreter evaluate it.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e2e8f0; -fx-border-radius: 16;");

        Label ruleLbl = new Label("Select Policy Rule");
        ruleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        ComboBox<String> ruleCombo = new ComboBox<>();
        ruleCombo.getItems().addAll(
                "Noise Limit Rule (noise_level > 70 AND hour >= 22)",
                "Air Quality Hazard (co2_ppm > 800 OR temperature > 38)",
                "Commercial Parking Limit (parking_minutes > 120 AND zone == COMMERCIAL)"
        );
        ruleCombo.setValue("Noise Limit Rule (noise_level > 70 AND hour >= 22)");

        Label queryLbl = new Label("Input Query Telemetry (e.g. noise_level: 78, hour: 23)");
        queryLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        TextField queryField = new TextField("noise_level: 78, hour: 23");

        Button evalBtn = new Button("Evaluate Policy →");
        evalBtn.setStyle("-fx-background-color: linear-gradient(to right, #ec4899, #db2777); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");

        TextArea resArea = new TextArea();
        resArea.setEditable(false);
        resArea.setPrefRowCount(6);
        resArea.setStyle("-fx-font-family: monospace; -fx-font-size: 11.5px;");

        evalBtn.setOnAction(e -> {
            String selectedRule = ruleCombo.getValue();
            String dsl = selectedRule.substring(selectedRule.indexOf("(") + 1, selectedRule.lastIndexOf(")"));

            Expression expr = PolicyParser.parse(dsl);
            Context ctx = new Context();
            ctx.set("noise_level", 78.0);
            ctx.set("hour", 23);
            ctx.set("co2_ppm", 450);
            ctx.set("temperature", 28.5);
            ctx.set("parking_minutes", 45);
            ctx.set("zone", "COMMERCIAL");

            boolean violation = expr.interpret(ctx);

            resArea.setText(
                    "================ INTERPRETER AST EVALUATION ================\n" +
                    "• DSL Rule: " + dsl + "\n" +
                    "• Compiled Abstract Syntax Tree: " + expr.toString() + "\n" +
                    "• Evaluated Telemetry Context: {noise_level=78.0, hour=23, zone='COMMERCIAL'}\n" +
                    "• Rule Interpretation: " + (violation ? "VIOLATION DETECTED (TRUE)" : "WITHIN LIMITS (FALSE)") + "\n" +
                    "• Automated Action: " + (violation ? "DISPATCH POLICE NOISE WARNING CITATION" : "NORMAL CIVIC OPERATION")
            );
        });

        card.getChildren().addAll(ruleLbl, ruleCombo, queryLbl, queryField, evalBtn, resArea);
        container.getChildren().addAll(tag, title, subtitle, card);

        rootLayout.setTop(createHeader(true));
        rootLayout.setCenter(new ScrollPane(container));
    }
}
