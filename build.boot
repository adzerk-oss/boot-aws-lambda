(set-env!
 :dependencies '[[com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]]
 :source-paths #{"src"})

(require '[adzerk.bootlaces :refer :all])

(def +version+ "1.0.0-SNAPSHOT")
(bootlaces! +version+)

(deftask build []
  (comp (javac)
        (build-jar)))

(task-options!
 pom {:project 'adzerk/boot-aws-lambda
      :version +version+})
