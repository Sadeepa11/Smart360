import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

options = Options()
options.add_argument('--headless')
options.add_argument('--window-size=1200,920')
options.add_argument('--disable-gpu')

driver = webdriver.Chrome(options=options)
assets_path = r"D:\SemiOODPII\docs\assets"

try:
    driver.get('http://localhost:8080')
    time.sleep(1)

    # 1. Main Dashboard
    driver.save_screenshot(f"{assets_path}\\ui_dashboard_main.png")
    print("Captured ui_dashboard_main.png")

    # 2. Part A: Decorator
    driver.execute_script("navigateTo('part-a-view');")
    time.sleep(0.5)
    driver.execute_script("executePartA();")
    time.sleep(0.8)
    driver.save_screenshot(f"{assets_path}\\ui_part_a_decorator.png")
    print("Captured ui_part_a_decorator.png")

    # 3. Part B: Composite
    driver.execute_script("navigateTo('part-b-view');")
    time.sleep(0.5)
    driver.execute_script("executePowerShutdown();")
    time.sleep(0.8)
    driver.save_screenshot(f"{assets_path}\\ui_part_b_composite.png")
    print("Captured ui_part_b_composite.png")

    # 4. Part C: Chain of Responsibility
    driver.execute_script("navigateTo('part-c-view');")
    time.sleep(0.5)
    driver.execute_script("executePartC();")
    time.sleep(0.8)
    driver.save_screenshot(f"{assets_path}\\ui_part_c_chain.png")
    print("Captured ui_part_c_chain.png")

    # 5. Part D: Flyweight
    driver.execute_script("navigateTo('part-d-view');")
    time.sleep(0.5)
    driver.execute_script("executePartD();")
    time.sleep(1.0)
    driver.save_screenshot(f"{assets_path}\\ui_part_d_flyweight.png")
    print("Captured ui_part_d_flyweight.png")

    # 6. Part E: Interpreter
    driver.execute_script("navigateTo('part-e-view');")
    time.sleep(0.5)
    driver.execute_script("executePartE();")
    time.sleep(0.8)
    driver.save_screenshot(f"{assets_path}\\ui_part_e_interpreter.png")
    print("Captured ui_part_e_interpreter.png")

finally:
    driver.quit()
    print("All UI views captured successfully!")
