[:site-title "Appventure" ; The default title of the pages
 :site-title-page " - Page %s" ; The extension for older pages
 :site-title-tag " - Tag %s" ; The extension for tags
 :site-description "Documenting my technology research"
 :site-url "http://appventure.me"
 :site-default-keywords "clojure objective-c cocoa javascript clojurescript html c ios mac photodesk stylemac benedikt terhechte research"
 :site-author "Benedikt Terhechte"
 :in-dir "resources/"
 :out-dir "html/"
 :default-template "default.clj"
 :list-template "list.clj"
 :base-template "base.clj"
 :encoding "UTF-8"
 :blog-as-index true
 :posts-per-page 5
 :create-archives true
 :archives-title "Archives"
 :archives-title-month " - %s" ; for the individual month
 :date-format-post "E, d MMM yyyy" ; the output dateformat for posts
 :date-format-rss "E, d MMM yyyy HH:mm:ss Z" ; the date format for the rss feed
 :date-format-archive "MMMM yyyy" ; the output dateformat for archive links
 :atomic-build true
 :emacs "/usr/local/bin/emacs"
 :emacs-config " -q "
 :emacs-eval ['(setq user-emacs-directory "resources/emacs/")
              '(setq vc-handled-backends ())
              '(add-to-list 'load-path "~/.emacs.d/org-mode/lisp")
              '(add-to-list 'load-path "~/.emacs.d/org-mode/contrib/lisp")
              '(add-to-list 'load-path "resources/emacs/htmlize-20130207.2102/")
              '(add-to-list 'load-path "resources/emacs/soothe-theme-20130805.1700/")
              '(require 'htmlize)
              '(require 'org)
              '(require 'ob)
              '(global-font-lock-mode 1)
              '(set-face-foreground 'font-lock-string-face "#afafff")
              '(set-face-foreground 'font-lock-keyword-face "#ff5f00")
              '(set-face-foreground 'font-lock-function-name-face "#d7af00")
              '(set-face-foreground 'font-lock-builtin-face "#afd700")
              '(set-face-foreground 'font-lock-comment-face "#008787")]]
