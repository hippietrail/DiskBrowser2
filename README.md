# Disk Browser ][
This is a major rewrite of [DiskBrowser](https://github.com/dmolony/diskbrowser), using JavaFX and the new libraries ([AppBase](https://github.com/dmolony/AppBase), 
[AppleFileSystem](https://github.com/dmolony/AppleFileSystem) and 
[AppleFormat](https://github.com/dmolony/AppleFormat)). It is currently in beta release.
  
The goal is for DiskBrowser ][ to retain all the file display formats of DiskBrowser, but  with a better interface and a more maintainable code base.

## Beta Release
The current version should be considered beta software.

## Installation
- Download and install the latest [JDK and JavaFX](https://jdk.java.net/) binaries.
- Download [DiskBrowser2](https://github.com/dmolony/DiskBrowser2/releases).
- Create executable run file as follows:  

#### MacOS or Linux shell file
```
/path/to/jdk/Contents/Home/bin/java               \
--module-path /path/to/javafx-sdk/lib             \
--add-modules=javafx.controls                     \
-jar /path/to/DiskBrowser2.jar
```  

#### Windows batch file
```
C:\path\to\jdk\bin\java.exe                   \
--module-path C:\path\to\javafx-sdk\lib       \
--add-modules=javafx.controls                 \
-jar C:\path\to\DiskBrowser2.jar
```

#### Hints
The first line in each of the above shell files can usually be shortened to 'java   \\' as it's just the command to execute java on your system. The second line must be the path to wherever you placed the javafx download.
   
## Example Screens
This image shows the main interface change from DiskBrowser. Instead of separate tabs
 for each disk, the file tree expands each disk image in place.
![Teaser](screens/teaser1.png?raw=true "Data screen")
File systems within file systems can be accessed. This screen shows the directory
listing of a Pascal Area which is stored on a Prodos disk. All of the pascal files can
be displayed as usual.
![Teaser](screens/teaser7.png?raw=true "Pascal area on a prodos disk image")
This screen shows several SHK disk images stored as LBR files.
![Teaser](screens/teaser8.png?raw=true "SHK files on a prodos disk image")
The second interface change is that all images are now shown on a separate Graphics tab. This includes hi-res images, fonts, shapes, icons etc. The Data tab will instead display information pertaining to the image.
![Teaser](screens/pic01.png?raw=true "Data Tab")
The screen above is the Data Tab, the screen below is the Graphics Tab.
![Teaser](screens/pic02.png?raw=true "Graphics Tab")
When a file is selected and the Options pane is active, the options for that file type can be altered.
![Teaser](screens/teaser2.png?raw=true "Don't rely on this")
Extra file information has moved to the Extras tab.
![Teaser](screens/teaser3.png?raw=true "Other file types will have different output")
The file tree can be filtered so that only the selected file types are shown.
![Teaser](screens/teaser4.png?raw=true "BXY files")
The Meta tab shows information about the file.
![Teaser](screens/teaser5.png?raw=true "Meta")