; The define-template macro takes care of all the boilerpalte
(static.core/define-template base-template-file 
     ; set the head properties
     [:head] (enlive/content (static.core/template-head-model metadata))

     ; Now the main Content
     [:#maincontent]   (enlive/content (map #(static.core/template-article-model % ) content)) 

     
     ; if we have a site entry, remove the date and the headline
     [:#maincontent :> :article :> [:h6 (enlive/nth-of-type 1)]] #(when (not (= (:type metadata) :site)) %)

     ; and append the pager, if possible
     [:#maincontent] (enlive/append (if (:pager metadata) (static.core/template-pager-model (:pager metadata)) ""))

     ; The categories
     [:#categories] (enlive/content (map #(static.core/template-category-model %) (:categories metadata)))

     ; And the projects
     [:#projects] (enlive/append (map #(static.core/template-project-model %) (:projects metadata)))
     [:#projects :> :li.project-template] (fn [a] nil) ;we remove the template
)
