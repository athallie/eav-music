# Eav Music  

Eav Music is a simple desktop music program utilizing JavaFX. It can index, play, pause, stop, and search for music. This program was created as part of my **Algorithms and Data Structures** course, showcasing my understanding and ability to implement several data structures, primarily:  

1. **TableView**: A view that utilizes a 2-dimensional list/array-like structure to store data in a table format.  
2. **ObservableList**: A list structure implementation that tracks live data changes.  
3. **LinkedList**: A list structure implementation that stores data as interconnected nodes.  
4. **FilteredList**: A list structure specialized for data filtering.  
5. **SortedList**: A list structure specialized for data sorting.  

In addition to these data structures, the program demonstrates two key algorithms: **searching** and **sorting**.  
- The **search algorithm** is implemented using the `FilteredList` class to conduct music searches based on artist, album, or song title.  
- The **sorting algorithm** is implemented using the `SortedList` class to sort music based on one of the music properties mentioned above.  

## Prerequisites  
To run this program, you need to have **Eclipse Temurin version 20.0.2.9** or higher installed. It must also be set as the `JAVA_HOME` environment variable on Windows. Please refer to other resources for setup instructions on other platforms.  

## Installation  
1. Download and unzip the source code, or clone the repository.  
2. Run the `mvnw` file in the source code with the `clean` option to build and execute the program. Example in PowerShell:  
    ```
    ./mvnw clean javafx:run
    ```

## Usage  

### UI Structure  
Before using the program's features, it's important to understand the UI structure. The UI consists of five interactive components, grouped by position: top, middle, and bottom.  

- **Top Section**: Contains the `Add Folders` and `Refresh` buttons, as well as the `Search` field.  
- **Middle Section**: Contains the table view displaying the indexed music. The columns and rows in the table are interactive.  
- **Bottom Section**: Contains the control buttons: `Pause`, `Play`, and `Stop`.  

### Adding Music  
To add music, select a folder containing music files (or subfolders with music files). Open the file dialog using the `Add Folders` button and choose a folder.  

### Refreshing the Music List  
If some of the indexed music has incorrect or default properties (e.g., a duration of 0), you can click the `Refresh` button to reindex and fix the list. This also helps if some music files were not initially added.  

### Playing Music  
You can play music in two ways:  
1. Double-click a song row.  
2. Select a song row and click the `Play` button.  

### Pausing Music  
To pause music, click the `Pause` button. To resume playback, click the `Play` button again.  

### Stopping Music  
To stop music, click the `Stop` button.  

### Searching for Music  
Use the `Search` field to find specific music. You can search by keywords such as artist name, album name, or song title.  

### Sorting Music  
Sort music by artist, album, title, or duration by clicking the corresponding column header in the table view.  

## Future Plans  
I plan to rewrite this program using a different framework, with a focus on multiplatform solutions such as **Jetpack Compose**.  
