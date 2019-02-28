(ns demo.io-prepl
  (:require [clojure.core.server :as server]))

(defn test-io-prepl
  []
  ;; :valf is a function that takes a value and returns a stringified value. By
  ;; default, :valf is plain old pr-str.
  ;;
  ;; We can also provide our own :valf that modifies the value before
  ;; stringifying it.
  (server/io-prepl :valf #(pr-str [:test %])))

(defn -main
  []
  (server/start-server {:accept  `test-io-prepl
                        :address "127.0.0.1"
                        :port    5555
                        :name    "test io-prepl"})
  (println "prepl server is running")
  (println)
  (println "to connect:")
  (println "clj -e '(clojure.core.server/remote-prepl \"127.0.0.1\" 5555 *in* println)'")
  (println)
  (println "or:")
  (println "telnet localhost 5555")
  ;; wait forever
  @(promise))

