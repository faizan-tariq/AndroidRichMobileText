# RichMobileText
RichMobileText is a module for Android apps which exposes RichEditText and RichTextView to client.

## Functionality

### Responsible For:
- Enable HTML Rendering Support
- Enable MarkDown Language Support
- Transformation from MarkDown to HTML
- Default Emojis Support (Encoding & Decoding)
- Custom Emojis Support (Encoding & Decoding)
- Support to Load Dynamic Emojis From Server End via json file named (CustomEmojiIcons.json)
- Dynamic Scrolling and Pagination Support
- Changeable Font Size (by Default: 14sp, 20sp, 32sp)

### Functionality Exposed:
Following Features are exposed to client app:
- customEmojiEnabled
- textSizeChangeable
- htmlEnabled
- markdownEnabled

## How to integrate it?
Following the 2 different ways of incorprating this library into your project.
###For non-maven / non-gradle projects:
Import this project as a library project into your development studio. Add this project as a library in your current project.

### Maven dependency
Add the following dependency in the dependencies tag of pom.xml file.
```java
<dependency>
  <groupId>rich.mobile.text</groupId>
  <artifactId>android</artifactId>
  <version>0.0.1</version>
  <type>aar</type>
</dependency>
```

### Gradle dependency
Add the following code snippet in build.gradle file.
```java
compile 'rich.mobile.text:android:0.0.1'
```

## Code Changes

```java
editText = (RichEditText) findViewById(R.id.edit);
editText.textSizeChangeable(true);
editText.customEmojiEnabled(true);
editText.setChangedTextSize(R.dimen.changeable_font_size_default);

text = (RichTextView) findViewById(R.id.text);
text.markdownEnabled(true);

text1 = (RichTextView) findViewById(R.id.text1);
text1.htmlEnabled(true);


String str = "## This is Markdown \n This is Markdown :happy \\uD83D\\uDE01";
String str1 = "<b>This is Html</b><br>This is Html :alien \\uD83D\\uDE01";

text.setText(str);
text1.setText(str1);
```


### For Initiating a Service to Load dyanmic Emojis and relative code from server
```java
Intent intent = new Intent(this, SyncEmojis.class);
intent.putExtra("url","server base url");
startService(intent);
```


### For Overriding default font sizes, text label and icon

```java
<dimen name="changeable_font_size_default">14sp</dimen>
<dimen name="changeable_font_size_medium">20sp</dimen>
<dimen name="changeable_font_size_large">32sp</dimen>

<string name="label">Rich Mobile Text</string>
R.drawable.ic_insert_emoji
```

<img src="https://github.com/faizan-tariq/AndroidRichMobileText/blob/master/img0.png" width="250">
<img src="https://github.com/faizan-tariq/AndroidRichMobileText/blob/master/img1.png" width="250">
<img src="https://github.com/faizan-tariq/AndroidRichMobileText/blob/master/img2.png" width="250">
