; This contains base defintions that will be available in all enlive templates.
; They will be registered in static.core/

; metadata is a dictionary of
; :categories -> {:name :url} all the tags in the project
; :projects -> {:name :url} all the sites / projects under sites
; :config -> the site config dictionary
; :type can be :post or :site or :nopost (?)
; :pager -> {:older "link" :newer "link"} (only on pages with pages)

; content is a vector of dictionaries:
; :id
; :title
; :content
; :url
; :content
; :keywords / tags
; :footnotes

(def base-template-file (static.core/template-path "_index.html"))

(enlive/defsnippet template-tags-link-model base-template-file [:.tagoverview :ul :> enlive/first-child]
  [post]
  [:span] (enlive/content (:date post))
  [:a] (enlive/do-> (enlive/set-attr :href (:url post)) (enlive/content (:title post))))

(enlive/defsnippet template-tags-model  base-template-file
  [:.tagoverview]
  [[headline content]]
  [[:h2 (enlive/nth-of-type 1)]] (enlive/content headline)
  [:ul] (enlive/content (map #(template-tags-link-model %) content)))

(enlive/defsnippet template-pager-model  base-template-file
  [:#pager]
  [{:keys [newer older]}]
  [:#previous-page] (enlive/set-attr :href newer)
  [:#next-page] (enlive/set-attr :href older)
  ; remove the previous or next pager when we don't need them
  [:#previous-page] #(when newer %)
  [:#next-page] #(when older %))

(enlive/defsnippet template-footnotes-model  base-template-file
  [:#thearticle :> :.footnotes :> enlive/first-child :> enlive/first-child]
  [{:keys [text ref id]}]
  [:li] (enlive/do->
         (enlive/set-attr :id ref))
  [:li :> enlive/first-child :> enlive/text-node] (enlive/substitute text)
  [:li :> enlive/first-child :> :a] (enlive/set-attr :href id))

(enlive/defsnippet template-article-model base-template-file [:#thearticle]
  [{:keys [id title date content url footnotes] :as thearticle}]
  [:.actual-content] (enlive/html-content content)
  [:h6 :> enlive/text-node] (enlive/substitute (str " " date " "))
  [:h6 :a] (enlive/set-attr :href url)
  [:h3 :a] (enlive/content title)
  [:h3 :a] (enlive/set-attr :href url)
  [:#thearticle] (enlive/set-attr :id (str "article-" id))
  [:.footnotes :> enlive/first-child] (enlive/content (map template-footnotes-model footnotes)) 
  )

(enlive/defsnippet template-category-model  base-template-file
  [:#categories :> enlive/first-child]
  [{:keys [tag url count]}]
  [:a] (enlive/do->
        (enlive/set-attr :href url)
        (enlive/content (str tag " (" count ")"))))


(enlive/defsnippet template-project-model  base-template-file
  [:#projects :> :li.project-template]
  [{:keys [project link]}]
  [:li] (enlive/remove-attr :class) ;remove the class as we filter the template based on it
  [:a] (enlive/do->
        (enlive/set-attr :href link)
        (enlive/content project)))

(enlive/defsnippet template-head-model base-template-file
  [:head]
  [metadata]
  [[:meta (enlive/attr= :content "template")]]
  (enlive/clone-for [{:keys [name value]} [{:name "description" :value (:description metadata)}
                                           {:name "keywords" :value (:tags metadata)}
                                           {:name "author" :value (:author metadata)}]]
                    (enlive/do->
                     (enlive/set-attr :name name)
                     (enlive/set-attr :content value)))

     ; Next, the RSS Link
     [[:link (enlive/attr= :rel "alternate" :title "rsstemplate")]]
     (enlive/set-attr :title (:site-title (static.config/config)))

     ; The title
     [:head :title] (enlive/content (if-let [t (:title metadata)] t (:site-title metadata)))
  )

