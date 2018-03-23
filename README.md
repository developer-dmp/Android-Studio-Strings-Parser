# Android Studio Strings Parser

This repo is my basic implementation to help translate your strings.xml file in Android Studio to any other language.

## Supporting Multiple Languages Overview
For anyone who doesn't know, here is a brief understanding of how Android supports multiple languages.  

If you have been developing properly in Android Studio, all of your literal strings that you use throughout your app should exist in the res/strings.xml file.  This file allows you to map plain text to a string name (or id).

In addition to saving string literals in the strings.xml file, Android also allows us to convert the content of these strings into different languages but still use the same id.  

For instance, here is an example of a string reference in English:

`<string name="hello">Hello</string>` 

and this would be how to support Spanish:

`<string name="hello">Hola</string>`

Notice how the name of the string remains the same but the content has changed.  This allows you to develop your application using references instead of the actual text (i.e. you would reference the string by R.string.hello instead of using the text 'Hello').  Android does things this way because the OS will detect which language the phone is configured for and use the corresponding strings.xml file.  If your device is in the United States and the primary language is English, the operating system will look for a strings.xml file that is configured with meta data 'en_US'.  If your device resides in Spain and is configured for Spanish, the OS will look for a strings.xml file with meta data 'sp_ES'.  This information of county codes can be found online.

## The Issue
Android's framework is pretty smart for doing things this way - it allows the developers to not worry about foreign languages not being displayed, but it provides a decent amount of work when it comes time to publishing.  What happens when your strings.xml file is 200 lines long and you want to support 100 languages?  That's 20,000 total strings with 100 duplicates - one for each different langauge.  AND the only thing that changes from string file to string file is the content within the <string></string> tags!

The Google Play Console does have a professional translating service for your app, however this takes time proportional to the number of strings and comes at a price.  Google will, for 7 cents a word, translate your entire app into however many languages you want.  So those 100 languages you wanted your app to support will now cost you $1,386.  Feel free to pay that if you'd like - 200 strings in your file is a decently large file, and 100 languages is a lot so this may be over-exaggerating but you get the point.  

Instead, feel free to use this simple program to help facilitate the process (and it's free).

## Usage
Since the Google Translate API is not free, there will be some busy work involved but this program will help you tremendously.  Essentially, this program (with your help) will decompose your strings.xml file from your Android Studio project and recompile a strings.xml file with your converted information.

DISCLAIMER: I used Intellij for the development of this program so it would be easiest to use that IDE when it comes time to run it.

Here is the flow:
1. Clone the project to your machine
2. Add your strings.xml file to the <i>input</i> directory
3. Run the program to parse strings.xml file
4. Copy the output in the file <i>output\strings_content.txt</i> into Google Translate and translate the whole file into your language of choice
5. Paste the output from Google Translate into <i>input\strings_content_converted.txt</i>
6. Run the program to rebuild the strings.xml file

Boom.  You now have a properly formatted strings.xml file in <i>output/strings.xml</i> that is an exact copy of the one you started with except it has the content for whatever foreign language you chose in Google Translate.  

When running this again (for another language you want to support), you can skip to step 5 with the content for your new language.  Steps 1-4 are only required the FIRST time the program runs.

Hope this can help you!
