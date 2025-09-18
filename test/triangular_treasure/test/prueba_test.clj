(ns triangular-treasure.test.prueba-test
  (:require [clojure.test :refer :all]
            [triangular-treasure.src.prueba :as sut]))



(deftest basic-tests
  (is (= (sut/triangular 2) 3))
  ;;(is (= (triangular 7) 28))
  ;;(is (= (triangular 12) 78))
  ;;(is (= (triangular 25) 325))
  ;;(is (= (triangular 50) 1275))
  ;;(is (= (triangular 1000) 500500))
  ;;(is (= (triangular 5000) 12502500))
  ;;(is (= (triangular 10000) 50005000))
  ;;(is (= (triangular 0) 0))
  ;;(is (= (triangular -1) 0))
  ;;(is (= (triangular -5) 0))
  )