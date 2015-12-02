(ns adzerk.test
  (:require [cheshire.core        :as json]
            [clojure.java.io      :as io]))

(defn handle-event
  [event]
  (println "Got the following event: " (pr-str event))
  {:status "testing123"})

(defn handler
  [in out ctx]
  (let [event (json/parse-stream (io/reader in))
        res (handle-event event)]
    (with-open [w (io/writer out)]
      (json/generate-stream res w))))
