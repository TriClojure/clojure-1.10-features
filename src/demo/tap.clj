(ns demo.tap)

(let [counter (atom 0)]
  (defn tap-handler
    [value]
    (swap! counter inc)
    (prn :tap @counter value)))

(comment
  (add-tap tap-handler)
  (tap> :hello)
  (remove-tap tap-handler))
