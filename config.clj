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
 :rss-description-char-limit 500
 :atomic-build true
 :emacs "/usr/local/bin/emacs"
 :emacs-config " -q "
 :emacs-custom-setup "(setup-org-image-gen)"
 :emacs-eval ['(setq user-emacs-directory "resources/emacs/")
              '(setq vc-handled-backends ())
              '(add-to-list 'load-path "~/.emacs.d/org-mode/lisp")
              '(add-to-list 'load-path "~/.emacs.d/org-mode/contrib/lisp")
              '(add-to-list 'load-path "resources/emacs/htmlize-20130207.2102/")
              '(add-to-list 'load-path "resources/emacs/org-image-gen/")
              '(require 'htmlize)
              '(require 'org)
              '(require 'ob)
              '(require 'org-image-gen)
              '(global-font-lock-mode 1)
              '(load-theme 'adwaita)
              '(setq org-confirm-babel-evaluate nil) ; automatically export code blocks
               ;; we need to store the base path before opening a file
              '(setq org-image-gen-base-path (format "%s" default-directory))
               ;; this is a small phantomjs script that takes a block of
               ;; code (handed over via stdin) a template name, renders
               ;; the code out as an image, and saves the image in a given path
               ;; i.e. render.js
               '(setq org-image-gen-code-renderer "resources/emacs/org-image-gen/render.js")
               ;; where to store the generated images in the file system
               '(setq org-image-image-out-path "resources/public/img-content/")
               ;; where will the images be once the static is generated, available for the server
               '(setq org-image-image-srv-path "/img-content/")

              ;'(set-face-foreground 'font-lock-string-face "#afafff")
              ;'(set-face-foreground 'font-lock-keyword-face "#ff5f00")
              ;'(set-face-foreground 'font-lock-function-name-face "#d7af00")
              ;'(set-face-foreground 'font-lock-builtin-face "#afd700")
              ; '(set-face-foreground 'font-lock-comment-face "#008787")
               ]]
