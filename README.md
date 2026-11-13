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

**Queue and Node.** The spec said singly linked, with `enqueue(null)` returning `false`. Our first implementation had a `prev` pointer (doubly linked) and threw an exception on null. We changed it back to match the spec, and the tests now check that `enqueue(null)` returns `false`. We added `clear()`, which the spec did not list, so `Model.startDay()` can reset a queue. `Node` is a private nested class inside `Queue`, which still matches the spec: only `Queue` can create nodes.

**Package.**
- The spec called for a `Stamp` class (`addStamp(Stamp)`). We used plain `String` stamp names, because stamps only need a name and a `Stamp` class would have added nothing but a wrapper. `addStamp` returns `false` for null or empty names or when the 20 stamp slots are full.
- We added two constructors (`Package(Random)` and one taking explicit values) so tests can build known packages and seeded games repeat exactly. We added `isSafe()` and `getNumStamps()`.
- `gradeStamps` follows the spec (point per correct stamp, minus a point per extra or missed stamp, 0 if nothing was stamped). An unsafe package that is processed scores -5, which is in our instructions but was not in the method spec.
- Bugs fixed while rewriting: stamps were never counted, the "keep dry" list was misaligned with the contents list, one safe item made a whole package safe, `" Alexandria"` had a leading space, and grading changed the package so a second call gave a different answer.

**Model.**
- The spec had `addStampToPackage` return `void`. It returns `boolean` so the controller can tell the player there is no room.
- The spec did not list `startProcessing`, `denyPackage` or `finishProcessing`, but the MVP flow needs them. `startProcessing` returns `boolean` so an empty queue cannot hand back a null package.
- We added small read-only methods (`getUnprocessedCount`, `getProcessedCount`, `hasCurrentPackage`) and a `Model(Random)` constructor so tests and the controller can check state. Two package-private helpers (`enqueueUnprocessed`, `enqueueProcessed`) exist only so tests can load known packages.
- `removeFromShelf` used to fail whenever the processed queue was empty, even if the slot held a package. It now fails only for a bad position or an empty slot, as the spec says.

**Controller.**
- The spec said `readInt()` returns -1 on bad input, but the Validation section said it keeps asking until the input is valid. We followed the Validation section: `readIntOption(min, max)` re-prompts until it gets a number in range.
- `readMenuOption(numOptions)` takes the number of valid letters. It originally accepted one letter too many.
- The game now runs in `run()` instead of inside the constructor, so tests can create a controller without starting the game.
- We added a "Go Back" choice (`T`) to the stamp menu so a mistaken click does not force a stamp onto the package.

**View.** We added the typed print methods (`printWeight`, `printVolume`, `printDestination`, `printContents`, `printStampAdded`, `printScore`, `printFinalScore`, `printMessage`) so the controller does not build output text itself. They replace the generic `printMisc`.

**Where validation lives.** It is where the spec put it: menu letters and numbers are checked in the Controller, and shelf, stamp-capacity and empty-queue checks are in the Model and Package. The one addition is that `Package.addStamp` also rejects null and empty names.

**Tests.** The spec's test plan listed an `OutOfMemoryError` case for the Model constructor and a "package generation fails" case. We cannot force either, so they are not tested directly (the second is handled by a try/catch in `addToQueue`). The "shelves are null" cases are tested by setting the field to null with reflection. We added tests for every class, including `PackageTest` and `ModelTest`, and replaced the old `student.TestCase`-based `QueueTest` with JUnit 5 like the other tests.

The spec's bad input "trying to run commands after the time system has run out" assumed a timer. There is no timer yet, so we apply it to commands after the day has ended: the day menu only offers Start and Leave, so day commands cannot be entered once the day is over.

### Did the model really stand alone?

Yes for dependencies. `Model`, `Package` and `Queue` never import, create or call `View` or `Controller`, and `ModelTest` exercises all of the game logic without them. Nothing had to move across the line to make it run.

There is one place where the model still knows about presentation. `Model.viewShelves()` builds the ASCII picture of the shelves, and some Model methods return user-facing sentences such as "No package currently being processed" and "Invalid shelf position". The spec asked for these, but they are display decisions that arguably belong to the view. Changing them would mean the model returns data and the view formats it.

### What happened to our unknowns?

We listed two unknowns: how to build a real-time clock for the player, and how to run the clock alongside the main loop while waiting for input. **Both are still open.** We did not attempt the timer in this deliverable, because it is a stretch goal and the MVP did not need it.

### What doesn't work yet?

- No timer or timed day (stretch goal).
- Packages left unprocessed or unscored at the end of a day are not carried to the next day. `startDay` clears them, although the MVP says leftovers should be stored for tomorrow.
- Only the first 30 processed packages are scored, but there is no visible "vehicle full" step.
- Denying a package is free. Denying a safe package costs nothing and denying an unsafe package earns nothing, so denying everything scores 0.
- The MVP says stamping happens before the user continues, but "finish" is allowed with zero stamps.
- Untested cases: the Model constructor `OutOfMemoryError` case and forcing package generation to fail.

## Next Steps

### What is left to finish the MVP, in order

1. Carry unprocessed and unscored packages over to the next day, so leftovers are kept instead of cleared (`Model.startDay`, `Model.calculateScore`).
2. Add a score effect for denying packages: a penalty for denying a safe package and a small reward for correctly denying an unsafe one (`Model.denyPackage`, `Package.isSafe`). This closes the "deny everything" loophole.
3. Show a clear end-of-day summary: how many packages were scored and how many are left over (`View`, `Controller.playDay`).
4. Decide whether finishing with no stamps should be blocked, and update tests to match.
5. Run all tests in Eclipse and fix style warnings.

### Our change feature: priority processing

A customer drops off a priority package in person and asks for it to be processed first. The package goes to the **front** of the unprocessed queue instead of the back, so the next package the player processes is the priority one. This turns our queue into a deque and is already described in our scope.

**Predicted to change:**
- `Queue`: new `enqueueFront(T)` method (and `QueueTest`). `dequeue`, `peek`, `size` and `isEmpty` should not need to change.
- `Model`: new `addPriorityPackage()`, which makes a package and calls `enqueueFront`. `startProcessing()` should not change, because it already takes the front package.
- `Controller`: `playDay()` gets a new menu branch, and `GAME_OPTIONS` goes from 6 to 7.
- `View`: `printGameMenu()` gets a new option, and a new `printPriorityArrival()` method announces the arrival.

**Predicted not to change:** `Package` (a priority package is an ordinary package), `Queue.Node`, `Model.calculateScore`, `Package.gradeStamps`, the shelf methods, and the Model/View separation (the model still never calls the view).

### Stretch goals

- **Committing to:** priority processing (our change feature).
- **Dropping:** the timing challenge, priority shipping, in-person package pickup, shipments per location, and sorting shelves by rule. The first three need either the clock (still an open unknown) or more shelf logic than we have time for.

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
| `Queue.java` (includes nested `Node`) | Nikita | Wrote and revised. GenAI-assisted. |
| `QueueTest.java` | Sarah | Wrote and revised. GenAI-assisted. |
| `Package.java` | Sarah | Wrote and revised. GenAI-assisted. |
| `PackageTest.java` | Nikita|
| `Model.java` | Sarah | Wrote and revised. GenAI-assisted. |
| `ModelTest.java` | Sarah. |
| `View.java` | Max | Wrote and revised. GenAI-assisted. |
| `ViewTest.java` | Max | Wrote and revised. GenAI-assisted. |
| `Controller.java` | Max | Wrote and revised. GenAI-assisted. |
| `ControllerTest.java` | Max | Wrote and revised. GenAI-assisted. |

### GenAI note

We used GenAI in 3 ways:

1. **Project name.** We prompted it for game title suggestions and kept "Precision Post".
2. **Spec review.** We had it review our spec for logic errors and missing MVP functionality.
3. **Early code help.** It helped with the class implementation and with writing some tests.
