# FW Code Test Android

## Goals
-  test knwledge of retrofit (custom converter factories, custom call adapter factories)
-  knwoledge of Realm / Room
-  ability to code a feature from the data layer to the UI layer
-  ability to respect existing conventions(style & architecture)
-  ability to test the code he writes
-  knwoledge of animation & transition techniques
-  ability to document code(especially in situations where it can create technical debt)

## Summary
Candidates will receive a project that uses FW architecture choices and 3rd party dependencies that are most common to our daily use. On this project, he will have to complete a series of tasks that aim to validate his knowledge in the areas described above.

Basically, we're using the olde code test format but improve it in order to remove the difficulties of setting up Twitter SDK or choosing an architecture pattern and focus on the coding skills and overall software development competencies.

## Project details

A simple project that:
 - has a login screen (credentials will be hardcoded in a fake server)
 - displays a list of `Items`
 -  navigates to an Item detail screen
 -  from the details screen, users can mark an item as favorite / unfavourite
 -  favourite items are available offline, in a separate tab on the home screen

### What will be implemented by us
 - project structure with MVP architecture and Dagger
 - fake server that provides custom data
 - network data source that uses the fake server
 - login screen(partially)
 - item list screen

### Candidate tasks

#### 1. Implement a mechansim that propagates server errors to the UI

Our fake server needs to throw an error like: `{"error": "Email is not registered"}`. The candidate needs to implement a mechanism that will display this error message to the user via a Toast/ Snackbar.

Expectations:

- create a separate model for the exceptions coming from the server
-  create a domain exception model
-  create a custom exception
-  create a custom `CallAdapterFactory` that can propagate our custom exception to the repository level
-  repository should forward a domain exception model to interactors / presenters
- view should have separate methods for displaying errors

#### 2. Implement a mechansim that parses JSON and XML*

The fake server needs to have an endpoint that will send XML data instead of JSON. The option to trigger the XML call will probably be placed in a menu item so we don't interfere with the general app flow.

Candidates have to create a mechanism that is able to distinguish between the received content types and use the correct converter factory

This tasks tests the knowledge of Retrofit as it requires candidates to deal with an unusual situation when working with a very common library.

Ex:

- `<endpoint>/items` - returns JSON content
- `<endpoint>/recommandations` - returns XML content

Expectations:

- candiates can implement a working solution

Ideal solution :

- use custom annotations on the Retrofit service interface to determine content type
-  create a custom converter factory that uses GSON and SimpleXML internally
-  determine type of required content by checking the annotation and forward call to the correct converter factory

#### 3. Implement local storage

Candidates need to implement local storage for holding favourite `Items`. They can use `Realm` or `Room`.

Expectations:

- separate model used for local storage
- use of Mappers for converting between domain model and persistence model
-  use of a wrapper over the persitence library(to make it easier to change the technology without upstream changes)
-   use of the persistence wrapper only at repository level (no presenter/interactor)


#### 4. Update Item details screen

Update item details screen to include functionality required to mark an item favourite.

Expectations:

- wire UI button to mark an Item as favourite / unfavourite
- attention to UI components
- no hardcoded values in xml layout files
- unit test for the presenter


#### 5. Implement favourite Items screen

Implement a simple screen that shows a list of favourite Items

Expectations:

- follow existing packaging strategy
- create MVP components
- Dagger module to inject dependencies
- RecyclerView and adapter to show favourite items
- unit test for the presenter

 ```(*)``` this is an optional task aimed at senior / lead level candidates

## Extra points

- using SharedElement transition when showing Item details screen
- using AnimatedVectorDrawable for favourite / unfavourite actions
- using overflow to dismiss ItemDetails screen
- documenting design / implementation decisions in a README


## Test strategy

All candidates will receive the same test and will be asked to complete as much tasks as they can  in the given time frame.

The test project will be written in Java but candidates are free to use Kotlin if they wish.

## Scoring

Each task(except for the advanced one) will receive a score from 1-10.
Bonus tasks each add an extra 3 points
Advanced Retrofit tasks add 4 points.

To calculate the overall score, you sum the score from the completed tasks,add any points obtained from extra / advanced section and divide the whole thing to the number of completed tasks.

This can be changed in the future as it does not affect the implementation of the code test.

