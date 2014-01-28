AndroidAboutShareDialog
=======================

Class that makes About and Share-Dialoges with the Android Framework. With easy to use HTML-Pages as Dialog Content.

What it does:
----
The class AboutShareDialog is just a little part of my Android-Kitchen, that gives you an easy way to display beautiful about-, rating- or sharing- etc. dialoges build with html.


How To Use:
----
1 Add the AboutShareDialog Class to your Project.   
2  Build your html-file and place it   
2.1  on any server (only recommendend, if your application already has the permission to access the internet)   
2.2 or in the Android asset folder   

3 Implement it

> AboutShareDialog myDialog = new AboutShareDialog(this);    
> myDialog.aboutDialog();   
> myDialog.openTwitterIntent();   
> myDialog.sentmail(null);    
> myDialog.shareWithFriends(null);
