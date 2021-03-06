<img src="https://api.codeclimate.com/v1/badges/38320312fdc180159372/maintainability" /> 

[Code Climate Test Report](https://codeclimate.com/github/themavencoder/JournalApplication/maintainability).	

#	Journal Application #

This journal application is utility application for Android. It allows clients to create a diary entry, review diary entries and also add or modify diary entries. It uses firebase for authentication. Clients are able to log in using a valid email address and also meeting password requirements for the application. All entries are also stored in a remote repository using Firebase and local repository using SQLite. **Both the MVC and MVP design guidelines are used in this journal application.** [Download APK](https://drive.google.com/drive/folders/13Mr18a-pmEgzYz6y4JNl_TTMmkC_aWMX?usp=sharing)

![deviceart](https://user-images.githubusercontent.com/15139694/42136112-b10a3690-7d4d-11e8-9e28-491084ccff9c.png)
![ui screen_framed](https://user-images.githubusercontent.com/15139694/42137137-292d1b54-7d5f-11e8-9257-bae54b2cb60a.png) 
![002 google log in_framed](https://user-images.githubusercontent.com/15139694/42137279-82b8c5cc-7d61-11e8-89e4-af4e65e68107.png)
![008 deleting and editing journal entries 1 _framed](https://user-images.githubusercontent.com/15139694/42137409-7ccaf5c0-7d63-11e8-9f5f-85456b571129.png)






##	Getting Started  ##

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

###	Prerequisites	###
```
Android Studio: The official Integrated Development Environment (IDE) for Android app development, based on IntelliJ IDEA.

Firebase: Firebase is Google's mobile platform that helps you quickly develop high-quality apps and grow your business.
```
### Installing  ###

Follow this steps if you want to get a local copy of the project on your machine. 

**1.First, clone the repository into a local directory and checkout the master branch if MVC or the mvp branch if MVP:**
```sh
git clone https://github.com/themavencoder/JournalApplication.git

```
**2. Import the project to Android Studio.**

1. In Android Studio, go to File -> New -> Import project.
2. Follow the dialog folder to choose where you cloned the project and click on open.
3. Android Studio imports it and builds it for you.


You can now run the project in an Android Emulator or a real Android Device.

### Running the tests	###

Journal Application comes with both Instrumented tests and Unit tests.

### Instrumentation tests ###

To run the instrumentation tests, you need an Android Emulator or a real android device Once you have any of this, open a terminal in Android STudio and run the command below.

```gradle
./gradlew connectedAndroidTest
```
		 

### Running Local Unit tests

You do not need any device to run this test. To run this test, open a terminal and run the command shown below. 


```gradle
./gradlew test
```


### Built With


-[Java](https://java.com) - Java for JVM

-[SQLite](https://developer.android.com/training/data-storage/sqlite) - The Opensource relational database for Android

-[Firebase](https://firebase.google.com/) - For authentication and data persistence.

### Contributing

	Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull request to us. 

###	Authors

**Tobiloba Adejumo** - Author.
	See also the list of contributors who participated in this project.

### License
	
	This project is licensed under the MIT License - see the LICENSE.md file for details.

##	Acknowledgments
-[PurpleBooth](https://firebase.google.com/) - for the README.md template.

-[Ravi Tamada](https://www.androidhive.info/)
	

