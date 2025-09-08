# Temporal Median Background Subtraction 

Copyright (c) 2017 Bram van den Broek and Rolf Harkes, Netherlands Cancer Institute.
Implementation of the algorithm in a maven .jar for easy deployment in Fiji (ImageJ2). 

Properties:
* Calculates the median from the ranked data
* Uses a central window
* It uses the first and last median to subtract the starting and ending frames.
* Parallel computed
* The possibility to add an offset to the data before median removal to prevent integer overflow.
* Automatic correction of even windowsizes
* Automatic conversion to 16-bit

Used articles:
Thomas S. Huang et al. 1979 - A Fast Two-Dimensional Median Filtering Algorithm


This software is released under the GPL v3. You may copy, distribute and modify 
the software as long as you track changes/dates in source files. Any 
modifications to or software including (via compiler) GPL-licensed code 
must also be made available under the GPL along with build & install instructions.
https://www.gnu.org/licenses/gpl-3.0.en.html

Based on original code by Marcelo Augusto Cordeiro: https://github.com/marcelocordeiro/MedianFilter-ImageJ/


## FTM2
A faster version was developed by [Martijn from the HohlbeinLab](https://github.com/DrNjitram)

https://github.com/HohlbeinLab/FTM2 

https://forum.image.sc/t/announcing-a-faster-temporal-median-filter-for-imagej/44279
