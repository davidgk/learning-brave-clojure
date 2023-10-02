(ns prueba-clj-03.otras-pruebas.ejemplos_protocolos_test
  (:require [clojure.test :refer :all]))

(defrecord Engine [performance])
(defrecord Car [engine])
(defrecord Driver [car])

(defprotocol Drives
  (drives [driver fuel-type]))

(extend-type Driver
  Drives
  (drives [driver fuel-type]
    (let [car (:car driver)
          engine (:engine car)
          performance (:performance engine)]
      ((keyword fuel-type) performance))))

;(defn drives [driver fuel-type]
;  (let [car (:car driver)
;        engine (:engine car)
;        performance (:performance engine)]
;    ((keyword fuel-type) performance)))

(deftest driver-car-engine-test
  (let [engine-under-expert (Engine. {:eco-fuel 100 :semi-eco-fuel 80 :std-fuel 60 :default 30})
        car-expert (Car. engine-under-expert)
        driver-expert (Driver. car-expert)]
    (testing "when driver is expert and uses eco-fuel it should perform 100"
      (is (= (drives driver-expert :eco-fuel) 100)))
    (testing "when driver is expert and uses semi-eco-fuel it should perform 80"
      (is (= (drives driver-expert :semi-eco-fuel) 80)))
    (testing "when driver is expert and uses std-fuel it should perform 60"
      (is (= (drives driver-expert :std-fuel) 60))))
  (let [engine-under-std (Engine. {:eco-fuel 80 :semi-eco-fuel 60 :std-fuel 40 :default 30})
        car-std (Car. engine-under-std)
        driver-std (Driver. car-std)]
    (testing "when driver is std and uses eco-fuel it should perform 80"
      (is (= (drives driver-std :eco-fuel) 80)))
    (testing "when driver is std and uses semi-eco-fuel it should perform 60"
      (is (= (drives driver-std :semi-eco-fuel) 60)))
    (testing "when driver is std and uses std-fuel it should perform 40"
      (is (= (drives driver-std :std-fuel) 40)))))

;(deftest driver-gear-test
;  (testing "Cuando un conductor pone primera el automovil se debe mover a 30"
;    (let [
;          a-car (Car. {:first-gear 30 :second-gear 60 :third-gear 90})
;          a-driver (Driver. a-car)]
;      (is (= (change-gear a-driver :first-gear) 30))
;      (is (= (change-gear a-driver :second-gear) 60))
;      (is (= (change-gear a-driver :third-gear) 90)))))
