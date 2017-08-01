(defproject minipcp-grade "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [liberator "0.13"]
                 [compojure "1.3.4"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-jetty-adapter "1.6.2"]
                 [ring/ring-json "0.4.0"]                 
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  :main ^:skip-aot minipcp-grade.core
  :target-path "target/%s"
  :plugins [[lein-ring "0.8.11"]
            [lein-gorilla "0.4.0"]]
  :ring {:handler minipcp-grade.core/handler}
  :profiles {:uberjar {:aot :all}})
