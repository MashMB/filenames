# Filenames
<p align="center">
	<img src = "img/filenames_ui.png" />
</p>

Simple JavaFX + Spring Boot application to rename large group 
of files at the same time.

## About
Everybody who uses PC probably know the problem of renaming 
large groups of files with given pattern. It can be really annoying 
and time wasteful. Imagine the situation - you are on the holidays 
taking photos with your camera and phone. Probably you will want 
to hold all photos in the same directory but in the creation order. 
Your camera will make files named something like *IMG_0128* 
and your phone like *photo 12-11-2018 16:00*. What is more,
images taken by camera can have different sequence numbers 
that are not exactly in order that you want. There is also extension
problem, maybe you do not want have the same pattern for video
files but you want to have appropriate order. This was my main
aim to create this peace of software.

## Getting started
This program was created mainly for Windows users. Package
that I created is deployed as executable (.exe) file. Program is
using file properties like file creation date so it can not work
properly on some file systems. For example **ext** Unix
partitions do not store information about file creation date.
If you really need to use it on other system than Windows
you can modify source files as you want. Maybe in future there
will be Unix support added. Before using I highly recommend to
read all the docs and test how application works on smaller amount
of files (some kind of test group) because you can make a lot
of mess in your file system. At the bottom of this README you
will find link to latest version with always up to date changelog.

## How it works
After successful installation you will see exactly the same window
as this shown above. You need to fill some gaps before usage:

* **Directory** - in this field, you should give valid path to directory with files to rename (you can choose it with button usage)

* **Core name** - specify core name for files that will be renamed (**IMG_**, **VID_**, **test-**)

* **Start number (format)** - pattern of sequence number that also will be first number for numeration, with core name it will create full name like: **IMG_0001**, **VID_010** or **test-10**

* **Extension (field below radio buttons)** - extensions for files that should be renamed

Application can work in 4 modes:

1. Continuous mode, Files with extension
2. Continuous mode, All Files
3. All mode, Files with extension
4. All mode, All Files

Description of modes with examples is below this section.

On the right side you can see text box - there will be a lot of
information for you after performing scan or rename operation.

Using scan operation (Scan button) will never make any changes in
your file system. It will scan directory in chosen mode for you
and show statistics in the text box on the right.

Using rename operation (Start button) will rename files in chosen
directory with given pattern by user (files to rename will be sorted 
in ascending order by **file creation date**). All statistics of 
operation will be showed in the text box on the right.

### 1. Continuous mode, Files with extension
Extension specified by user: **txt**
Core name specified by user: **text_file_**
Start number (format) specified by user: **0001**

Directory before rename operation:
```
test_directory
	|
	| - test_file_0001.txt
	|
	| - test_file_0003.txt
	|
	| - alala.md
	|
	| - baba.txt
	|
	| - cat.txt
	|
	| - test.png
```

Directory after rename operation:
```
test_directory
	|
	| - test_file_0001.txt // File not modified
	|
	| - test_file_0002.txt // Renamed baba.txt
	|
	| - test_file_0003.txt // File not modified
	|
	| - test_file_0004.txt // Renamed cat.txt
	|
	| - alala.md // File not modified
	|
	| - test.png // File not modified 
```

### 2. Continuous mode, All Files
Core name specified by user: **renamed-**
Start number (format) specified by user: **010**

Directory before rename operation:
```
test_directory
	|
	| - rapapapa.txt
	|
	| - index.html
	|
	| - renamed-009.pdf
	|
	| - renamed-011.txt
	|
	| - renamed-012.rdf
	|
	| - renamed-013.jpg
```

Directory after rename operation:
```
test_directory
	|
	| - renamed-009.pdf // File not modified
	|
	| - renamed-010.txt // Renamed rapapapa.txt
	|
	| - renamed-011.txt // File not modified
	|
	| - renamed-012.pdf // File not modified
	|
	| - renamed-013.jpg // File not modified
	|
	| - renamed-014.html // Renamed index.html
```

### 3. All mode, Files with extension
Extension specified by user: **pdf**
Core name specified by user: **doc_**
Start number (format) specified by user: **02**

Directory before rename operation:
```
test_directory
	|
	| - doc_01.txt
	|
	| - doc_02.md
	|
	| - lalala.pdf
	|
	| - doc_01.pdf
	|
	| - doc_02.pdf
	|
	| - doc_03.pdf
```

Directory after rename operation:
```
test_directory
	|
	| - doc_01.txt // File not modified
	|
	| - doc_02.md // File not modified
	|
	| - doc_02.pdf // Renamed lalala.pdf
	|
	| - doc_03.pdf // Renamed doc_01.pdf
	|
	| - doc_04.pdf // Renamed doc_02.pdf
	|
	| - doc_05.pdf // Renamed doc_03.pdf
```

### 4. All mode, All Files
Core name specified by user: **test**
Start number (format) specified by user: **0012**

Directory before rename operation:
```
test_directory
	|
	| - doc_01.txt
	|
	| - doc_02.md
	|
	| - lalala.pdf
	|
	| - doc_01.pdf
	|
	| - doc_02.pdf
	|
	| - doc_03.pdf
```

Directory after rename operation:
```
test_directory
	|
	| - test0012.txt // Renamed doc_01.txt
	|
	| - test0013.md // Renamed doc_02.md
	|
	| - test0014.pdf // Renamed lalala.pdf
	|
	| - test0015.pdf // Renamed doc_01.pdf
	|
	| - test0016.pdf // Renamed doc_02.pdf
	|
	| - test0015.pdf // Renamed doc_03.pdf
```

## Installation

## Release and changelog

## Bugs and issues

## License
The MIT License (MIT)

Copyright (c) 2018 Maciej Bedra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
