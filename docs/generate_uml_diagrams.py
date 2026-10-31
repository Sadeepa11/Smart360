import os
import matplotlib.pyplot as plt
import matplotlib.patches as patches

output_dir = r"D:\SemiOODPII\docs\assets"
os.makedirs(output_dir, exist_ok=True)

def draw_uml_box(ax, x, y, width, height, title, stereo=None, attributes=None, methods=None, bg='#ffffff', stroke='#1a5276'):
    rect = patches.FancyBboxPatch((x, y), width, height, boxstyle="round,pad=0.02,rounding_size=5",
                                  facecolor=bg, edgecolor=stroke, linewidth=1.5)
    ax.add_patch(rect)
    
    title_y = y + height - 16
    if stereo:
        ax.text(x + width/2, title_y, f"<<{stereo}>>", color='#566573', fontsize=8,
                fontfamily='sans-serif', ha='center', va='center', style='italic')
        title_y -= 14
        
    ax.text(x + width/2, title_y, title, color='#1b2631', fontsize=9.5,
            fontfamily='sans-serif', ha='center', va='center', weight='bold')
    
    sep_y = title_y - 12
    ax.plot([x, x + width], [sep_y, sep_y], color=stroke, linewidth=1)
    
    curr_y = sep_y - 12
    if attributes:
        for attr in attributes:
            ax.text(x + 8, curr_y, attr, color='#2c3e50', fontsize=8, fontfamily='monospace', va='center')
            curr_y -= 12
            
    if methods:
        ax.plot([x, x + width], [curr_y, curr_y], color=stroke, linewidth=0.8, linestyle='--')
        curr_y -= 10
        for m in methods:
            ax.text(x + 8, curr_y, m, color='#1a5276', fontsize=8, fontfamily='monospace', va='center')
            curr_y -= 12

def generate_composite_uml():
    fig, ax = plt.subplots(figsize=(9, 5.5), dpi=150)
    ax.set_xlim(0, 700)
    ax.set_ylim(0, 420)
    ax.axis('off')
    fig.patch.set_facecolor('#f8f9fa')
    
    ax.text(350, 400, "Figure: Part B - Composite Pattern Class Diagram", color='#1a5276',
            fontsize=12, weight='bold', ha='center')

    draw_uml_box(ax, 230, 260, 240, 115, "ZoneComponent", stereo="interface",
                 methods=["+ getName() : String", "+ shutdownPower() : void", "+ restorePower() : void", "+ reportUsage() : double", "+ add(ZoneComponent c) : void"],
                 bg='#e8f8f5', stroke='#117864')

    draw_uml_box(ax, 60, 50, 230, 130, "Building", stereo="Leaf",
                 attributes=["- name : String", "- basePowerUsageKwh : double", "- powerOn : boolean"],
                 methods=["+ shutdownPower() : void", "+ restorePower() : void", "+ reportUsage() : double"],
                 bg='#ebf5fb', stroke='#2980b9')

    draw_uml_box(ax, 410, 40, 240, 145, "ZoneComposite", stereo="Composite",
                 attributes=["- name : String", "- zoneType : String", "- children : List<ZoneComponent>"],
                 methods=["+ add(c : ZoneComponent) : void", "+ remove(c : ZoneComponent) : void", "+ shutdownPower() : void", "+ reportUsage() : double"],
                 bg='#fef9e7', stroke='#b7950b')

    ax.annotate("", xy=(300, 260), xytext=(175, 180),
                arrowprops=dict(facecolor='white', edgecolor='#117864', arrowstyle='-|>', lw=1.5))
    ax.text(210, 220, "implements", color='#566573', fontsize=8, style='italic')

    ax.annotate("", xy=(400, 260), xytext=(520, 185),
                arrowprops=dict(facecolor='white', edgecolor='#117864', arrowstyle='-|>', lw=1.5))
    ax.text(475, 220, "implements", color='#566573', fontsize=8, style='italic')

    ax.annotate("", xy=(470, 310), xytext=(610, 185),
                arrowprops=dict(arrowstyle='<|-', facecolor='#b7950b', edgecolor='#b7950b', lw=1.5, linestyle=':'))
    ax.text(575, 270, "contains\n0..*", color='#b7950b', fontsize=8, weight='bold')

    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, "uml_part_b_composite.png"), bbox_inches='tight', facecolor=fig.get_facecolor())
    plt.close()
    print("Created UML: uml_part_b_composite.png")

def generate_interpreter_uml():
    fig, ax = plt.subplots(figsize=(10, 6), dpi=150)
    ax.set_xlim(0, 800)
    ax.set_ylim(0, 460)
    ax.axis('off')
    fig.patch.set_facecolor('#f8f9fa')

    ax.text(400, 440, "Figure: Part E - Interpreter Pattern AST Class Diagram", color='#1a5276',
            fontsize=12, weight='bold', ha='center')

    draw_uml_box(ax, 40, 290, 210, 100, "Context",
                 attributes=["- data : Map<String, Object>"],
                 methods=["+ set(key, val) : void", "+ getDouble(key) : double", "+ getString(key) : String"],
                 bg='#f4ecf7', stroke='#7d3c98')

    draw_uml_box(ax, 380, 295, 220, 90, "Expression", stereo="interface",
                 methods=["+ interpret(context : Context) : boolean"],
                 bg='#e8f8f5', stroke='#117864')

    ax.annotate("", xy=(250, 340), xytext=(380, 340),
                arrowprops=dict(arrowstyle='->', facecolor='#7d3c98', edgecolor='#7d3c98', lw=1.3, linestyle='--'))
    ax.text(300, 346, "evaluates in", color='#7d3c98', fontsize=8, style='italic')

    draw_uml_box(ax, 40, 60, 220, 130, "NumericComparisonExpression", stereo="TerminalExpression",
                 attributes=["- variableName : String", "- operator : String", "- threshold : double"],
                 methods=["+ interpret(Context) : boolean"],
                 bg='#ebf5fb', stroke='#2980b9')

    draw_uml_box(ax, 280, 60, 210, 130, "StringEqualsExpression", stereo="TerminalExpression",
                 attributes=["- variableName : String", "- targetValue : String"],
                 methods=["+ interpret(Context) : boolean"],
                 bg='#ebf5fb', stroke='#2980b9')

    draw_uml_box(ax, 510, 60, 250, 130, "AndExpression / OrExpression", stereo="NonTerminalExpression",
                 attributes=["- left : Expression", "- right : Expression"],
                 methods=["+ interpret(Context) : boolean"],
                 bg='#fef9e7', stroke='#b7950b')

    ax.annotate("", xy=(440, 295), xytext=(150, 190),
                arrowprops=dict(facecolor='white', edgecolor='#117864', arrowstyle='-|>', lw=1.3))
    ax.annotate("", xy=(480, 295), xytext=(380, 190),
                arrowprops=dict(facecolor='white', edgecolor='#117864', arrowstyle='-|>', lw=1.3))
    ax.annotate("", xy=(520, 295), xytext=(630, 190),
                arrowprops=dict(facecolor='white', edgecolor='#117864', arrowstyle='-|>', lw=1.3))

    ax.annotate("", xy=(600, 340), xytext=(720, 190),
                arrowprops=dict(arrowstyle='<|-', facecolor='#b7950b', edgecolor='#b7950b', lw=1.4, linestyle=':'))
    ax.text(670, 270, "operands\n1..2", color='#b7950b', fontsize=8, weight='bold')

    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, "uml_part_e_interpreter.png"), bbox_inches='tight', facecolor=fig.get_facecolor())
    plt.close()
    print("Created UML: uml_part_e_interpreter.png")

# ----------------- FIXED & PERFECTLY ALIGNED ARCHITECTURE OVERVIEW -----------------
def generate_architecture_overview():
    # Width 1100, Height 580 to give ample space
    fig, ax = plt.subplots(figsize=(11, 5.8), dpi=200)
    ax.set_xlim(0, 1000)
    ax.set_ylim(0, 520)
    ax.axis('off')
    fig.patch.set_facecolor('#ffffff')

    # Main Diagram Title
    ax.text(500, 485, "SmartCity360 Enterprise Architecture Overview", color='#1a5276',
            fontsize=17, weight='bold', ha='center', fontfamily='sans-serif')
    ax.text(500, 460, "High-Level Architectural Decomposition of Municipal Management Subsystems", color='#566573',
            fontsize=10.5, ha='center', fontfamily='sans-serif')

    card_w = 168
    card_h = 280
    gap = 20
    x_start = 40
    y_card = 120

    modules = [
        {
            "part": "PART A",
            "title": "Energy Security",
            "pattern": "Decorator Pattern",
            "desc": "Secures data streams with dynamic layers of AES-128 Encryption, SHA-256 HMAC Authentication, and GZIP Compression without altering core stream logic.",
            "bg": "#f0f7fc",
            "border": "#2980b9",
            "tag_bg": "#d4e6f1"
        },
        {
            "part": "PART B",
            "title": "Smart Zones",
            "pattern": "Composite Pattern",
            "desc": "Models spatial containment (City -> District -> Street -> Building). Executes uniform operations (shutdownPower, reportUsage) polymorphically at any tier.",
            "bg": "#f0faf7",
            "border": "#16a085",
            "tag_bg": "#d1f2eb"
        },
        {
            "part": "PART C",
            "title": "Complaints Desk",
            "pattern": "Chain of Responsibility",
            "desc": "Routes citizen grievances dynamically based on severity: Local Staff (LOW) -> District Supervisor (MEDIUM) -> City General Manager (CRITICAL) with full audit trail.",
            "bg": "#fefcf3",
            "border": "#d4ac0d",
            "tag_bg": "#fcf3cf"
        },
        {
            "part": "PART D",
            "title": "Streetlight Grid",
            "pattern": "Flyweight Pattern",
            "desc": "Manages 50,000+ smart streetlights by sharing intrinsic hardware specs via FlyweightFactory, reducing JVM heap memory consumption by 95.6%.",
            "bg": "#faf5fc",
            "border": "#8e44ad",
            "tag_bg": "#f4ecf7"
        },
        {
            "part": "PART E",
            "title": "Policy Engine",
            "pattern": "Interpreter Pattern",
            "desc": "Parses operational regulations defined in Domain-Specific Language (DSL) into AST Expressions and dynamically evaluates environmental sensor telemetry.",
            "bg": "#fdf6f0",
            "border": "#d35400",
            "tag_bg": "#fae5d3"
        }
    ]

    import textwrap
    for idx, mod in enumerate(modules):
        cx = x_start + idx * (card_w + gap)
        
        card_rect = patches.FancyBboxPatch((cx, y_card), card_w, card_h, boxstyle="round,pad=0.0,rounding_size=8",
                                           facecolor=mod["bg"], edgecolor=mod["border"], linewidth=1.8)
        ax.add_patch(card_rect)
        
        center_x = cx + card_w / 2

        # Card Part Tag (Badge)
        badge_w = 70
        badge_h = 20
        badge_rect = patches.FancyBboxPatch((center_x - badge_w/2, y_card + card_h - 26), badge_w, badge_h,
                                            boxstyle="round,pad=0.0,rounding_size=4",
                                            facecolor=mod["tag_bg"], edgecolor=mod["border"], linewidth=1.0)
        ax.add_patch(badge_rect)
        ax.text(center_x, y_card + card_h - 16, mod["part"], color=mod["border"],
                fontsize=8.5, weight='bold', ha='center', va='center', fontfamily='sans-serif')

        # Subsystem Title
        ax.text(center_x, y_card + card_h - 44, mod["title"], color='#1a252f',
                fontsize=11, weight='bold', ha='center', fontfamily='sans-serif')

        # Pattern Name
        ax.text(center_x, y_card + card_h - 62, f"[{mod['pattern']}]", color=mod["border"],
                fontsize=8.5, weight='bold', style='italic', ha='center', fontfamily='sans-serif')

        # Horizontal separator
        ax.plot([cx + 12, cx + card_w - 12], [y_card + card_h - 75, y_card + card_h - 75],
                color=mod["border"], linewidth=0.8, alpha=0.5)

        # Description text cleanly wrapped inside the card
        wrapped_desc = textwrap.fill(mod["desc"], width=23)
        ax.text(center_x, y_card + card_h - 88, wrapped_desc, color='#34495e',
                fontsize=8.2, ha='center', va='top', fontfamily='sans-serif', linespacing=1.35)

        # Connective small arrow down to base platform
        ax.annotate("", xy=(center_x, y_card - 20), xytext=(center_x, y_card),
                    arrowprops=dict(arrowstyle="-|>", color=mod["border"], lw=1.5))

    # Base unified platform bar (Width 920 from 40 to 960)
    platform_w = 5 * card_w + 4 * gap
    platform_rect = patches.FancyBboxPatch((x_start, 25), platform_w, 55, boxstyle="round,pad=0.0,rounding_size=8",
                                          facecolor='#1a5276', edgecolor='#154360', linewidth=1.5)
    ax.add_patch(platform_rect)
    ax.text(500, 57, "SmartCity360 Core Runtime & Simulation Engine",
            color='#ffffff', fontsize=12, weight='bold', ha='center', va='center', fontfamily='sans-serif')
    ax.text(500, 40, "Java 17 (LTS) | Maven Modular Architecture | Gang of Four (GoF) Patterns",
            color='#d4e6f1', fontsize=9.5, ha='center', va='center', fontfamily='sans-serif')

    plt.tight_layout()
    plt.savefig(os.path.join(output_dir, "system_architecture.png"), bbox_inches='tight', facecolor=fig.get_facecolor(), dpi=200)
    plt.close()
    print("Created perfectly aligned Architecture Overview: system_architecture.png")

generate_composite_uml()
generate_interpreter_uml()
generate_architecture_overview()
