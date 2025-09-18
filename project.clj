(defproject prueba-clj-03 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/core.async "1.6.681"]
                 [org.clojure/clojure "1.11.1"]
                 [prismatic/schema "1.4.1"]
                 [org.clojure/tools.logging "1.3.0"]
                 [log4j/log4j "1.2.17"]
                 ]
  :main ^:skip-aot prueba-clj-03.core
  :target-path "target/%s"
  :jvm-opts ["-Dclojure.tools.logging.factory=clojure.tools.logging.impl/log4j"]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
