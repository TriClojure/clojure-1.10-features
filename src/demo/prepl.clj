(ns demo.prepl
  (:require [clojure.core.server :as server])
  (:import [clojure.lang LineNumberingPushbackReader]
           [java.io InputStreamReader PipedInputStream PipedOutputStream]
           [java.util.concurrent LinkedBlockingQueue]))

(defn input-reader
  [input-stream]
  (LineNumberingPushbackReader. (InputStreamReader. input-stream)))

(defn test-prepl
  [^LineNumberingPushbackReader in, ^LinkedBlockingQueue out]
  (server/prepl in #(.put out %)))

(defn -main
  []
  (with-open [in  (PipedInputStream.)
              out (PipedOutputStream. in)]
    (let [prepl-out (LinkedBlockingQueue.)]
      (println "Starting prepl...")
      (future (test-prepl (input-reader in) prepl-out))
      (future (while true (prn (.take prepl-out)))))

    (while true
      (printf "input> ")
      (flush)
      (let [input (-> (read-line) (str \newline) .getBytes)]
        (doto out
          (.write input 0 (count input))
          .flush))
      (Thread/sleep 100))))
