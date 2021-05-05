(defproject com.7theta/k8s "0.1.0"
  :description "A clojure library to make interaction with the K8s REST API available in code"
  :url "https://github.com/7theta/k8s-clj"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [http-kit "2.5.3"]
                 [metosin/jsonista "0.3.2"]
                 [inflections "0.13.2"]
                 [stencil "0.5.0"]]
  :repl-options {:init-ns k8s.core})
