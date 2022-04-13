(defproject com.7theta/k8s "0.1.2"
  :description "A clojure library to make interaction with the K8s REST API available in code"
  :url "https://github.com/7theta/k8s-clj"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.7theta/aleph "0.4.7-alpha9-3"]
                 [metosin/jsonista "0.3.2"]
                 [inflections "0.13.2"]
                 [stencil "0.5.0"]]
  :repl-options {:init-ns k8s.core})
