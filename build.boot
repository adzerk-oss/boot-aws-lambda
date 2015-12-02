(set-env!
 :dependencies '[[com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [org.clojure/clojure "1.7.0"]
                 [cheshire "RELEASE"]]
 :source-paths #{"src"}
 :resource-paths #{"rsc"})

(task-options!
 pom {:project 'adzerk/boot-aws-lambda
      :version "1.0.0-SNAPSHOT"}
 jar {:main 'adzerk.BootLambda})
