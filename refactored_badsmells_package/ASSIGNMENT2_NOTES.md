# Assignment 2 Refactoring Notes

## AlternativeClassesWithDifferentInterfacesExample.java

**Original smell:** ZoomClassroom and TeamsClassroom serve the same purpose but expose different APIs, so clients cannot use them uniformly.

**Refactorings applied:**
- Introduced a shared Classroom abstraction
- Aligned client code around startSession()
- Adapted TeamsClassroom with an adapter

**Why the result is better:** Client code now depends on one protocol instead of concrete mismatched classes, which lowers coupling and improves substitutability.

## CommentsExample.java

**Original smell:** The comments were compensating for code that was not clear enough on its own.

**Refactorings applied:**
- Extracted intention-revealing methods
- Renamed the main method and variables

**Why the result is better:** The pricing steps are now visible in the code structure, so the comments are no longer needed.

## DataClassExample.java

**Original smell:** StudentRecord was just a bag of public fields while the real behavior lived elsewhere.

**Refactorings applied:**
- Encapsulated StudentRecord state
- Moved honors, discount, and standing behavior into StudentRecord

**Why the result is better:** The data holder now owns its behavior, which improves cohesion and encapsulation.

## DataClumpsExample.java

**Original smell:** The same cluster of name, email, and phone was passed around repeatedly as separate values.

**Refactorings applied:**
- Introduced ContactInfo
- Moved label, greeting, SMS, and reachability behavior onto ContactInfo

**Why the result is better:** The repeated value group is now modeled as one concept, which reduces noisy APIs.

## DivergentChangeExample.java

**Original smell:** One class handled reporting, SQL generation, and CSV export, so it changed for unrelated reasons.

**Refactorings applied:**
- Extracted StudentReportFormatter
- Extracted ExamResultSqlBuilder
- Extracted CsvExporter

**Why the result is better:** Each class now changes for one reason, which reduces risk when requirements change.

## DuplicatedCodeExample.java

**Original smell:** Summer and winter invoice methods repeated the same tax and shipping logic.

**Refactorings applied:**
- Extracted shared invoice calculation
- Kept only seasonal discount logic separate

**Why the result is better:** The common pricing logic now has a single source of truth.

## FeatureEnvyExample.java

**Original smell:** ScholarshipCalculator mostly used StudentAccount data and added little of its own.

**Refactorings applied:**
- Moved qualification logic into StudentAccount

**Why the result is better:** Behavior now lives with the data it depends on most.

## GlobalDataExample.java

**Original smell:** Public static state could be read or changed from anywhere, making ownership unclear.

**Refactorings applied:**
- Introduced AcademicSettings
- Encapsulated reads and updates behind methods
- Injected settings into services

**Why the result is better:** Shared state is now explicit and controlled instead of being globally reachable.

## InsiderTradingExample.java

**Original smell:** AuditService manipulated BankAccount internals directly, so the two classes knew too much about each other.

**Refactorings applied:**
- Encapsulated BankAccount fields
- Moved freezing logic into BankAccount
- Kept AuditService at a higher level

**Why the result is better:** BankAccount now protects its own invariants and coupling is lower.

## LargeClassExample.java

**Original smell:** One class mixed enrollment, staffing, finance, facilities, support, website, and payroll responsibilities.

**Refactorings applied:**
- Split the class into focused nested services
- Moved related state and behavior together by domain

**Why the result is better:** Responsibilities are clearer and future changes are more localized.

## LazyElementExample.java

**Original smell:** StudentNameFormatter added almost no value beyond a single trim operation.

**Refactorings applied:**
- Inlined the trivial helper class into the caller

**Why the result is better:** The design is simpler because unnecessary structure was removed.

## LongFunctionExample.java

**Original smell:** One method handled discount, shipping, tax, approval, and formatting in one block.

**Refactorings applied:**
- Extracted helper methods for each subtask
- Introduced OrderRequest as a parameter object

**Why the result is better:** The main workflow now reads clearly at a high level, and the calculations are isolated.

## LongParameterListExample.java

**Original smell:** The registration method accepted many related raw values instead of meaningful objects.

**Refactorings applied:**
- Introduced Address
- Introduced GuardianContact
- Wrapped the data in StudentRegistration

**Why the result is better:** The method signature now reflects domain concepts instead of a long list of primitives.

## LoopsExample.java

**Original smell:** The loop hid a simple filter-and-map collection transformation.

**Refactorings applied:**
- Replaced the loop with a stream pipeline
- Extracted the GPA check into a named method

**Why the result is better:** The intent of the transformation is clearer and easier to read.

## MessageChainsExample.java

**Original smell:** The caller navigated through several objects to reach one value, which exposed internal structure.

**Refactorings applied:**
- Added simpler delegate methods
- Let University expose getCoordinatorPhoneNumber()

**Why the result is better:** Client code now depends on a simpler abstraction and follows the Law of Demeter more closely.

## MiddleManExample.java

**Original smell:** StudentPortal mostly forwarded calls to TranscriptService without adding value.

**Refactorings applied:**
- Removed the extra forwarding layer from the client path

**Why the result is better:** The code is simpler because the client talks directly to the real service.

## MutableDataExample.java

**Original smell:** The internal student list was returned directly, so callers could mutate object state from the outside.

**Refactorings applied:**
- Returned an unmodifiable view
- Added an intention-revealing count method

**Why the result is better:** The object now controls its own state. This is the one example where the visible client behavior changes intentionally to remove the bug.

## MysteriousNameExample.java

**Original smell:** Names like f, a, b, c, x, and y hid the calculation's intent.

**Refactorings applied:**
- Renamed the method and variables
- Extracted named helper methods

**Why the result is better:** The code can now be read directly instead of reverse-engineered.

## PrimitiveObsessionExample.java

**Original smell:** Age, status, debt, and country were represented as unrelated primitives instead of domain concepts.

**Refactorings applied:**
- Replaced strings with enums
- Introduced StudentAge and Money value objects

**Why the result is better:** The code now expresses domain meaning more clearly and avoids invalid raw values.

## RefusedBequestExample.java

**Original smell:** Penguin inherited fly() even though it could not support that behavior.

**Refactorings applied:**
- Introduced a FlyingBird subtype
- Moved fly() only to birds that actually fly

**Why the result is better:** The hierarchy now matches real behavior and avoids a misleading inheritance contract.

## RepeatedSwitchesExample.java

**Original smell:** The same student-type classification logic was duplicated across multiple switch statements.

**Refactorings applied:**
- Centralized the type-specific behavior in one enum

**Why the result is better:** Adding or changing a student type now happens in one place instead of many switches.

## ShotgunSurgeryExample.java

**Original smell:** A single wording change for course titles would require edits in several different classes.

**Refactorings applied:**
- Introduced CoursePresentation as a single source of truth
- Delegated wording to that object

**Why the result is better:** One conceptual change is now localized instead of scattered.

## SpeculativeGeneralityExample.java

**Original smell:** The abstraction carried unused parameters for imagined future needs.

**Refactorings applied:**
- Removed unused parameters from the interface
- Simplified the channel to current behavior

**Why the result is better:** The design now reflects real needs instead of hypothetical variation.

## TemporaryFieldExample.java

**Original smell:** The object had fields that only mattered in certain modes, leaving partially irrelevant state around.

**Refactorings applied:**
- Extracted separate onsite and online preparation classes
- Moved mode-specific fields to the class that uses them

**Why the result is better:** Each object now has a consistent state instead of temporary fields.
