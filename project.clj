(defproject lifepad "0.1.0-SNAPSHOT"
  :description "Conway's Game of Life for the Launchpad S."
  :url "http://github.com/lgastako/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot lifepad.core
  :jvm-opts ^:replace ["-XX:-OmitStackTraceInFastThrow"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [its-log "0.1.0-SNAPSHOT"]
                 [launchtone "0.1.0"]])
