package com.smartcity360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "city_policies")
public class CityPolicyRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String ruleName;

    @Column(nullable = false, length = 255)
    private String dslRule;

    @Column(length = 255)
    private String description;

    private boolean active = true;

    public CityPolicyRuleEntity() {}

    public CityPolicyRuleEntity(String ruleName, String dslRule, String description) {
        this.ruleName = ruleName;
        this.dslRule = dslRule;
        this.description = description;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getDslRule() { return dslRule; }
    public void setDslRule(String dslRule) { this.dslRule = dslRule; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
