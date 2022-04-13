(defproject com.7theta/k8s "0.1.2"
  :description "A clojure library to make interaction with the K8s REST API available in code"
  :url "https://github.com/7theta/k8s-clj"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.7theta/via "10.2.1"]
                 [metosin/jsonista "0.3.5"]
                 [inflections "0.13.2"]
                 [stencil "0.5.0"]]
  :repl-options {:init-ns k8s.core})
