First I need a tool which would allow me to merge two consequent videos showing a screen. I know what you're thinking - doesn't _Adobe After Effects_ already do that? Yes, but...

1. I don't know how to use it;
2. It's _art made with code_, not _art made with software, designed for making art_;
3. I have a vision of completely automating the process so anybody can submit their own experience (video showing some screen) to the loop.

So I decided to develop my own _Omniscience Video Editor_, capable of detecting screens in a video and augmenting it with another video. I'll use a technique called [chroma key compositing](http://en.wikipedia.org/wiki/Chroma_key "Chroma key compositing") (green screen) to overlay one video on top of another. And I'll start with a single frame.

![Color filter](project_images/color_filter.png?raw=true "Color filter")

I'd like to take a moment to explain [texture mapping](http://en.wikipedia.org/wiki/Texture_mapping "Texture mapping") a little. Suppose you have a two-dimensional projection of three-dimensional rectangular object (say, a screen). The projection itself is not necessarily a rectangle, so how do you map a rectangular texture on it? You can linearly interpolate (simply stretch) the texture to match this projection, but this gives an inaccurate result:

![Perspective](http://www.vrarchitect.net/anu/cg/Texture/Image/slide014.jpg "Perspective")

You can see that in order to correctly map a texture on a 3D surface, you need depth information of it. And we are already talking about augmented reality in my case. I don't think I have enough time left before competition deadline to get into this area. Furthermore, I want my algorithm to work with uncalibrated cameras, with unknown physical tag (screen itself) size, etc. which complicates it even more. Thus affine texture mapping will have to suffice. I just hope it looks realistic enough.

I took a couple of pictures of screens, displaying green colour. I wrote a skeleton editor, which for now can filter out an image for green.

![Color filter sketch](project_images/color_filter_sketch.jpg?raw=true "Color filter sketch")

Next steps will be to let user change filter tolerance dynamically and support videos. Only 94% to go! But seriously, I better hurry...