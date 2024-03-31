<br/>
<p align="center">
  <a href="">
  </a>

  <h3 align="center">ReadME</h3>

  <p align="center">
   This is the ReadMe File for EECS 2311 Group 1 Winter 2024
    <br/>
    <a href="https://github.com/ShaanCoding/ReadME-Generator"><strong></strong></a>
    <br/>
    <br/>
    <a href="">View Demo</a>
    .
    <a href="">Report Bug</a>
    .
    <a href="">Request Feature</a>
  </p>
</p>


## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Set Up](#Set-Up)
* [Usage](#usage)
  * [Launching the Main Application](#Launching-the-Main-Application)
  * [Exploring Functionalities](#Exploring-Functionalities)
      * [Logining in and Authentication](#Logining-in-and-Authentication)
      * [Dashboard](#Dashboard)
      * [Rating](#Rating)
      * [Favourites](#Favourites)
      * [Analytics](#Analytics)
      * [Email Feature](#Email-Feature)
  * [Sign Out - the Main Application](Sign-Out---the-Main-Application)
* [Contributing](#contributing)
* [Authors](#authors)
* [Acknowledgements](#acknowledgements)

## About The Project


The Show/movie Tracking web app is designed for users who do not have streaming subscriptions but would still like to know about upcoming movies/TV shows. The web app provides an in-time notification up to their preference, recommends trending shows on major streaming platforms like Netflix, Prime, etc., and gives users a thread-like comment place that allows users to develop deep discussions to share their thoughts with friends or people who are also into the shows. It also functions as an integrated hub that saves users the trouble of jumping from one platform to another.

## Built With

Java Swing

Java

DB Browser(Database)

## Getting Started

   
### Prerequisites

Before you begin, ensure you have the following software installed on your system:

1. Git - Version Control

2. Eclipse Community edition - The IDE to asociate the code with. Here, eclipse is primarily used.

3. Java Development Kit (JDK) version 8 or above: JDK is required to compile and run the Java application. Ensure JDK 8 or later is installed on your system. [Download JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

4. Maven: This project uses Maven for managing dependencies and building the application. Maven simplifies the process of including external libraries such as JDBC drivers for SQLite, JFreeChart, and JavaMail API in the project. Ensure Maven is installed on your system. [Download Maven](https://maven.apache.org/download.cgi)


### Installation
To run Show Tracking web app, ensure you have Java installed on your system. Follow these steps:
1. Clone the repository: `git clone https://github.com/vaghani-shreya/EECS_2311_Group_Project.git`
2. Navigate to the project directory: `cd EECS_2311_Group_Project`
3. Run Maven Build: Execute `mvn clean install` to build the project and download the necessary dependencies automatically.
4. Run the application: Use the command `java -cp target/classes front.loginpage` to run the application.

 GitHub - https://git-scm.com/
 Eclipse Community edition - https://www.eclipse.org/community/
 Java - https://www.oracle.com/ca-en/java/technologies/downloads/
 (jre version 1.8)


## Set Up

1. Clone project - `git clone https://github.com/vaghani-shreya/EECS_2311_Group_Project.git`
2. Import Project in eclipse
3. Make sure after Importing, there is a library called Maven Dependencies in the project folder as shown below
  ![figure 1](Images/Image1.png)
If the maven dependencies cannot be resolved, sync maven dependencies  https://maven.apache.org/


## Usage

  ### Launching the Main Application 
Follow the steps to launch the application:

  1. Locate the LoginPage.java found under **src/front/LoginPage.java** in the Project folder and right click on it, scroll down to the **Run** option and click on **Java Application** to Launch the application.
     The process is witnessed in the Image below
     
      ![figure 2](Images/Image2.png)

   **NOTE - It takes a few seconds to Launch the Application as it is connected to a database and parsing data from the database**

### Exploring Functionalities 

#### Logining in and Authentication
You can use the **username** - user , **password** - password to login into the system and explore the application. You can also register as a new user by entering a new email you'd like to set as your username and enter a new password. Then click **register**.
 ![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/a5114757-36ee-4b69-ae40-43014b11d056)
 
A pop-up window should appear letting you know you have successfully registered.
 ![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/6379938c-73cd-4278-8ee7-c30a46d7e817)
 
If you forgot your password or want to change it. Click **forgot my password** then enter your **username** then press **Enter**.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/72da77bc-c38e-4037-bead-75667fd2850d)

The verification window will appear where you should enter the code that has been sent to your email.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/011476ee-a9fa-4f45-9f72-1221344e8d05)

If you have an existing account simply enter the **username** and **password** and click **Login**.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/fb7bf959-92c8-410e-8e4e-ff6a4220fcbd)


After Logging into the Main Application, you can click on the different tabs to checkout the different features **Dashboard**, **Ratings**, **Favourites page**, and **Analytics** 
#### Dashboard
  There are three different tabs under the dashboard which can be explored **Netflix**, **Disney**,and **Amazon**.
  ![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/23b00aca-ec89-4d73-bfc6-863baec20afd)
  ![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/bfd861d5-572b-417d-ae46-81ef8ba27aea)
  ![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/7a46a761-fdad-4bac-87e8-a985b97bcda3)

  
To search for a specific show enter the title of the show and press **Search**.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/7953cc45-6c8d-475e-8244-80355acd372c)

To further filter based on genre, Type, or ratings you can click on the drop-down arrow by the search button. Select the filter by clicking then enter the genre, rating, type etc. then press **search**.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/705b3e9b-3ad6-4078-82be-ba8292020a9d)


You can further sort by date added, release year, and title. Follow the same steps as above.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/aef904f2-0dd0-444d-a366-31dbd7ce1b12)


To see additional information about the specific show click on a show panel. A pop-up window should appear.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/2ee87997-a995-4c12-9feb-edf6849edc9d)


Here you can add a show to your favourites list.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/d759ffc2-eead-462b-934c-eb9f2ce26238)

You can also mark the show as watched.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/51b64db3-d8bf-4269-99b3-aa26e43204b8)

#### Rating
Similarly to dashboard there are three different tabs under ratings which can be explored **Netflix**, **Disney**,and **Amazon**.
The main difference is included when you click on an individual show. Once Clicked this pop up window should appear. With an **Add Rating** functionality as well as **Add Comment**.

![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/4af8174a-29e5-4d15-b0b0-86894743df48)


To Enter a rating and/or comment type a rating between 1-10 and a comment in their respective search fields. The press **save** or **save comment**.

![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/4a776581-7fed-4f38-bdbb-dcb9f7601aaa)

When rating is complete a pop window should appear informing you the rating was complete.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/19011afd-540b-43a4-acb5-be6080c33f7d)

Similarly when a comment is added it will appear in show details. 
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/fa82c5a6-c50a-4ee8-90c9-15c4df6e5697)

#### Favourites
If you click on favourites page you can see your favourite list of shows.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/c173dd4b-863b-4167-ba67-c0549af8b396)

If you click on any show you can see further details and delete any show added to the list by clicking **Delete**
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/89653733/4cfaa4f5-1f71-49d5-93cd-f5b063ada754)

#### Analytics
The Analytics page displays the number of shows in each platform which have a certain age rating associated with it.

![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/da1a57b9-11f5-451b-a117-67bf6d2c186b)

#### Email Feature
The email feature is automated to be sent weekly. To test this feature:

In the newsletter.java file go to the sendEmail method and enter the correct password.
![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/6e316270-3eb9-4e50-9eb8-de2f3cce10d5)

Then run the application again and log in with your email. You should recieve an email automatically. The next email will be sent in a week.

### Sign Out - the Main Application

To Sign out from the Main application, click on the Sign out button on the right hand side of the application.

![image](https://github.com/vaghani-shreya/EECS_2311_Group_Project/assets/96916849/5d3338d0-1967-48b3-a89f-b1d1bac2d2f0)



## Authors
- Mai
- Elizabeth
- Anusha
- David
- Shreya
- Huiling


## Acknowledgements
- Thanks to Hadi Hemmati, the EECS 2311 instructor and Hamed Taherkhani, the TA, for their invaluable guidance.

=======
## License

## Authors

- Mai: Implemented The Netflix “Discover Weekly” page where users can search for a certain show from Netflix.
- Elizabeth: Implementing the login page
- Anusha: Implemented the Forgot Password Function
- David: Implemented the Favourites Page
- Shreya: Implementing the dashboard with all the different pages
- Huiling: Implementing the detailed page for each entry 

## Acknowledgements

- Thanks to Hadi Hemmati, the EECS 2311 instructor and Hamed Taherkhani, the TA, for their invaluable guidance.
- Special thanks to [JFreeChart](http://www.jfree.org/jfreechart/) for the charting tools.
>>>>>>> d2c9357061c05c44d93b3e9f4a61d3cdd7883354
