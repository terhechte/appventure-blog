---
title: Vim Configuration
---

## My Vundle based Vim Configuration.

[Github Link](https://github.com/terhechte/VimConfiguration)

This exists mostly, so that I can easily clone it onto any machine I'm working on and so that all my machines same the same set of behaviours.

I've since switched over to [Emacs](https://github.com/terhechte/emacs.d) however, I still do use this Vim configuration especially for any Terminal work, where I don't like firing up Emacs. Also, my Emacs conf is so heavily modified that it feels very very similar to this Vim config. Nevertheless, there won't be any updates in here anymore, I suppose, since everything will probably happen in the relevant Emacs config instead.

The bundle is optimized, or will continiously optimized, for the following languages / use cases:

* Objective-C iOS / Mac
* Python (Mostly scripts, but also some Django)
* HTML / CSS / Javascript / JSON

It also contains a lot of plugins to make life in vim easier (like easymotion). There're also several configuration options that I've collected over the years from various sources and can't really attribute anymore. The most important of these requires a seperate block (see below under emacs bindings).

It is based upon a lot of vundle plugins. Also, it is still very much a work in progress. So it needs a couple of command line calls before one can use it:

```bash
# Get it from Github
git clone git://github.com/terhechte/VimConfiguration.git

# Link the .vimrc file into your ~home folder.
ln -s VimConfiguration/vimrc .vimrc

# Link the folder to .Vim
ln -s VimConfiguration .vim

# Enter the folder, and check out the git modules
cd VimConfiguration
git submodule init
git submodule update

# Tell vundle to download / update all available packages
vim +BundleInstall +qall
```

Next, you should also download and instlall a patched version of your favorite programming font, to be used with vim-powerline (https://github.com/Lokaltog/vim-powerline). You can choose one here: https://github.com/Lokaltog/vim-powerline/wiki/Patched-fonts

There's an updated programming font in this bundle. It is a copy of the fantastic Adobe Source Code Pro font with a special  modification: 
Vertical lines appear like one long line instead of looking dashed. This alone makes Vim (especially MacVim) much more pleasant to look at.

Finally, this repository also contains my own .bashrc / .bashprofile with my own set of useful functions and more. You probably don't want to install this, since it's suited to the locations where I installed some software. Nevertheless, have a look in there to see if there's something interesting for you.

## Documentation

The setup has the following shortcuts:

``` bash
,e: Toggle the nerd tree

,t: Toggle the TagBar

,s: Toggle Whitespace

cmd+n: Remove current search highlight

cmd+e: To switch between the 2 last buffers

,/: Toggle commenting the currently selected block or current line

,cd: Switch to the directory of the current buffer

,q: gqip Imitates TextMates Ctrl+Q function to re-hardwrap paragraphs of text

,v: Reselect the text that was just pasted

,,[motion]: EasyMotion command. I.e. ,,w will do easymotion with the w motion command (just try it).

cmd+l: (In Insert mode) toggle Sparkup
```

## Emacs Bindings

I've been using Mac OS X for almost 10 years now. All input fields in Mac OS X support the basic Emacs movement mechanisms to move around text, like cmd+e (end of line), cmd+a (beginning of line), cmd+f (next character), cmd+b (prevoius character). So these movements are by now deeply engrained in my text movement habits, especially when I'm writing (read, Insert-Mode). Something I really never liked about Vim was that it required me to leave insert mode for short text movements, like going to the end of the current line, or even going one character back. So I've long sought a way to activate the Emacs movement bindings for the Vim Insert-Mode. My Vim config supports this, without any additional plugins. Just the basic Emacs bindings are supported, nothing more:

* cmd+f: Move one character forward
* cmd+b: Move one character back
* cmd+a: Move to beginning of line
* cmd+e: Move to end of line
* cmd+p: Move to previous line
* cmd+n: Move to next line

(I've recently learned that cmd-o in insert mode allows you to perform one normal mode command, say like a motion, which invalidates most of the above but isn't as ingrained in my habits :)



