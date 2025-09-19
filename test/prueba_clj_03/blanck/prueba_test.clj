(ns prueba-clj-03.blanck.prueba-test
  (:require [clojure.test :refer :all]
            [prueba-clj-03.blanck.src.prueba :as sut]
            ))

(deftest example
   (is (= (sut/example 1) 2))
   )