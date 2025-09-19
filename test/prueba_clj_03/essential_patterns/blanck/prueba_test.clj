(ns prueba-clj-03.essential-patterns.blanck.prueba-test
  (:require [clojure.test :refer :all]
            ))

(defn example [n] (+ n 1))

(deftest example-test
   (is (= (example 1) 2))
   )