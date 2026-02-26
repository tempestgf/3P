# 3P Architecture Design

## Architectural Choices

The 3P application follows the **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern. This ensures separation of concerns, testability, and scalability.

- **UI Layer (`app/ui`)**: Contains Jetpack Compose screens and ViewModels.
- **Domain Layer (`app/domain`)**: Contains business logic, use cases, and domain models.
- **Data Layer (`app/data`)**: Contains repositories, local database (Room), and remote API clients.

## Data Model Diagram

Below is the domain model diagram representing the core entities of the application.

```mermaid
classDiagram
    class User {
        +String id
        +String name
        +String email
        +UserPreferences preferences
        +authenticate()
    }
    
    class UserPreferences {
        +Boolean darkMode
        +Boolean notificationsEnabled
        +String currency
    }
    
    class Trip {
        +String id
        +String title
        +String destination
        +Date startDate
        +Date endDate
        +Boolean isArchived
        +getDurationInDays() Int
    }
    
    class ItineraryDay {
        +String id
        +String tripId
        +Date date
        +List~Activity~ activities
        +sortActivities()
    }
    
    class Activity {
        +String id
        +String title
        +String description
        +String startTime
        +String endTime
        +String location
        +Double cost
        +isValidTimeRange() Boolean
    }

    User "1" -- "1" UserPreferences : has
    User "1" -- "*" Trip : creates
    Trip "1" -- "*" ItineraryDay : contains
    ItineraryDay "1" -- "*" Activity : includes
```
