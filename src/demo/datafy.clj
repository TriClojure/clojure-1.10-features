(ns demo.datafy
  (:require [clojure.datafy               :refer (datafy nav)]
            [clojure.pprint               :refer (pprint)]
            [hiccup.page                  :as    hiccup]
            [io.pedestal.http             :as    http]))

(def INITIAL-NODE java.util.concurrent.Phaser)

(def current-node (atom INITIAL-NODE))

(defn nav-link
  [data k v]
  [:a {:href (format "?k=%s&v=%s" (name k) (name v))}
   (pr-str v)])

(defn htmlify
  [data h-level]
  (if (map? data)
    [:div
     [(keyword (str \h h-level)) (:name data)]
     (apply
       concat
       (for [[k v] (dissoc data :name)]
         [[(keyword (str \h (inc h-level))) (name k)]
          (cond
            (map? v)
            (for [v (vals v)]
              (for [v (if (coll? v) v [v])]
                (htmlify (datafy v) (+ h-level 2))))

            (coll? v)
            [:ul (map #(vector :li (nav-link data k %)) v)]

            :else
            (nav-link data k v))]))]
    [:h1 (pr-str data)]))

(defn html-page
  [body]
  (hiccup/html5
    [:head [:title "clojure 1.10 datafy/nav demo"]]
    [:body body]))

(defn index
  [{:keys [query-params]}]
  (let [{:keys [k v]} query-params]
    (if (and k v)
      ;; HACK: workaround for the query params being strings, but actual values
      ;; not being strings
      (let [k (keyword k)
            v (if (= :flags k)
                (keyword v)
                (try
                  (Class/forName v)
                  (catch ClassNotFoundException _
                    (symbol v))))]
        (swap! current-node #(nav % k v)))
      (reset! current-node INITIAL-NODE))
    {:status 200
     :body   (try
               (html-page (htmlify (datafy @current-node) 1))
               (catch Throwable t
                 (html-page [:pre (with-out-str (pprint t))])))}))

(defn -main
  []
  (println "Starting server on port 8008")
  (-> {::http/routes #{["/" :get `index]}
       ::http/port   8008
       ::http/type   :jetty}
      http/default-interceptors
      (update ::http/interceptors concat [http/html-body])
      http/create-server
      http/start))

