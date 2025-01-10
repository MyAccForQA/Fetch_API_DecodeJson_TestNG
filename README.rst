###############################
     OpenWeather for Fetch
###############################

This repository contains some API behavior and covered by tests for the `Fetch <https://fetch.com/>`_, `GitHub Account <https://github.com/MyAccForQA/Fetch_API_DecodeJson_TestNG>`_.


    .. image:: https://github.com/MyAccForQA/Fetch_API_DecodeJson_TestNG/blob/main/screenshot/README/homepage.png
        :alt: Fetch
        :width: 30%
        :align: center


.. contents::

.. section-numbering::

.. raw:: pdf

   PageBreak oneColumn


=============
Demo
=============

Before continuing, can watch the demo `Homebrew <https://www.loom.com/share/2eac2abd5a144f838db175577dbeb643?sid=35e5ec58-d865-4544-9e56-83dd83bb8de4>`_.

=============
Dependencies
=============
----------------
Homebrew
----------------
Make sure you have `Homebrew <https://brew.sh/>`_ installed on your system. To check use the command below:

``brew -v``

----------------
Git
----------------
Make sure you have `Git <https://git-scm.com/>`_ installed on your system. To check use the command below:

``git -v``

----------------
Java
----------------
Make sure you have `Java <http://www.java.com/>`_ installed on your system. To check use the command below:

``java -version``

----------------
Maven
----------------
Make sure you have `Maven <https://maven.apache.org/download.cgi>`_ installed on your system. To check use the command below:

``mvn -v``

----------------
GitHub
----------------
Make sure you have access `GitHub Account <https://github.com/MyAccForQA/Fetch_API_DecodeJson_TestNG>`_.

=============
Run
=============
----------------
Clone
----------------
To clone use the command below:

``git clone https://github.com/MyAccForQA/Fetch_API_DecodeJson_TestNG``

----------------
Navigate to the project
----------------

To navigate use the command below:

``cd Fetch_API_DecodeJson_TestNG``

----------------
Generate
----------------

To generate use the command below:

``mvn clean package -DskipTests``

----------------
Run application
----------------
~~~~~~~~~~~~
Run application through Java
~~~~~~~~~~~~

To run application through Java use the command below:

``java -jar target/Fetch_API_DecodeJson_TestNG-v1.0.jar``

~~~~~~~~~~~~
Run application through Maven
~~~~~~~~~~~~

To run application through Maven use the command below:

``mvn exec:java -Dexec.mainClass="org.example.Main"``

----------------
Run test
----------------

To run test use the command below:

``mvn test``

=============
Open report
=============
----------------
Surefire report
----------------

`Surefire report <https://maven.apache.org/surefire/maven-surefire-report-plugin/>`_ is open-source framework designed to create test execution reports clear to everyone in the team.

~~~~~~~~~~~~
Open report
~~~~~~~~~~~~

To open report use the command below:

``open target/surefire-reports/emailable-report.html``
