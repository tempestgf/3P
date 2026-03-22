# Sprint 02 Retrospective

## What was accomplished
- Successfully implemented an InMemory database (\FakeTripDataSource\) to manage CRUD operations for trips and activities.
- Handled all required validations for times and dates correctly using built-in date pickers.
- Replaced older placeholder screens with a fully designed and working user flow containing Lists, Details, Edit, and Add screens for Trips and Activities.
- Implemented and successfully applied SharedPreferences to remember User Settings, like language and user's theme selection across app reboots.
- Tested the CRUD operations effectively with JUnit tests inside \TripRepositoryTest.kt\.

## Test Results and Fixes Applied (T3.4)
- Ran JUnit tests targeting \TripRepositoryImpl\ and internal logic.
- **Failures found initially**: Some test boundaries allowed overlapping activities, and incorrect naming conventions were breaking compile cycles.
- **Fixes applied**: Tightened validation functions inside \TripListViewModel.kt\ and properly formatted unit test methods leveraging proper \CamelCase\ instead of backtick spacing that failed Kotlin test wrappers.
- **Status Status**: **100% Pass** for all 6 core methods (addTrip, editTrip, deleteTrip, addActivity, updateActivity, deleteActivity).

## What went well
- Applying MVVM greatly helped structure data sources logically separate from the UI.
- The pre-existing sprint 01 navigation was mostly scalable to encompass the new flow natively.

## What can be improved
- The repository integration currently scales well for an InMemory DB, but we foresee requiring architectural tweaks when upgrading to real persistence later on.
