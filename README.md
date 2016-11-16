# Timecon
[![](https://jitpack.io/v/alxrm/Timecon.svg)](https://jitpack.io/#alxrm/Timecon)
[![License](http://img.shields.io/badge/license-MIT-green.svg?style=flat)]()

Easy-to-use animated clock icon written in Kotlin

![](https://github.com/alxrm/Timecon/blob/master/imgs/timecon.gif?raw=true)


## Including in your project

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	    // ...
	    maven { url "https://jitpack.io" }
	}
}
```

Add the dependency:
```Groovy
dependencies {
    compile 'com.github.alxrm:Timecon:1.0.2'
}
```

Usage
-----

### ClockDrawable

You can use it as a `Drawable`, and insert into any `ImageView`,
with beautiful fluent API like this:

```Java
final ImageView clocks = (ImageView) findViewById(R.id.clocks); // some ImageView
final ClockDrawable clockDrawable = ClockDrawable.builder(this)
    .hours(4)                                                   // initial time hours
    .minutes(20)                                                // initial time minutes
    .withSpeed(-2.5F)                                           // set indeterminate animation minutes pointer speed (1F by default)
    .withColor(Color.WHITE)                                     // set icon color
    .withFrameWidth(Stroke.REGULAR)                             // set frame width
    .withPointerWidth(Stroke.THIN)                              // set pointer width
    .withDuration(600L)                                         // set animation duration in millis (600L by default)
    .withInterpolator(new DecelerateInterpolator())             // set animation interpolator (default is OverShootInterpolator)
    .withListener(new AnimatorListenerAdapter() {/*...*/})      // set animation listener
    .into(clocks);                                              // attach the Drawable you built to ImageView and returns Drawable
 // .build();                                                      or you can just use build() to simply get Drawable   
```

All of these methods can be ignored, so the drawable will be created with it's default state

### ClockImageView

There is an `ImageView` wrapper that simply draws the icon and provides an API to work with it.
You can use it in any layout.

Customization is also available through xml attributes:

```xml
	<rm.com.clocks.ClockImageView
		android:id="@+id/clocks"
		android:layout_width="56dp"
		android:layout_height="56dp"
		android:layout_centerInParent="true"
		app:hours="16"
		app:minutes="20"
		app:timeSetDuration="400"
		app:clockColor="#BBFFFFFF"
		app:indeterminateSpeed="2"
		app:pointerWidth="thin"
		app:frameWidth="regular"
		/>
```

## API

To set hours and minutes on the clocks without animation:

```java
Clock.setHours(int hours)
Clock.setMinutes(int minutes)
```

To change time _with_ animation:

```java
Clock.animateToTime(int hours, int minutes)
```

To make it spinning endlessly:

```java
Clock.animateIndeterminate()
```

Use `stop()` to interrupt this indeterminate animation

If you want it to __"tick"__ endlessly, set `indeterminateSpeed` to `0.001F`

Or if you want it to show a __rewind__ like animation, set negative `indeterminateSpeed`

## Contribution

I'd like to improve this, so feel free to suggest your ideas about it in the issues,
or, if you found a bug and you have some free time to fix it, writing a few lines of code in __Kotlin__, 
feel free to send me PRs. 

## License

    MIT License

    Copyright (c) 2016 Alexey Derbyshev

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
