---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" alt="Architectural diagram"/>

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" alt="Architecture Sequence Diagram"/>

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `FoodListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/resources/view/MainWindow.fxml).

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/logic/Logic.java)

1. `Logic` uses the `McGymmyParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a food item).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the McGymmy data.
* exposes an unmodifiable `ObservableList<Food>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in `McGymmy`, which `Food` references. This allows `McGymmy` to only require one `Tag` object per unique `Tag`, instead of each `Food` needing their own `Tag` object.

<br>

![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W17-3/tp/tree/master/src/main/java/jimmy/mcgymmy/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the McGymmy data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.mcgymmy.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `ModelManager`. 
It stores multiple versions of `McGymmy` in a stack, with the most recent version on top.
Additionally, it implements the following operations:

* `ModelManager#canUndo()` - Checks if there are any older McGymmy states.
* `VersionedMcGymmy#commit()` — Saves the current McGymmy state in its history.
* `VersionedMcGymmy#undo()` — Restores the previous McGymmy state from its history.
* `VersionedMcGymmy#redo()` — Restores a previously undone McGymmy state from its history.

These operations are exposed in the `Model` interface as `Model#commitMcGymmy()`, `Model#undoMcGymmy()` and `Model#redoMcGymmy()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedMcGymmy` will be initialized with the initial McGymmy state, and the `currentStatePointer` pointing to that single McGymmy state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th food item in the McGymmy. The `delete` command calls `Model#commitMcGymmy()`, causing the modified state of the McGymmy after the `delete 5` command executes to be saved in the `mcGymmyStateList`, and the `currentStatePointer` is shifted to the newly inserted McGymmy state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/Rice …​` to add a new food item. The `add` command also calls `Model#commitMcGymmy()`, causing another modified McGymmy state to be saved into the `mcGymmyStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitMcGymmy()`, so the McGymmy state will not be saved into the `mcGymmyStateList`.

</div>

Step 4. The user now decides that adding the food item was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoMcGymmy()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous McGymmy state, and restores the McGymmy to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial McGymmy state, then there are no previous McGymmy states to restore. The `undo` command uses `Model#canUndoMcGymmy()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoMcGymmy()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the McGymmy to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `mcGymmyStateList.size() - 1`, pointing to the latest McGymmy state, then there are no undone McGymmy states to restore. The `redo` command uses `Model#canRedoMcGymmy()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the McGymmy, such as `list`, will usually not call `Model#commitMcGymmy()`, `Model#undoMcGymmy()` or `Model#redoMcGymmy()`. Thus, the `mcGymmyStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitMcGymmy()`. Since the `currentStatePointer` is not pointing at the end of the `mcGymmyStateList`, all McGymmy states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/Rice …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo & redo executes

* **Alternative 1 (current choice):** Saves the entire McGymmy database.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the food item being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage food intake
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* is sedentary people who sit in front of their computers

**Value proposition**: 
* manage food intake faster than a typical mouse/GUI driven app
* reduce the risk of health issues for people with an unhealthy lifestyle
* help them develop a healthy lifestyle through diet
* help them track their calorie intake
* find trends in their eating habits
* profile setting for them to store different settings


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a/an …​                                  | I want to …​                            | So that I can…​                                                         |
| -------- | ------------------------------------------ | -------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions                 | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | track what I am eating                 |                                                                        |
| `* * *`  | user                                       | delete a food item                     | remove food items that I no longer care of                             |
| `* * *`  | user                                       | easily add food items/details          | keep track of my diet                                                  |
| `* *`    | frequent user                              | perform tasks on a group of food items |                                                                        |
| `* *`    | expert user                                | create shortcuts for tasks             | frequently performed tasks                                             |
| `*`      | programmer                                 | make use of the CLI like design        | get used to CLI in the future                                          |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is `McGymmy` and the **Actor** is the `user`, unless specified otherwise)


**Use case: UC01 List food**

**MSS**
1. User requests to list food (UC01)
2. McGymmy shows a list of food that user has added (UC02)
Use case ends

**Use case: UC02 Add food**

**MSS**
1. User requests to add food into the list
2. McGymmy adds the food item into the list

Use case ends.

**Extensions**
- 1a. The format of the add method is invalid
    1a1. McGymmy shows an error message
    Use case ends.

**Use case: UC03 Delete food**

**MSS**
1. User requests to list food (UC01)
2. McGymmy shows a list of food
3. User request to delete a specific food on the list
4. McGymmy deletes the food

Use case ends

**Extensions**
- 2a. The list is empty<br>

    Use case ends.
    
- 3a. The given index is invalid.<br>
   - 3a1. McGymmy shows an error message.
    
    Use case resumes at step 2.

**Use case: UC04 Help**

**MSS**
1. User requests help
2. McGymmy shows all commands and examples of command usages

Use case ends

**Extensions**
No extensions

**Use case: UC05 Update food**

**MSS**
1. User requests to list food (UC01)
2. McGymmy shows a list of food
3. User request to update a specific food on the list
4. McGymmy updates the food

Use case ends

**Extensions**
- 2a. The list is empty<br>
    Use case ends.
- 3a. The given index is invalid.<br>
   - 3a1. McGymmy shows an error message
    Use case resumes at step 2.

**Use case: UC06 Add a macro command**

**MSS**

1. User creates a macro to execute two 'add' commands in sequence
2. McGymmy adds the macro to the list of available commands
3. User uses the newly added macro command
4. McGymmy executes the two commands consecutively

Use case ends

**Extensions**

 - 1a. The format of the macro is invalid.
    - 1a1. McGymmy shows an error message.

Use case ends.

 - 4a. One of the executed commands encounter an error.
    - 4a1. McGymmy shows the error message from that command, and displays the commands that successfully executed, and the commands that have yet to execute.

Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 food items without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The product should be for a single user i.e. (not a multi-user product).
5. The data should be stored locally and should be in a human editable text file.
6. The software should not depend on a private remote server.
7. The size of the final compiled JAR file should be less than 100Mb.
8. There should be CLI alternatives to every GUI input. (E.g. instead of clicking on a button I can type an equivalent command to achieve the same result.)

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private diet detail**: Diet details that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file
      <br>
      Expected: Shows the GUI with a set of sample food items. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.
      <br>
      Expected: The most recent window size and location is retained.

### Deleting food items

1. Deleting a food item while all food items are shown.

   1. Prerequisites: List all food items using the `list` command. Multiple food items in the list.

   1. Test case: `delete 1`<br>
      Expected: First food item is deleted from the list. Details of the deleted food item shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No food item is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


### Saving data

1. Dealing with missing/corrupted data files

   1. Delete the 'data' file if any. Relaunch the app by double-clicking the jar file. 
   <br>
   Expected: A new data file is generated
   
   1. Open the data file inside the `data` folder using any text editor and edit the file.
   <br>
   Expected: A new empty data file is generated which overwrites the old one.

