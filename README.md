#Genesys Chat Libraries.
----------

## Overview

This repo houses code written by Ankit and Sinthushan during the 24 hour Genesys Hackathon, held at Ryerson Intenational Living Centre on the 18th and 19th of July 2014.

## Objective

The code here provides for an iOS and an Android library solution to enable anyone making an iOS or Android app to add Genesys chat functionality to their application.

## USAGE

### Chat server

- Clone the code from <https://github.com/skreis/gws-emulator>
- Setup the chat server following the Readme.md file included in the repo.

### Android

The android solution presents itself as a jar and provides views (activities) for login and the actual chat window. The library is completely skinable and has no dependencies (apart from appcompat for backward compatibility). It uses a speech bubble styling from <https://github.com/AdilSoomro/Android-Speech-Bubble>

There are 2 android projects: 

- "GenesysChatLib" under the "Android" folder.
- "AndroidSample" in a folder with the same name.

AndroidSample demonstrates the simple usage of the library without overriding any styles. 

### iOS

The iOS solution presents itself in form of a CocoaPod. You can find it here: <https://github.com/sin2/SAGenesysChat>