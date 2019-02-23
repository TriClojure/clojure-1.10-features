(ns demo.repl
  (:require [clojure.main :as main]))

(defn -main
  []
  (main/repl))
    ;; :init        #(do (println "welcome to the repl!")
    ;;                   (.addShutdownHook
    ;;                     (Runtime/getRuntime)
    ;;                     (Thread. (fn [] (println "goodbye!")))))))
    ;; :need-prompt #(< (rand) 0.5)))
    ;; :prompt      (let [counter (atom 0)]
    ;;                #(printf "[%s] %s=> "
    ;;                         (swap! counter inc)
    ;;                         (ns-name *ns*)))))
    ;; :flush       #(do (println "flushing!") (flush))))
    ;; :read        (fn [request-prompt request-exit]
    ;;                ???)))
    ;; :eval        #(vector % '=> (eval %))))
    ;; :print       #(println "RESULT:" (pr-str %))))
    ;; :caught      #(println "you should do really do something about this"
    ;;                        (type %))))


