# Pull To Refresh Views for Android

![Screenshot](https://github.com/chrisbanes/Android-PullToRefresh/raw/master/header_graphic.png)

This project aims to provide a reusable Pull to Refresh widget for Android. It was originally based on Johan Nilsson's [library](https://github.com/johannilsson/android-pulltorefresh) (mainly for graphics, strings and animations), but these have been replaced since.

## Features

 * Supports both Pulling Down from the top, and Pulling Up from the bottom (or even both).
 * Animated Scrolling for all devices (Tested on 1.6+)
 * Works for all AbsListView derived classes. ListView, ExpandableListView & GridView have been implemented.
 * Also Works with WebView!
 * Integrated End of List Listener for use of detecting when the user has scrolled to the bottom.
 * Maven Support (thanks to Stefano Dacchille)
 * Indicators to show the user when a Pull-to-Refresh is available
 * Lots of [Customisation](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Customisation) options!

Repository at <https://github.com/chrisbanes/Android-PullToRefresh>.

## Extra customization Options from edsionw.

Added more customization parameters (see: library/res/values/attrs.xml)

 * "pull_to_refresh_from_bottom_pull_label" 
 * "pull_to_refresh_from_bottom_refreshing_label"
 * "pull_to_refresh_from_bottom_release_label"
 * "pull_to_refresh_pull_label"
 * "pull_to_refresh_refreshing_label"
 * "pull_to_refresh_release_label"
 * Added "rotation_mode" (true/false, if you want the image to rotate during refresh)
 * Added "pull_to_refresh_image_scale", scale on the image given. 
 * Added "pull_to_refresh_pull_image", resource id for the image when pulling.
 * Added "pull_to_refresh_refreshing_image", resource id for the image while refreshing.
 * Added "pull_to_refresh_release_image", resource id for the image when released.

## Sample Application
The sample application (the source is in the repository) has been published onto Google Play for easy access:

[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](http://play.google.com/store/apps/details?id=com.handmark.pulltorefresh.samples)

## Usage
To begin using the libary, please see the [Quick Start Guide](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Quick-Start-Guide) page.

### Customisation
Please see the [Customisation](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Customisation) page for more information on how to change the behaviour and look of the View.

### Pull Up to Refresh
By default this library is set to Pull Down to Refresh, but if you want to allow Pulling Up to Refresh then you can do so. You can even set the View to enable both Pulling Up and Pulling Down using the 'both' setting. See the [Customisation](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Customisation) page for more information on how to set this.

## Apps
Want to see which Apps are already using Android-PullToRefresh? Have a look [here](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Apps). If you have an App which is not on the list, [let me know](http://www.senab.co.uk/contact/).

## Changelog
Please see the new [Changelog](https://github.com/chrisbanes/Android-PullToRefresh/wiki/Changelog) page to see what's recently changed.

## Pull Requests

I will gladly accept pull requests for fixes and feature enhancements but please do them in the dev branch. The master branch is for the latest stable code,  dev is where I try things out before releasing them as stable. Any pull requests that are against master from now on will be closed asking for you to do another pull against dev.

## Acknowledgments

* [Stefano Dacchille](https://github.com/stefanodacchille)
* [Steve Lhomme](https://github.com/robUx4)
* [Maxim Galkin](https://github.com/mgalkin)


## License

    Copyright 2011, 2012 Chris Banes

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
