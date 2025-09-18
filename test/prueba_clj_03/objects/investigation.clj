(ns prueba-clj-03.objects.investigation
  (:require [clojure.test :refer :all]))
;Object API ==================================
(def NOT_VALID_ATTRIBUTE "NOT_VALID_ATTRIBUTE")
(def updater (fn [persona att val] (update-in persona [:props (keyword att)] (fn [_] val))))
(def updater-private (fn [persona att val] (update-in persona [:private-props (keyword att)] (fn [_] (fn [] val)))))

(defn exec
  ([subject action]
   ((-> subject :actions action) subject))
  ([subject action param]
   ((-> subject :actions action) subject param)))

(defn getter
  [subject action]
   ((-> subject :getters action) subject))
; ==================================
; ==================================

; Business ==================================

(defn- do_walk
  [init step]
  (if (> init step)
    (format "walked %s steps" step)
    (do
      (println (format "walked %s step" init))
      (do_walk (inc init) step))))

(defprotocol PersonActions
  "Some actions that a person can make"
  (can-say-my-name-and-greeting [this greeting]
    "Can return a string with the name and some message")
  )

(defprotocol DeveloperActions
  "Some actions that a person can make"
  (rant-and-greetings [this greeting]
    "Can rant and say hello")
  )

(defprotocol GetterActions
  "Some actions that a person can make"
  (get-name [this]
    "Can retrieve the name")
  )
(defrecord Persona [_name]
  PersonActions
  (can-say-my-name-and-greeting [_ greetings]
    (format "Hey there, I'm %s and %s" _name greetings))
  DeveloperActions
  (rant-and-greetings [_ greetings]
    (format "Fucking Clojure, I'm %s and %s" _name greetings))
  GetterActions
  (get-name [_]
    (str "My name is " _name))
  )

(defn create-a-persona [name]
  (Persona. name))
(deftest Persona2-test
  (let [charly (create-a-persona "carlos") ]
    (is (= (get-name charly ) "My name is carlos"))
    (is (= (can-say-my-name-and-greeting charly "ta'que los pario") "Hey there, I'm carlos and ta'que los pario"))
    (is (= (rant-and-greetings charly "ta'que los pario") "Fucking Clojure, I'm carlos and ta'que los pario")) ))

(def person {:constructor (fn ([name age dni]
                               (-> person
                                   (updater :name name)
                                   (updater :age age)
                                   (updater-private :dni dni))))
             :private-props  {:dni #(NOT_VALID_ATTRIBUTE) }
             :props  {:name NOT_VALID_ATTRIBUTE
                      :age NOT_VALID_ATTRIBUTE}
             :actions {:tell-your-name #(str "My name is " (-> % :props :name))
                       :add-10-to-age #(+ (-> %1 :props :age) %2)
                       :walk #(do_walk 1 %2)}
             ; optional
             :getters {:dni #((-> %1 :private-props :dni))}})

; ==================================
; ==================================

; Tests ==================================

(deftest person-test
  (let [charly ((-> person :constructor) "carlos" 73 "21912691")]
    (is (= (-> charly :props :name) "carlos"))
    (is (= (-> charly :props :age) 73))
    (is (= (exec charly :tell-your-name) "My name is carlos"))
    (is (= (exec charly :add-10-to-age 10) 83))
    (is (= (exec charly :walk 10) "walked 10 steps"))
    (is (= (getter charly :dni) "21912691"))
    ))

; Clojure Spec Def record y protocols