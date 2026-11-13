# CS2114 Project 3, Group 20: PRECISION POST

Precision Post is a console mailroom game. Packages line up in a queue. For each one you weigh it, measure it, read its destination and contents, and apply the correct stamps (weight class, size class, destination, and special stamps like Fragile). Unsafe packages must be denied. At the end of the day the first 30 processed packages are scored and the score is recorded.

All code is in the `game` package under `src`.

| Class | Role |
| --- | --- |
| `Queue` (with a private nested `Node`) | Generic singly linked FIFO queue |
| `Package` | One package: weight, size, destination, contents, stamps, grading |
| `Model` | Game state: two queues of packages, 5x5 shelves, current package, daily scores |
| `View` | Prints menus, package info and scores. Never reads input and never touches the model |
| `Controller` | Reads and checks input, calls the model, tells the view what to print |

## How to run

### The model on its own (no view)

The model does not import or call `View` or `Controller`, so it runs from tests alone:

1. In Eclipse, right-click `ModelTest.java` and choose Run As, then JUnit Test.
2. Do the same for `QueueTest.java` and `PackageTest.java` to run the Queue and Package tests.

These tests create a `Model`, load it with packages, process them, shelve them, score the day and check the results. No console and no view is involved.

### The thin console view

1. Run `Controller.java` (Run As, then Java Application).
2. Menus use letters. At the opening menu enter `A` to start a new day or `B` to leave.
3. During a day: `A` process the next package, `B` show the shelves, `C` look at a package on a shelf, `D` place a processed package on a shelf, `E` take a package off a shelf, `F` end the day and see your score.
4. While processing a package: `A` weigh, `B` measure, `C` contents, `D` destination, `E` add a stamp (pick a letter from the stamp menu, or `T` to go back), `F` deny the package, `G` finish processing it.
5. Leaving the game prints the score for every day.

`ViewTest` and `ControllerTest` run the view and controller with typed input, no keyboard needed. To run every test at once, right-click the `src` folder and choose Run As, then JUnit Test.

## Reflection

### What changed from the specification?

**Queue and Node.** The spec said singly linked, with `enqueue(null)` returning `false`. Our implementation matches the spec, and the tests check that `enqueue(null)` returns `false`. We added `clear()`, which the spec did not list, so queues can be emptied when needed. `Node` is a private nested class inside `Queue`, matching the spec: only `Queue` can create nodes. 

**Package.**
- The spec called for a `Stamp` class (`addStamp(Stamp)`) and arrays of size 20 for `stampsApplied` and `actualStamps`. We used plain `String` stamp names (`addStamp(String)`), because stamps only need a name, and sized both arrays to 10 slots. `addStamp` returns `false` for null or blank names or when the 10 stamp slots are full. 
- We added a `numStamps` counter and a `viewStamps()` method (not listed in the spec) so shelf inspection can display the stamps applied to a package as a comma-separated list.
- `gradeStamps` follows the spec (1 point per correct stamp, minus 1 point per extra or missed stamp). Unlike the spec's method note claiming an empty `stampsApplied` list returns 0, an unstamped safe package loses 1 point per missed stamp to match the scoring rules. An unsafe package that is processed scores -5, which is in our instructions but was not in the method spec. 

**Model.**
- The spec had `addStampToPackage` return `void`. It returns `boolean` so the controller can tell the player there is no room for an additional stamp.
- The spec did not list `startProcessing`, `denyPackage` or `finishProcessing`, but the MVP flow needs them. `startProcessing` returns `boolean` so an empty queue cannot hand back a null package.
- In the spec, `startDay()` initialized `unprocessedPackages` with random packages and cleared `processedPackages` and `shelves`. Currently, `startDay()` calls `checkQueue()` and clears `currentPackage` without wiping existing queues or shelves. 
- In the spec, `viewShelf(int, int)` returned a package's contents, size, weight, and destination; our implementation calls `viewStamps()` to display the stamps applied to the shelved package.
- `removeFromShelf` fails only for a bad position or an empty slot, as the spec says. 

**Controller.**
- The spec listed `private Controller()` and only a `scanner` field. We made `Controller()` `public` so `main` and tests can instantiate it, stored `view` and `model` as fields, and moved the game loop into `private void run()`. 
- The spec had `readMenuOption()` with no parameters; we changed it to `readMenuOption(int numOptions)` so each menu validates against its own number of valid letters. 
- The spec said `readInt()` returns `-1` on bad input, while the Validation section said it keeps asking until the input is valid. We implemented `public int readIntOption(int min, int max)` to validate range on a single input and return `-1` on invalid input, while the loops in `run()` handle re-prompting until a valid integer is entered. 
- We added a "Go Back" choice (`T`) to the stamp menu so a mistaken click does not force a stamp onto the package.

**View.** The spec's system diagram showed `View<T>`, which we implemented as a non-generic `View` class. Beyond the 6 print methods in the spec, we added prompt and feedback helpers (`printIntError`, `printLeaveGame`, `printReadShelfNumber`, `printReadShelveNumber`, `printShelves`, `printPackage`, `printMessage`) as well as typed print methods (`printWeight`, `printVolume`, `printDestination`, `printContents`, `printStampAdded`, `printScore`, `printFinalScore`). `Controller` calls `printFinalScore` on exit and routes package details and daily scores through `printPackage` and `printMessage`. 

**Where validation lives.** It is where the spec put it: menu letters and numbers are checked in the Controller, and shelf, stamp-capacity and empty-queue checks are in the Model and Package. The one addition is that `Package.addStamp` also rejects null and blank names.

**Tests.** The spec's test plan listed an `OutOfMemoryError` case for the Model constructor and a "package generation fails" case in `addToQueue()`. We cannot force either, so they are not tested directly. The "shelves are null" cases are tested by setting the field to null with reflection. All test classes (`QueueTest`, `PackageTest`, `ModelTest`, `ViewTest`, and `ControllerTest`) extend `student.TestCase`. 

The spec's bad input "trying to run commands after the time system has run out" assumed a timer. There is no timer yet, so we apply it to commands after the day has ended: the day menu only offers Start and Leave, so day commands cannot be entered once the day is over.

### Did the model really stand alone?

Yes for dependencies. `Model`, `Package` and `Queue` never import, create or call `View` or `Controller`, and `ModelTest` exercises all of the game logic without them. Nothing had to move across the line to make it run.

There is one place where the model still knows about presentation. `Model.viewShelves()` builds the ASCII picture of the shelves, and some Model methods return user-facing sentences such as "No package currently being processed" and "Invalid shelf position". The spec asked for these, but they are display decisions that arguably belong to the view. Changing them would mean the model returns data and the view formats it.

### What happened to our unknowns?

We listed two unknowns: how to build a real-time clock for the player, and how to run the clock alongside the main loop while waiting for input. **Both are still open.** We did not attempt the timer in this deliverable, because it is a stretch goal and the MVP did not need it.

### What doesn't work yet?

- No timer or timed day (stretch goal).
- `Model.startDay()` only calls `checkQueue()` once (adding 1 package if fewer than 3 are queued) rather than refilling the queue to 3 packages at the start of a new day, and `Model` does not yet manage how leftover processed or shelved packages carry over between days.
- Only the first 30 processed packages are scored, but there is no visible "vehicle full" step.
- Denying a package is free. Denying a safe package costs nothing and denying an unsafe package earns nothing, so denying everything scores 0.
- The MVP says stamping happens before the user continues, but "finish" is allowed with zero stamps.
- Untested cases: the Model constructor `OutOfMemoryError` case and forcing package generation to fail.

## Next Steps

### What is left to finish the MVP, in order

1. Update `Model.startDay()` so a new day ensures `unprocessedPackages` is filled up to 3 packages while cleanly carrying over leftover packages from the previous day (`Model.startDay`, `Model.calculateScore`).
2. Add a score effect for denying packages: a penalty for denying a safe package and a small reward for correctly denying an unsafe one (`Model.denyPackage`, `Package.isSafe`). This closes the "deny everything" loophole.
3. Show a clear end-of-day summary: how many packages were scored and how many are left over (`View`, `Controller.run`).
4. Decide whether finishing with no stamps should be blocked, and update tests to match.
5. Run all tests in Eclipse and fix style warnings.

### Our change feature: priority processing

A customer drops off a priority package in person and asks for it to be processed first. The package goes to the **front** of the unprocessed queue instead of the back, so the next package the player processes is the priority one. This turns our queue into a deque and is already described in our scope.

**Predicted to change:**
- `Queue`: new `enqueueFront(T)` method (and `QueueTest`). `dequeue`, `peek`, `size` and `isEmpty` should not need to change.
- `Model`: new `addPriorityPackage()`, which makes a package and calls `enqueueFront`. `startProcessing()` should not change, because it already takes the front package.
- `Controller`: `run()` gets a new menu branch, and the option count passed to `readMenuOption` for the game menu goes from 6 to 7.
- `View`: `printGameMenu()` gets a new option, and a new `printPriorityArrival()` method announces the arrival.

**Predicted not to change:** `Package` (a priority package is an ordinary package), `Queue.Node`, `Model.calculateScore`, `Package.gradeStamps`, the shelf methods, and the Model/View separation (the model still never calls the view).

### Stretch goals

- **Committing to:** priority processing, priority shipping, shipments per location, timing challenge
- **Dropping:** sorting shelves by rule

### New tests needed

- `Queue.enqueueFront`: on an empty queue; on a non-empty queue (new item comes out first, order of the rest unchanged); null returns `false` and changes nothing; mixing `enqueue` and `enqueueFront`; size updates.
- `Model.addPriorityPackage`: unprocessed count goes up by one, and the next `startProcessing` returns the priority package; works on an empty queue.
- `Controller`: the new menu letter works, invalid letters beyond it still show the error, and the priority package really is next.
- `View`: the updated game menu and the arrival message.
- Bad input: priority arrival when the day is not started, and many priority packages in a row.

### Who does what

| Item | Owner |
| --- | --- |
| `Queue.enqueueFront` and its tests | Nikita |
| `Model.addPriorityPackage`, carry-over of leftover packages, deny scoring and their tests | Sarah |
| `Controller` and `View` changes, end-of-day summary, `ControllerTest` and `ViewTest` updates | Max |
| Final README update | All |

## Attribution

| File | Who | How |
| --- | --- | --- |
| `Queue.java` (includes nested `Node`) | Nikita | Wrote and revised. |
| `QueueTest.java` | Sarah | Wrote and revised. |
| `Package.java` | Sarah | Wrote and revised. |
| `PackageTest.java` | Nikita | Wrote and revised. |
| `Model.java` | Sarah | Wrote and revised.|
| `ModelTest.java` | Sarah | Wrote and revised.|
| `View.java` | Max | Wrote and revised. |
| `ViewTest.java` | Max | Wrote and revised. GenAI-assisted. |
| `Controller.java` | Max | Wrote and revised. |
| `ControllerTest.java` | Max | Wrote and revised. GenAI-assisted. |

### GenAI note

We used GenAI in 4 ways:

1. **Project name.** We prompted it for game title suggestions and kept "Precision Post".
2. **Spec review.** We had it review our spec for logic errors and missing MVP functionality.
3. **View and Controller Tests.** It helped with writing tests for the view and controller which needed to read and write to the console.
4. **Logic Errors.** We prompted it to find logic errors and updated our code accordingly.
