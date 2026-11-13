# CS2114 Project-3 Group 20: PRECISION POST
The current console interface can be run through the Controller class.

1. Run Controller.java.
2. The opening menu will appear.
3. Enter 1 to start a new day or 2 to exit.
4. During a day, select options to process packages, examine package information, add stamps, reject packages, or finish processing.
5. Enter 2 from the main game menu to end the day.

The ViewTest class can also be run to demonstrate the different display methods in the View class. ControllerTest contains JUnit tests for controller navigation, menu selections, stamp selection, and invalid input.

##### What changed from the spec?
One change was how we divided responsibilities between the view and controller. The View class contains methods dedicated to displaying menus, instructions, package information, scores, and messages. Rather than having the view handle user input, the Controller handles input through a Scanner and determines which view methods to call. 
The ViewTest was also created to both test the methods and test that the actual view of the console was correct. ControllerTest was more for system navigation and multiple menu selections.

##### What doesn't work yet?
1. Package weight, volume, contents, and destination currently display placeholder messages instead of retrieving actual package information.
2. Stamp selection returns the selected stamp, but the stamp is not yet applied to a package through the model.
3. Rejecting and finishing a package end the package-processing loop without performing the full model operations.
4. The end-of-day method displays a placeholder scoring message because the actual score calculation isn't connected. The same is true for the final score.

##### Attribution
View.java - Max Iaci - Wrote the class; implemented menus, instructions, package information displays, score displays, and general messages.
ViewTest.java - Max Iaci - Wrote the test class; tested the view's display methods.
Controller.java - Max Iaci - Wrote the class; implemented menu use, user input handling, stamp selection, and program usage.
ControllerTest.java	- Max Iaci - Wrote tests; tested menu navigation, package processing, stamp selection, invalid inputs, and integer input handling.

##### GenAi Usage
Used to help interpret assignment details, as well as assisting with Scanner implementation (Controller) and test writing for some methods in Controller test (Byte). 