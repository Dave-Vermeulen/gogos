(defproject gogo-x-roads "0.1.0-SNAPSHOT"
  :description "This is FOSS Tech camp with STEM curriculum for the youth by the youth"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                [ring/ring-defaults "0.3.4"]
                [ring/ring-core "1.9.6"]
                [ring/ring-jetty-adapter "1.9.6"]
                [compojure "1.6.2"]
                [hiccup "2.0.0-alpha2"]
                [ring/ring-json "0.5.1"]
                [org.xerial/sqlite-jdbc "3.36.0.3"]
                [org.clojure/java.jdbc "0.7.12"]
                [buddy/buddy-hashers "1.6.0"]] ;;password hashing
                
  :main ^:skip-aot gogo-x-roads.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
