#	Journal Application

This journal application allows clients to create a diary entry, review diary entries and also add or modify diary entries. It uses firebase for authentication. Clients are able to log in using a valid email address and also meeting password requirements for the application. All entries are also stored in a remote repository using Firebase and local repository using SQLite.

{<img src="https://api.codeclimate.com/v1/badges/38320312fdc180159372/maintainability" />}[https://codeclimate.com/github/themavencoder/JournalApplication/maintainability]

##	Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

###	Prerequisites
'''
Android Studio: The official Integrated Development Environment (IDE) for Android app development, based on IntelliJ IDEA.

Firebase: Firebase is Google's mobile platform that helps you quickly develop high-quality apps and grow your business.
'''
###	Installing

Follow this steps if you want to get a local copy of the project on your machine. 

***1. Clone or fork the repository by running the command below***
	'''https://github.com/themavencoder/JournalApplication.git'''
***2. Import the project to Android Studio.***
	1. In Android Studio, go to File -> New -> Import project.
	2. Follow the dialog folder to choose where you cloned the project and click on open.
	3. Android Studio imports it and builds it for you.

You can now run the project in an Android Emulator or a real Android Device.

### Running the tests

Journal Application comes with both Instrumented tests and Unit tests.

####Instrumentation tests

To run the instrumentation tests, you need an Android Emulator or a real android device Once you have any of this, open a terminal in Android STudio and run the command below.
		
		'''./gradlew connectedAndroidTest'''

####Running Local Unit tests

You do not need any device to run this test. To run this test, open a terminal and run the command shown below. 

		'''./gradlew test'''


### Built With


	- [Java](https://java.com) - Java for JVM
	- [SQLite](https://developer.android.com/training/data-storage/sqlite) - The Opensource relational database for Android
	- [Firebase](https://firebase.google.com/) - For authentication and data persistence.

### Contributing

	Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull request to us. 

###	Authors

	See also the list of contributors who participated in this project.

### License
	
	This project is licensed under the MIT License - see the LICENSE>md file for details.

##	Acknowledgments
	https://www.androidhive.info/
	- [PurpleBooth](https://firebase.google.com/) - for the README.md template.
	- [Ravi Tamada](https://www.androidhive.info/)
	

