(static.core/define-template base-template-file
  
     [:head] (enlive/content (static.core/template-head-model metadata))

     [:#maincontent]   (enlive/content (map #(static.core/template-tags-model %) content)) 

     ; The categories
     [:#categories] (enlive/content (map #(static.core/template-category-model %) (:categories metadata)))

     [:#swiftblogs] (enlive/content nil)

     ; And the projects
     [:#projects] (enlive/content (map #(template-project-model %) (:projects metadata)))
)

