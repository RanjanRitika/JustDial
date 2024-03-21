# Selenium Automation Testing Project: Identifying Courses
## Project Overview
This Selenium automation testing project focuses on automating tasks related to the justdial.com web site. The primary objectives include searching for car wawshing service near current location, capturing car washing service details, navigating to free listing page, filling form, and capturing error message. once completed, going back to the home page and then searching for gyms to get the sub menu items. The project uses various dependencies and libraries to facilitate automation.
## Project Structure
### 1. Maven Repository
- **Maven Version**: 3.12.1
### 2. Dependencies
- **Apache POI**
- Version: 5.2.5
- Purpose: Used for reading and writing Excel files, facilitating data-driven testing.
- **TestNG**
- Version: 7.9.0
- Purpose: Framework for test automation that allows for parallel execution and flexible test configuration.
- **Extent Report**
- Version: 5.1.1
- Purpose: Generates interactive and detailed HTML reports to enhance test result analysis.
- **Selenium**
- Version: 4.18.1
- Purpose: Enables interaction with web elements, navigation, and form submission in the browser.
 
- **Loggers**
-Version: 2.22.1
-Purpose: Provides logging capabilities for better debugging and traceability.
 
- **Jenkins Integration**
-Jenkins is employed for continuous integration. Ensure Jenkins is set up with necessary configurations to execute the automation scripts.
- **Maven**
-Maven version 3.12.1 is the build automation tool for managing dependencies and building the project. Maven simplifies the project structure and management.
- **Git**
-Git is the version control system used for tracking changes in the source code and is used for collaboration and version management.
## Automation Test Flow
1. **Open justdial.com page**
- Navigate to justdial.com.
2. **Validate the page**
- Verify navigation to justdial.com.
3. **Search for Car Washing Services near Current Location**
- Click on the Location dropdown.
- Select the "Detect Location" option.
- Click on the Category search bar.
- Provide input "Car Washing Services" and click on search button.
4. **Apply Filters**
- Validate whether currently on the search page or not.
- Select 'Rating' from Sort By.
- Select '4.0+' from Ratings.
- Select 'Top Rated' filter.
5. **Get details**
- Get the name and phone number for the top 5 search results.
- Store the information in Excel sheet.
6. **Tasks on Free Listing**
- Click on 'Free Listing'.
- Enter invalid Phone Number.
- Click "Start Now".
- Capture and print error message.
7. **Navigate back to the home page
- Validate whether currently on the home page.
- Scroll down to the gyms.
- Click on gyms.
8. **Get Sub-Menu items**
- Capture the sub-menu items. 
- Store the details in excel.
9. **Quit from the browser**

## How to Run the Tests
1. **Open Eclipse IDE:**
- Launch Eclipse IDE on your machine.
2. **Import Project:**
- Select `File` -> `Import` from the menu.
- Choose `Existing Maven Projects` and click `Next`.
- Browse to the directory where you cloned the repository and select the project.
3. **Update Maven Project:**
- Right-click on the project in the Project Explorer.
- Choose `Maven` -> `Update Project`.
- Click `OK` to update dependencies.
 
4. **Set Up Configuration**
- Open the 'src/test/resources/config.properties' file.
- Update any configuration parameters like browser type, URLs, etc., as needed.
5. **Run Test Suite:**
- Locate the test suite file (e.g., `src/test/java/TestSuite.java`).
- Right-click on the file and choose `Run As` -> `TestNG Suite`.
6. **View Reports:**
- After execution, open the 'reports' folder.
- Find the Extent Report HTML file for detailed test reports.
## Author Information
- **Ritika Ranjan**
## Disclaimer
This project is intended for educational and testing purposes only. The authors are not responsible for any unauthorized use or modification of the code. Use at your own risk.