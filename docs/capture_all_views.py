import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

options = Options()
options.add_argument('--headless')
options.add_argument('--window-size=1440,960')
options.add_argument('--disable-gpu')
options.add_argument('--no-sandbox')

driver = webdriver.Chrome(options=options)
assets_path = r"D:\SemiOODPII\docs\assets"

try:
    driver.get('http://localhost:8080')
    time.sleep(2)

    # 1. Main Dashboard
    driver.save_screenshot(f"{assets_path}\\ui_dashboard_main.png")
    print("Captured ui_dashboard_main.png")

    # 2. Part A: Decorator
    driver.execute_script("switchView('view-part-a', document.getElementById('nav-part-a'));")
    time.sleep(1)
    driver.execute_script("runPartA();")
    time.sleep(2)
    driver.save_screenshot(f"{assets_path}\\ui_part_a_decorator.png")
    print("Captured ui_part_a_decorator.png")

    # 3. Part B: Composite
    driver.execute_script("switchView('view-part-b', document.getElementById('nav-part-b'));")
    time.sleep(1)
    driver.execute_script("runPowerShutdown();")
    time.sleep(2)
    driver.save_screenshot(f"{assets_path}\\ui_part_b_composite.png")
    print("Captured ui_part_b_composite.png")

    # 4. Part C: Chain of Responsibility
    driver.execute_script("switchView('view-part-c', document.getElementById('nav-part-c'));")
    time.sleep(1)
    driver.execute_script("runPartC();")
    time.sleep(2)
    driver.save_screenshot(f"{assets_path}\\ui_part_c_chain.png")
    print("Captured ui_part_c_chain.png")

    # 5. Part D: Flyweight
    driver.execute_script("switchView('view-part-d', document.getElementById('nav-part-d'));")
    time.sleep(1)
    driver.execute_script("runPartD();")
    time.sleep(2)
    driver.save_screenshot(f"{assets_path}\\ui_part_d_flyweight.png")
    print("Captured ui_part_d_flyweight.png")

    # 6. Part E: Interpreter
    driver.execute_script("switchView('view-part-e', document.getElementById('nav-part-e'));")
    time.sleep(1)
    driver.execute_script("runPartE();")
    time.sleep(2)
    driver.save_screenshot(f"{assets_path}\\ui_part_e_interpreter.png")
    print("Captured ui_part_e_interpreter.png")

finally:
    driver.quit()
    print("All UI views captured successfully!")
