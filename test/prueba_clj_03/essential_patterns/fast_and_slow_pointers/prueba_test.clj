(ns prueba-clj-03.essential-patterns.fast-and-slow-pointers.prueba-test
  (:require [clojure.test :refer :all]
            ))

(defn example [n] (+ n 1))

(deftest example-test
  (is (= (example 1) 2))
  )