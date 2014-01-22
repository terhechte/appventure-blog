---
title: Use VIM as an Xcode alternative
tags: cocoa objective-c ios
alias: ["/2013/01/index.html"]
---
I really like [Vim](http://www.vim.org). I've been using it for many years now. However, since I do most of my coding in Objective-C, I could oftentimes only use Vim for HTML / Javascript / Python / Script projects. This is because I got so used to the fantastic auto completion in XCode. 

Objective-C is a rather verbose and elaborate language, so the XCode completion is very valuable in everyday coding. Consider this line of code:

    [NSThread detachNewThreadSelector:@selector(example:) toTarget:anObject withObject:anotherObject]

Without completion, it is easy to forget the detailed syntax (was it onTarget: or toTarget: or withTarget:), it is cumbersome and slow to type the whole line, and it is error prone. Even worse, when you define lots of custom objects, it takes a lot longer as soon as you don't remember the exact name of that specific property anymore. 

XCode's code completion helps in all these cases, which is why using Vim for Objective-C coding is by far not as good as in XCode. I've tried to remedy this by using [AppCode](http://www.jetbrains.com/objc/) with its [IdeaVim](http://plugins.jetbrains.net/plugin/?id=164) plugin, but the plugin lacked too many Vim commands that I use everyday. I used [XVim](https://github.com/JugglerShu/XVim) a plugin that extends XCode to have Vim bindings, but I couldn't live without a couple of Vim extensions and commands. In the end, event he slightest difference between one of these solutions and original Vim behaviour would bring me off track.

So yesterday I decided to try to tackle [Clang Complete](https://github.com/Rip-Rip/clang_complete), a completion plugin for Vim that utilizes the very same Clang engine that XCode uses, too. I had tried installing it before, but it never worked for me. This time, I got lucky though.

In addition to Clang Complete, I also installed a couple of other plugins to deal with other important XCode features. My current Objective-C Vim setup is a pleasure to work with, but there're still some things that irk me and which I will try to remedy in the future:

- It is currently not really possible to build your project and run it on the device or in the simulator. I've actually started working on this, but it is not done.
- There is no way to look up the documentation for an entity. This seems to be possible with Cocoa.vim, but I haven't looked into it yet.
- Debugging, of course, does not work. Here, I think, I'll just continue to use XCode.
- Interface Builder stuff does not work either - of course.
- Your Vim needs to be compiled with Python and Ruby support.

That said, let me go through the steps required to get Vim up to par with XCode

#### Installing Packages

In order to get Vim to behave nice with XCode, a couple of plugins are necessary. In order to install them, I'd recommend using a package manager like [Pathogen](https://github.com/tpope/vim-pathogen) or [Vundle](https://github.com/gmarik/vundle). I'm using Vundle. This means that whenever you see something like "Bundle 'name/plugin'" in my Vim configuration, it is a Vundle command that will look up the Vim plugin on Github, download it, and include it - all in one step.

### Clang

First of all, you'll need a recent version of Clang. The Clang Complete Wiki argues to not use the built-into-xcode one, but instead install the most recent version. I used [Homebrew](http://mxcl.github.com/homebrew/) to install version 3.3:

    brew install --HEAD llvm --with-clang


Next up, is the actual Clang Complete Vim plugin. If you manually installed clang, you should know where it is now. If you installed with homebrew, it is probably in /usr/local/bin. 

### Clang Complete

You can [download it here](https://github.com/Rip-Rip/clang_complete). Below is my configuration to make it work.

    " Tell Vundle to download & import the clang complete plugin
    Bundle 'Rip-Rip/clang_complete'

    " Disable auto completion, always <c-x> <c-o> to complete
    let g:clang_complete_auto = 0 
    let g:clang_use_library = 1
    let g:clang_periodic_quickfix = 0
    let g:clang_close_preview = 1

    " For Objective-C, this needs to be active, otherwise multi-parameter methods won't be completed correctly
    let g:clang_snippets = 1

    " Snipmate does not work anymore, ultisnips is the recommended plugin
    let g:clang_snippets_engine = 'ultisnips'

    " This might change depending on your installation
    let g:clang_exec = '/usr/local/bin/clang'
    let g:clang_library_path = '/usr/local/lib/libclang.dylib'

Cmd           | Behaviour                         |
-------------:|:----------------------------------|
&lt;c-x&gt; &lt;c-o&gt;   | Show list of completions          |

### Ultisnips

For the actual completion, Clang Complete has its own code but can also work with different completion plugins. I found the Clang Complete solution to not work very well, so I tested the others. Ultisnips comes closest to the XCode experience. It also includes a [set of readymade snippets for common Objective-C patterns](https://github.com/SirVer/ultisnips/blob/master/UltiSnips/objc.snippets).

So next up you need to [install ultisnips](https://github.com/SirVer/ultisnips):

    Bundle 'guns/ultisnips'

In XCode, when you want to switch between several parameters on a selector (doSomething:<> toObject:<> withObject:<>) you can just hit <tab> or <shift>+<tab>. This does not work with ultisnips, instead you have to type <c-j> or <c-k>.

Cmd           | Behaviour                         |
-------------:|:----------------------------------|
&lt;c-j&gt;         | Go to next parameter in completion|
&lt;c-k&gt;         | Go to previous parameter          |

Snippet       | Behaviour                         |
-------------:|:----------------------------------|
cl&lt;tab&gt; | Define Class                      |
sel&lt;tab&gt;      | @selector(example:)               |
log&lt;tab&gt;      | NSLog(...)                        |
format&lt;tab&gt;   | NSString stringWithFormat         |

[More](https://github.com/SirVer/ultisnips/blob/master/UltiSnips/objc.snippets)

### Syntastic
XCode also shows you errors in your files and makes it easy to see when there's an unused variable, or when you're trying to call a selector that does not exist. Clang Complete has support for displaying these errors, but I found the overall integration in Vim sub par. I might be spoiled by [Syntastic](https://github.com/scrooloose/syntastic) which is a multi language syntax checker with fantastic Vim integration. It shows sidebar signs, can be embedded into the status bar, and displays the errors in the bottom bar when you're in the affected lines. 

The syntax checker for Objective-C  in Syntastic uses gcc though. So I spend some time and ported it over to Clang and so that it can read the same .clang_complete project definition file, that Clang Complete uses. These changes are not yet in Syntastic master (I did issue a pull request) so you'll need to [check out my fork for now](https://github.com/terhechte/syntastic):

    Bundle 'terhechte/syntastic'

    " Show sidebar signs.
    let g:syntastic_enable_signs=1

    " Read the clang complete file
    let g:syntastic_objc_config_file = '.clang_complete'

    " Status line configuration
    set statusline+=%#warningmsg#  " Add Error ruler.
    set statusline+=%{SyntasticStatuslineFlag()}
    set statusline+=%*
    nnoremap <silent> ` :Errors<CR>

    " Tell it to use clang instead of gcc
    let g:syntastic_objc_checker = 'clang'

Cmd           | Behaviour                         |
-------------:|:----------------------------------|
:Errors       | See the current errors in the file|

### Indentation
Vim's default Objective-C indent is not very functional. Thankfully, Björn Winckler, a main contributor to MacVim, [released a very good plugin for this](https://github.com/b4winckler/vim-objc), that fixes all the issues I had:

    Bundle "b4winckler/vim-objc"

### Switching Header Files
Another feature of XCode that I could not live without is the easy way to switch between header and implementation. [Vim-iOS](https://github.com/eraserhd/vim-ios) adds commands for this. With :A you can switch to the alternate file. In addition to that it also offers :XBuild for building your project and :Xinstall for installing it on a device.

    Bundle 'eraserhd/vim-ios.git'

Cmd           | Behaviour                         |
-------------:|:----------------------------------|
:A            | Switch to alternate file          |
:XBuild       | Compile Project                   |
:Xinstall     | Install on device                 |

### Additional Recommendations

That's it! These are all the packages you need for a pleasant Vim Objective-C experience. i would also recommend [Vim-Surround](https://github.com/tpope/vim-surround) for fast navigation in methods, and [Fugitive](https://github.com/tpope/vim-fugitive) for git integration like XCode and [ctrlp](https://github.com/kien/ctrlp.vim) for fast file opening like the XCode cmd+shift+o command:

    Bundle 'tpope/vim-fugitive'
    Bundle 'tpope/vim-surround'
    Bundle 'kien/ctrlp.vim'

Cmd           | Behaviour                         |
-------------:|:----------------------------------|
:Gcommit      | Commit whats in staging           |
:Gdiff        | Do a diff                         |
:Gstatus      | Current status                    |

[More](https://github.com/tpope/vim-fugitive/blob/master/doc/fugitive.txt)

#### Project Configuration: .clang_complete

Now that your Vim is all set up, you will need to define a .clang_complete file in the home directory of your project that tells Clang Complete where your files are and how to compile. I plan on writing a tool that generates this definition out of your XCode Project file, but haven't done so.

Here is an example file. If you intend to use this, please make sure that the paths to SDK's and more fit your system.

    -isysroot /Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator6.0.sdk
    -isystem /usr/local/lib/clang/3.3
    -F/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator6.0.sdk/System/Library/Frameworks
    -I/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator6.0.sdk/usr/include
    -D __MACH__
    -D __IPHONE_OS_VERSION_MIN_REQUIRED=40300
    -fblocks
    -fobjc-nonfragile-abi
    -fno-builtin
    -m32
    -include ./MyFantasticProject/Prefix.pch
    -Wall
    -Wextra
    -Wno-unknown-pragmas
    -Wno-unused-parameter
    -Wno-sign-compare
    -I./MyFantasticProject/Classes
    -I./MyFantasticProject/ThirdParty/FacebookSDK
    -I./MyFantasticProject/ThirdParty/AFNetworking

Basically, what you do is, at the bottom you go through all your directories that contain code and add them to the project. [I've written a small and handy Python script](https://gist.github.com/4665223) that does this and prints out the list of directories.

#### Closing Notes
Now, when you start Vim from within the directory that contains the .clang_complete file, you should get completion for all symbols by pressing <c-x> <c-o>. You can switch between the different parameters of a selector with <c-j> and <c-k>.

My current Vim configuration (which contains a lot more) can be found [here](https://github.com/terhechte/VimConfiguration).


