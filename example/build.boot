(set-env!
 :dependencies '[[org.clojure/clojure "1.7.0"]
                 [cheshire "5.5.0"]
                 [adzerk/boot-aws-lambda "1.0.0-SNAPSHOT"]]
 :resource-paths #{"src"})

(deftask build
  []
  (comp (uber)
        (jar :file "lambda-project.jar")
        (sift :include #{#"^lambda-project.jar"})))
