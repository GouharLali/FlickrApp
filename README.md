# Flickr App

Flickr App is an Android application developed using Jetpack Compose for fetching and displaying list of photos from Flickr.

## Features

- Search for photos by username or tags
- Displaying a list of photos with their titles, tags, and date taken
- Click on a photo to view more details

## Screenshots

<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/c91dc791-b3c7-4c5a-9a46-97354238a99c" alt="firstScreen" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/80df759e-63c2-47dc-abf0-915513758f87" alt="seven" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/2564ca14-6a61-4c0b-984a-b027b8d7408b" alt="Second" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/df2e64da-715e-4759-8f48-366f47c75c44" alt="three" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/5a9d2a1c-3691-4e9a-bf4d-cb926d813b00" alt="four" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/c1044b00-14c4-4ab3-9c94-74bbf96d2c16" alt="five" width="30%" height="auto"/>  
<img src="https://github.com/GouharLali/FlickrApp/assets/94018886/756753a1-e015-42dd-b5f8-5748209037d4" alt="six" width="30%" height="auto"/>

## Getting Started

To get started with this project, follow these steps:

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Technologies Used

- Jetpack Compose: Modern toolkit for building native Android UI
- Retrofit: HTTP client for making network requests
- Room: SQLite object mapping library for local database operations
- Coil: Image loading library for displaying images efficiently
- Navigation Component: Android Jetpack library for navigation between screens

## Components

### FlickrRepository

Handles fetching photos from the Flickr API and saving them to the local database.

### FlickrService

Retrofit service interface for defining API endpoints.

### FlickrViewModel

ViewModel responsible for managing UI-related data and interactions. Follows the MVVM (Model-View-ViewModel) architecture.

### MainScreen

The main screen of the app where users can search for photos.

### UserPhotosScreen

Screen for displaying photos belonging to a specific user.

### DetailScreen

Screen for displaying detailed information about a photo.

### CenteredProgressBar

A reusable component for displaying a centered progress indicator.

## Implementation Decisions

### Architecture

The application follows a clean architecture pattern, separating concerns between UI, data, and business logic.

### UI Components

- RecyclerView: Utilized for efficiently displaying photos in a list.
- Custom Views: Custom view components for enhancing the visual appeal of the app.

### Networking

- Retrofit: Used for making network requests to the Flickr API.

### Database

- Room: Utilized for local database operations to store photo data.

## Setup

To run the application, make sure you have Android Studio installed. Clone the repository and open the project in Android Studio. Run the app on an emulator or physical device.
