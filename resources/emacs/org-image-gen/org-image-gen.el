(defvar org-image-gen-code-renderer "resources/public/render.js"
  "The script/binary that takes code and renders a fancy image")

(defvar org-image-image-out-path "resources/public/img-content/"
  "The place where rendered images should be stored")

(defvar org-image-gen-phantomjs-path "/usr/local/lib/node_modules/phantomjs/lib/phantom/bin/phantomjs"
  "Where the phantomjs binary is located")

(defvar org-image-gen-feature-property
  :export-image
  "If this key is set on a src block (i.e. BEGIN_SRC python :export-image true)
then the source block will be exported as an image")

(defvar org-image-gen-template-property
  :export-template
  "This key defines the template to use for exporting code as an image. The default is template 1")

(defvar org-image-gen-default-template
  "template1"
  "The default template when exporting")

(defvar org-image-gen-blocks '()
  "this is where the block information on image-gen blocks is being stored")

(defvar org-image-gen-strip-noweb t
  "Should any noweb references be stripped from code blocks, i.e. <<noweb>>")

;; This will store the contents of source code blocks as local vars,
;; thus enabling access to them at a later export stage

(defadvice org-babel-get-src-block-info (after org-babel-store-info)
  (let* ((info-copy ad-return-value)
         (block-language (nth 0 info-copy))
         (block-args (nth 2 info-copy))
         (block-switches (nth 3 info-copy))
         (block-name (nth 4 info-copy))
         (block-text (nth 1 info-copy)))
    (when (and block-name ; only blocks with a name
            (assoc org-image-gen-feature-property block-args))
      ;;(list (assoc :export-image block-args) block-language block-args block-switches block-text)
      (setq org-image-gen-blocks (append org-image-gen-blocks
                                   (list (list block-name block-args block-language block-text)))))
    info-copy))
(ad-activate 'org-babel-get-src-block-info)

;; Simplification of calling command with stdin data
(defun perform-command (command stdin)
  (if stdin
    (with-temp-buffer
      (insert stdin)
      (shell-command-on-region
        ;; beginning and end of buffer
        (point-min)
        (point-max)
        ;; command and parameters
        command
        ;; output buffer
        (current-buffer)
        ;; replace?
        t
        ;; name of the error buffer
        "*ERRORBUFFER*"
        ;; show error buffer?
        t)
      (buffer-string))
    ;; else simple
    (shell-command-to-string command)))

;; This function will take a string of code, run it through the
;; templating tool, and return the path of the generated image.

(replace-regexp-in-string "<<\\w+>>" "" "abc\n<<klaus>>\nfunc <t>abc()")
(replace-regexp-in-string "abc\n<<klaus>>\nfunc <t>abc()" "abc" "")

(defun org-generate-header-image (name code template)
  "generate a header image with code/template, return the path to the image"
  (let* ((image-abs-path (format "%s%s%s.jpg" org-image-gen-base-path org-image-image-out-path name))
          (image-srv-path (format "%s%s.jpg" org-image-image-srv-path name))
          ;; remove any noweb references.
          (clean-code (if org-image-gen-strip-noweb
                        (replace-regexp-in-string "<<\\w+>>" "" code)
                        code))
          (command (format "%s %s%s %s %s" org-image-gen-phantomjs-path org-image-gen-base-path org-image-gen-code-renderer template image-abs-path))
          (result (perform-command command clean-code)))
    image-srv-path))

(defun meta-template (name value)
  "returns a static meta entry in the static meta format"
  (format "<!-- #+%s: %s -->\n" name value))

;; This code will always execute the given function when the html export
;; is almost done, allowing to modify the html, for example
;; i.e remove stuff
(defun fan/org-html-produce-inline-html (string backend info)
  "write value as a tag into the header of an org html export"
  (when (and (org-export-derived-backend-p backend 'html)
          (> (length org-image-gen-blocks) 0))
    (let ((fname ))
      (dolist (entry org-image-gen-blocks)
        ;; for each entry, generate an image
        ;; check if this fails because of .. paths..
        (let* ( (fname (format "%s-%s" (file-name-base) (first entry))) ; the gen file name
                (template (assoc org-image-gen-template-property (nth 1 entry)))
                (template (if template (cdr template) org-image-gen-default-template))
                (path (org-generate-header-image
                      fname
                      (nth 3 entry)
                      template)))
          (setq meta (meta-template (first entry) path))))
      (format "%s\n%s" meta string))))

(defun setup-org-image-gen ()
  ;; apparently this is not set
  ;(princ (expand-file-name org-image-gen-base-path))
  (setq org-export-filter-final-output-functions '())
  (add-to-list 'org-export-filter-final-output-functions
    'fan/org-html-produce-inline-html))

(provide 'org-image-gen)
