# CS 2114 Project 3 — Group 20: PRECISION POST

## Project Overview

Precision Post is a console-based mailroom simulation game. Players process packages in arrival order by weighing them, measuring their dimensions, checking their contents and destinations, and applying the appropriate shipping stamps. Unsafe packages must be denied.

Players can also place packages on shelves, retrieve them, and review their scores. At the end of each day, up to 30 processed packages are graded based on the accuracy of their stamps.

The game follows the Model-View-Controller (MVC) architecture to separate game logic, user interaction, and presentation.

## Project Structure

All Java files are located in the `game` package under `src`.

| Class | Responsibility |
|---|---|
| `Queue.java` | Generic singly linked queue supporting FIFO operations and priority insertion |
| `Node` | Private nested class within Queue that stores data and a reference to the next node |
| `Package.java` | Stores package information, stamps, safety properties, and grading logic |
| `Model.java` | Manages package queues, shelves, current packages, and scoring |
| `View.java` | Displays menus, package information, instructions, and scores |
| `Controller.java` | Handles user input, manages game flow, and communicates between Model and View |

The project also includes `QueueTest.java`, `PackageTest.java`, `ModelTest.java`, `ViewTest.java`, and `ControllerTest.java`.

## How to Run

### Compiling and Running the Game

1. Open the project in Eclipse.
2. Locate `Controller.java` inside `src/game`.
3. Right-click `Controller.java`.
4. Select **Run As → Java Application**.
5. Follow the instructions in the console.

At the opening menu, select `A` to start a new day or `B` to exit.

The original gameplay menu provides options to process the next package, view shelves, inspect a shelf, place or remove packages, and end the day.

While processing a package, players can weigh it, measure it, inspect its contents and destination, add stamps, deny the package, or finish processing it.

The priority-processing menu option will become available once the Controller and Model integrations are completed.

### Running the Tests

1. Open Eclipse.
2. Locate the test class inside `src/game`.
3. Right-click the test class.
4. Select **Run As → JUnit Test**.
5. Check the JUnit results for errors and failures.

Repeat this for the other test classes, or run the complete test suite from the project.

The project uses the `student.TestCase` testing framework.

The Queue tests have been updated to cover priority processing and existing FIFO behavior.

### Running the Model Independently

The Model, Queue, and Package classes do not depend directly on the View or Controller.

To test the Model independently, run `ModelTest.java`. The tests exercise the game's state and logic without requiring console interaction.

This demonstrates that the Model can operate independently from the user interface.

---

## Reflection

### What Changed from the Original Specification?

**Queue and Node**

The original specification described a singly linked FIFO queue. We implemented this structure using a private nested Node class and added `clear()` to support resetting the queue.

For Deliverable 4, we added `enqueueFront(T newEntry)` to support priority processing. This method inserts an entry at the front of the queue so that it will be removed before regular entries.

The existing `enqueue`, `dequeue`, `peek`, `size`, `isEmpty`, and `clear` methods were preserved.

The change did not require a separate Node class or a complete redesign of the queue.

**Package**

The original specification proposed a separate Stamp class and arrays containing 20 stamps. We instead represented stamps using strings and used a maximum capacity of 10.

We added a stamp counter and `viewStamps()` to display applied stamps.

The `addStamp()` method rejects invalid stamp names and attempts to exceed the maximum capacity.

For grading, correct stamps receive positive points while incorrect or missing stamps receive penalties. Processing an unsafe package results in a negative score.

**Model**

The original specification did not explicitly include every method required for the complete processing flow.

We added methods such as `startProcessing()`, `finishProcessing()`, and `denyPackage()` to support gameplay.

We also changed `addStampToPackage()` to return a boolean so that the Controller can determine whether a stamp was successfully added.

The planned Deliverable 4 integration adds `addPriorityPackage()`, which creates a new package and places it at the front of the unprocessed queue.

**Controller**

The Controller was expanded to manage the game loop and validate menu input.

We changed the menu-reading logic to support different numbers of options and added validation for integer inputs.

For Deliverable 4, the planned change is a new priority-processing menu option that calls the Model rather than manipulating the Queue directly.

**View**

The View was expanded beyond the original specification to include additional menus, prompts, error messages, and package information.

For Deliverable 4, the planned changes include a priority-arrival message and an improved end-of-day summary.

### Deliverable 4 Change Feature: Priority Processing

Our selected change feature is priority processing, which was identified in our Deliverable 3 Next Steps.

Previously, all packages were added to the back of the queue and processed in FIFO order.

The new `enqueueFront()` method allows a priority package to be inserted at the front of the queue.

For example, if the queue contains packages A, B, and C, adding priority package P changes the processing order from:

`A → B → C`

to:

`P → A → B → C`

This changes the underlying queue behavior without affecting ordinary package processing.

The planned integration is:

1. The Controller receives a priority-package request.
2. The Controller calls `Model.addPriorityPackage()`.
3. The Model creates a package and calls `Queue.enqueueFront()`.
4. The next package-processing operation removes the priority package first.
5. The View displays the appropriate information.

The Queue implementation is complete. The Model and Controller integration must be verified before submission.

### Testing and Validation

We expanded `QueueTest.java` to test the existing queue methods and the new priority-processing functionality.

The additional tests cover:

- Priority insertion into an empty queue
- Priority insertion into a nonempty queue
- Null priority entries
- Multiple priority entries
- Interleaved enqueue and dequeue operations
- Priority insertion after the queue becomes empty
- Priority insertion after clearing the queue
- Normal FIFO ordering
- Queue size and empty-state behavior
- Large numbers of entries

The new tests confirm that priority insertion does not interfere with ordinary FIFO behavior.

During development, the Queue tests were adapted to the project's `student.TestCase` framework.

The Eclipse test run showed zero errors and zero failures, although the displayed count was 43/44. The final complete test count must be verified after integration.

The final Model tests should verify that priority packages are processed before regular packages and that invalid operations are handled safely.

The final Controller tests should verify that the priority-processing option works and invalid menu selections do not crash the program.

### Model/View Boundary

The project follows MVC architecture.

The Model stores and updates game state, including package queues, shelves, and scores.

The Controller receives user input and communicates with the Model and View.

The View displays information to the user.

The Queue does not directly interact with either the Controller or View.

The priority-processing feature maintains this separation because the Controller communicates with the Model, and the Model uses the Queue.

One remaining design limitation is that some Model methods return formatted text, such as shelf displays and error messages. A future improvement would move more formatting responsibilities into the View.

### Differences from Our Deliverable 3 Predictions

Our Deliverable 3 plan predicted that priority processing would require changes to Queue, Model, Controller, and View.

The Queue change matched our prediction: adding `enqueueFront()` was sufficient to support front insertion.

We did not need to change the existing `dequeue()` method because it already removes the first entry.

We also did not need to create a separate Node class.

The remaining integration should use the planned Model and Controller methods.

### Design Improvement for Future Features

A useful future improvement would be to represent package priority as a property of the Package class and centralize scheduling decisions inside the Model.

This would make it easier to support multiple priority levels without changing the Controller or View each time.

Another improvement would be to separate shelf data from its formatted display so that the View could present the same information in different formats.

### Remaining Limitations

The following items should be checked before the final submission:

- Whether leftover packages are carried over correctly between days
- Whether denying safe and unsafe packages affects scoring appropriately
- Whether the priority-processing menu is fully integrated
- Whether the end-of-day summary displays accurate information
- Whether invalid shelf positions are handled without exceptions
- Whether all automated tests run successfully

The timing challenge remains a stretch goal unless it is implemented and tested.

---

## Next Steps

### Priority 1 — Complete Model Integration

**Owner: Sarah**

- Implement and verify `Model.addPriorityPackage()`.
- Connect it to `Queue.enqueueFront()`.
- Add Model tests for priority processing.
- Verify package carry-over behavior.
- Verify deny scoring.
- Check invalid shelf positions and other bad-input cases.

### Priority 2 — Complete Controller and View Integration

**Owner: Max**

- Add the priority-processing menu option.
- Connect the option to `Model.addPriorityPackage()`.
- Display a priority-package arrival message.
- Implement or verify the end-of-day summary.
- Update Controller and View tests.
- Check invalid menu input.

### Priority 3 — Queue Testing and Verification

**Owner: Nikita**

- Implement `Queue.enqueueFront()`.
- Update Queue tests for priority processing.
- Verify normal FIFO behavior.
- Test invalid inputs.
- Verify interleaved queue operations.
- Confirm compatibility with Model integration.

The Queue implementation and test updates have been completed.

### Priority 4 — Final Integration and Documentation

**Owners: All Group Members**

- Merge everyone's changes.
- Run all automated tests.
- Resolve any remaining failures.
- Manually test the complete gameplay sequence.
- Update the README to reflect the final implementation.
- Confirm class attribution.
- Rehearse the demonstration.

### Stretch Goals

Our Deliverable 3 plan identified these stretch goals:

| Feature | Plan |
|---|---|
| Priority processing | Selected Deliverable 4 change feature |
| Priority shipping | Committed & Completed |
| Shipments per location | Committed & Completed |
| Timing challenge | Committed & Not Yet Completed |
| Sorting shelves by rule | Dropped |

### Three-Minute Demo Walkthrough

| Time | Action | Presenter |
|---|---|---|
| 0:00–0:25 | Introduce Precision Post and MVC architecture | Nikita |
| 0:25–0:55 | Start a day and inspect a regular package | Max |
| 0:55–1:30 | Add a priority package and demonstrate queue ordering | Nikita |
| 1:30–2:05 | Demonstrate package stamping, processing, or denial | Sarah |
| 2:05–2:35 | Show shelves and end-of-day scoring | Max |
| 2:35–3:00 | Explain testing, validation, and remaining features | All |

This walkthrough should be rehearsed after all features are integrated.

---

## Attribution

| File | Contributor | Contribution |
|---|---|---|
| `Queue.java` (including nested Node) | Nikita | Implemented and revised the linked queue; added priority insertion with `enqueueFront()`. GenAI-assisted. |
| `QueueTest.java` | Nikita | Added tests for priority processing, invalid inputs, FIFO ordering, and interleaved operations. GenAI-assisted. |
| `Package.java` | Sarah | Implemented package properties, stamp handling, and grading logic. |
| `PackageTest.java` | Nikita | Contributed package tests and validation. GenAI-assisted. |
| `Model.java` | Sarah | Implemented game state, package management, shelves, and scoring. |
| `ModelTest.java` | Sarah | Developed and revised tests for Model functionality. GenAI-assisted. |
| `View.java` | Max | Implemented menus, output, and gameplay messages. |
| `ViewTest.java` | Max | Developed tests for View behavior. GenAI-assisted. |
| `Controller.java` | Max | Implemented user input, validation, and game flow. |
| `ControllerTest.java` | Max | Developed tests for Controller behavior. GenAI-assisted. |
| `README.md` | All | Documented project structure, changes, testing, reflection, and future work. |

Each member should verify their attribution before submission.

## GenAI Use

Our group used generative AI during development in the following ways:

1. **Project naming:** We requested name suggestions and selected Precision Post.
2. **Specification review:** We asked for help identifying possible logic errors and missing functionality in our original specification.
3. **Testing:** We used GenAI to help review tests for the Controller, View, Queue, and Package classes.
4. **Debugging:** We asked for assistance identifying logic errors and improving code behavior.
5. **Priority processing:** We used ChatGPT to help review `Queue.enqueueFront()` while preserving existing FIFO functionality.
6. **Queue tests:** We requested tests for priority insertion, null inputs, interleaved operations, and queue reuse. The initial generated tests used JUnit 5, so we revised them to match the project's `student.TestCase` framework.
7. **Documentation:** We used ChatGPT to help organize the Deliverable 4 reflection, next steps, attribution, and demo plan.

Generated suggestions were reviewed and adapted to the project's design and requirements. Testing in Eclipse was used to check the implementation.

## Final Submission Checklist

- [ ] Queue and QueueTest changes committed and pushed
- [ ] Model priority-processing integration complete
- [ ] Controller and View priority-processing integration complete
- [ ] New feature accessible through gameplay
- [ ] Normal and invalid-input tests added for core methods
- [ ] Complete automated test suite passes
- [ ] README reflects the final code
- [ ] Reflection and Next Steps updated
- [ ] Attribution verified by all group members
- [ ] Three-minute demonstration rehearsed
- [ ] Repository URL submitted
